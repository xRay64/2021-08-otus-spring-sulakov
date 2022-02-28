package ru.otus.configgenerator.service.mavenrest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hazelcast.core.HazelcastInstance;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;
import ru.otus.configgenerator.model.mavenRest.ArtifactList;
import ru.otus.configgenerator.model.mavenRest.PluginItem;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;

@SpringBootTest
@DisplayName("PluginArtifactsProviderImplTest должен")
class PluginArtifactsProviderImplTest {

    private static final String EXPECTED_URL = "https://repo.corp.tander.ru/service/rest/v1/search?maven.artifactId=gradle-release-plugin";
    @Autowired
    private PluginArtifactProvider pluginArtifactProvider;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private HazelcastInstance hazelcastInstance;

    private ObjectMapper mapper = new ObjectMapper();

    private PluginItem expectedPluginItem;
    private ArtifactList expectedArtifactList;

    @BeforeEach
    public void initStep() throws JsonProcessingException, URISyntaxException {
        System.out.println(hazelcastInstance.getClass().getName());

        expectedPluginItem = new PluginItem();
        expectedPluginItem.setId("testIdString");
        expectedPluginItem.setGroup("test group");
        expectedPluginItem.setName("test name");
        expectedPluginItem.setVersion("1.1.1");
        expectedPluginItem.setRepository("test repo");

        expectedArtifactList = new ArtifactList();
        expectedArtifactList.setItems(List.of(expectedPluginItem));

        hazelcastInstance.getMap("mavenPluginList").clear();
    }

    @Test
    @DisplayName("кешировать ответ")
    void shouldCacheResponse() throws URISyntaxException, JsonProcessingException {
        MockRestServiceServer mockRestServiceServer = MockRestServiceServer.createServer(restTemplate);
        mockRestServiceServer.expect(ExpectedCount.once(), requestTo(new URI(EXPECTED_URL)))
                .andExpect(method(HttpMethod.GET))
                .andRespond(
                        withStatus(HttpStatus.OK)
                                .contentType(MediaType.APPLICATION_JSON)
                                .body(mapper.writeValueAsString(expectedArtifactList))
                );

        pluginArtifactProvider.getPluginList();
        pluginArtifactProvider.getPluginList();
        pluginArtifactProvider.getPluginList();

        mockRestServiceServer.verify();
    }

    @Test
    @DisplayName("идти в мавен после истечения TTL кэша")
    public void whenTTLIsEndShouldGoToMaven() throws URISyntaxException, JsonProcessingException, InterruptedException {
        MockRestServiceServer mockRestServiceServer = MockRestServiceServer.createServer(restTemplate);
        mockRestServiceServer.expect(ExpectedCount.twice(), requestTo(new URI(EXPECTED_URL)))
                .andExpect(method(HttpMethod.GET))
                .andRespond(
                        withStatus(HttpStatus.OK)
                                .contentType(MediaType.APPLICATION_JSON)
                                .body(mapper.writeValueAsString(expectedArtifactList))
                );

        pluginArtifactProvider.getPluginList();
        Thread.sleep(3000);
        pluginArtifactProvider.getPluginList();
        pluginArtifactProvider.getPluginList();

        mockRestServiceServer.verify();
    }

    @Test
    @DisplayName("должен возвращать ожидаемый результат")
    public void shouldReturnExpectedResult() throws URISyntaxException, JsonProcessingException {
        MockRestServiceServer mockRestServiceServer = MockRestServiceServer.createServer(restTemplate);
        mockRestServiceServer.expect(ExpectedCount.once(), requestTo(new URI(EXPECTED_URL)))
                .andExpect(method(HttpMethod.GET))
                .andRespond(
                        withStatus(HttpStatus.OK)
                                .contentType(MediaType.APPLICATION_JSON)
                                .body(mapper.writeValueAsString(expectedArtifactList))
                );

        List<PluginItem> pluginList = pluginArtifactProvider.getPluginList();


        assertThat(pluginList.get(0))
                .usingRecursiveComparison()
                .isEqualTo(expectedPluginItem);
    }
}