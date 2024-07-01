package com.id3.taskscheduler.graphapi.apiservices;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.id3.taskscheduler.graphapi.model.User;

import java.io.IOException;

public interface IGraphApiService {

    public User getMe() throws IOException, InterruptedException;

    public void createMeeting() throws IOException, InterruptedException;


}
