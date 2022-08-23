package com.example.springquartzexample.manage;

import com.example.springquartzexample.job.PrintLogJob;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class JobService {

    private final Scheduler scheduler;

    public JobKey createPrintLogJob(String cron) {
        log.info("printLogJob 생성");
        JobDetail printLogJobDetail = JobBuilder.newJob(PrintLogJob.class)
                .withIdentity(UUID.randomUUID().toString(), UUID.randomUUID().toString())
                .build();

        Trigger trigger = TriggerBuilder.newTrigger()
                .forJob(printLogJobDetail)
                .withSchedule( CronScheduleBuilder.cronSchedule(cron))
                .build();

        try {
            scheduler.addJob(printLogJobDetail, true, true);
            scheduler.scheduleJob(trigger);
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }

        return printLogJobDetail.getKey();
    }

    public List<JobKey> getJobKeys() {
        List<JobKey> jobKeys = new LinkedList<>();
        try {
            for(String name : scheduler.getJobGroupNames())
                jobKeys.addAll(scheduler.getJobKeys(GroupMatcher.jobGroupEquals(name)));
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
        return jobKeys;
    }

    public void deleteJob(String name, String group) {
        log.info("job 삭제");
        JobKey jobKey = JobKey.jobKey(name, group);
        try {
            scheduler.deleteJob(jobKey);
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
    }

}
