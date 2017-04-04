package com.cardinal;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class NotificationsScheduler {

    @Autowired
    JobLauncher jobLauncher;

    @Autowired
    Job processUserJob;

    @Autowired
    Job importUserJob;

    @Scheduled(fixedRate = 20000)
    public void runProcessUserJob() throws Exception {
        jobLauncher.run(processUserJob, new JobParameters());
    }

    @Scheduled(fixedRate = 25000)
    public void runImportUserJob() throws Exception {
        jobLauncher.run(importUserJob, new JobParameters());
    }

}