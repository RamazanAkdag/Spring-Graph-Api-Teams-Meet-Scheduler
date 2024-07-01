package com.id3.taskscheduler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.id3.taskscheduler.graphapi.token.TokenManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.net.http.HttpClient;


@SpringBootApplication
public class TaskSchedulerApplication {

    public static void main(String[] args) throws IOException, InterruptedException {

        SpringApplication.run(TaskSchedulerApplication.class, args);




    }


}
