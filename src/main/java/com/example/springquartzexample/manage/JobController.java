package com.example.springquartzexample.manage;

import lombok.RequiredArgsConstructor;
import org.quartz.JobKey;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@RequiredArgsConstructor
public class JobController {

    private final JobService jobService;

    @PostMapping("/printLogJob")
    @ResponseStatus(CREATED)
    public JobKey createPrintLogJob(@RequestParam("cron") String cron) {
        return jobService.createPrintLogJob(cron);
    }

    @GetMapping("/jobs")
    public List<JobKey> getJobKeys() {
        return jobService.getJobKeys();
    }

    @DeleteMapping("/job")
    @ResponseStatus(NO_CONTENT)
    public void deleteJob(@RequestParam("name") String name, @RequestParam("group") String group) {
        jobService.deleteJob(name, group);
    }

}
