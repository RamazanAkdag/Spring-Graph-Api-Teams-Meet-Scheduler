package com.id3.taskscheduler.quartz;

import com.id3.taskscheduler.service.IMeetService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
public class MeetJob implements Job {
    @Autowired
    private IMeetService meetService;
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        try {
            log.info("---Create Meet Job Started -----------");
            meetService.createMeet();
            log.info("---Create Meet Job Ended -----------");
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
