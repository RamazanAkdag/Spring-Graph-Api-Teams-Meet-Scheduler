package com.id3.taskscheduler.service;

import com.id3.taskscheduler.graphapi.apiservices.IGraphApiService;
import com.id3.taskscheduler.graphapi.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@Slf4j
@RequiredArgsConstructor
public class MeetManager implements IMeetService {

    private final IGraphApiService graphApiService;
    @Override
    public void createMeet() throws IOException, InterruptedException {
        log.info("-----------------------------------------------");
        graphApiService.createMeeting();

    }
}
