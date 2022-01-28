package ru.otus.homeworkintegration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.config.EnableIntegration;
import ru.otus.homeworkintegration.domain.Commit;
import ru.otus.homeworkintegration.services.GitLab;

import java.util.List;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;

@SpringBootApplication
@EnableIntegration
@IntegrationComponentScan
public class HomeworkIntegrationApplication {

    public static void main(String[] args) throws Exception {
        ConfigurableApplicationContext ctx = SpringApplication.run(HomeworkIntegrationApplication.class, args);

        TeamCity teamCity = ctx.getBean(TeamCity.class);
        GitLab gitLab = ctx.getBean(GitLab.class);

        ForkJoinPool pool = ForkJoinPool.commonPool();

        while (true) {
            Thread.sleep(7000);

            pool.execute(() -> {
                Random random = new Random();
                List<Commit> changes = gitLab.getChanges(random.nextInt(1, 3));
                System.out.println("Collect changes from GL:\n" + changes + "\n\n");
                String deploysLog = teamCity.process(changes);
                System.out.println("        DEPLOY LOGS        \n-----------------------------");
                System.out.println(deploysLog);
            });
        }
    }

}
