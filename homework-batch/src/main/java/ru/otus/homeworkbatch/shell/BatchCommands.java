package ru.otus.homeworkbatch.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
@RequiredArgsConstructor
public class BatchCommands {
    private final Job bookImportJob;

    private final JobLauncher jobLauncher;

    @ShellMethod(value = "startBookImportJob", key = "batch-book-start")
    public void setBookImportJob() throws Exception {
        JobExecution execution = jobLauncher.run(bookImportJob, new JobParametersBuilder().toJobParameters());
        System.out.println(execution);
    }
}
