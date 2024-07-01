package com.id3.taskscheduler.quartz;

import com.id3.taskscheduler.graphapi.token.TokenManager;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
public class RefreshTokenJob implements Job {
    @Autowired
    private TokenManager tokenManager;


    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        try {
            log.info("---token refresh job started ----------------");
            tokenManager.refreshToken();
            log.info("---token refresh job ended ------------------");
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
