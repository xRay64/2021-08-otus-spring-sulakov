package ru.otus.homeworkintegration.integration;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.stereotype.Component;
import ru.otus.homeworkintegration.services.BuildAgent;
import ru.otus.homeworkintegration.services.DeployService;

@Component
@RequiredArgsConstructor
public class CICDFlow {
    private final DeployService deployService;
    private final BuildAgent buildAgent;

    @Bean
    public IntegrationFlow integrationFlow() {
        return IntegrationFlows.from("commitsChanel")
                .split()
                .handle(buildAgent, "build")
                .aggregate()
                .channel("buildChanel")
                .handle(deployService, "deploy")
                .channel("deployLogsChanel")
                .get();
    }
}
