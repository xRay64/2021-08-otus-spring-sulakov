package ru.otus.homeworkintegration;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import ru.otus.homeworkintegration.domain.Commit;

import java.util.Collection;

@MessagingGateway
public interface TeamCity {
    @Gateway(requestChannel = "commitsChanel", replyChannel = "deployLogsChanel")
    String process(Collection<Commit> commits);
}
