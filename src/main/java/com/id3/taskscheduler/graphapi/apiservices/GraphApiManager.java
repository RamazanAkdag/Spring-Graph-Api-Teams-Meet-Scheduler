package com.id3.taskscheduler.graphapi.apiservices;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.id3.taskscheduler.graphapi.model.User;
import com.id3.taskscheduler.graphapi.token.TokenConstants;
import com.id3.taskscheduler.graphapi.token.TokenManager;
import com.id3.taskscheduler.mail.IEmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class GraphApiManager implements IGraphApiService {

    private final ObjectMapper objectMapper;
    private final HttpClient client;
    private final TokenManager tokenManager;
    private final IEmailService emailService;
    @Override
    public User getMe() throws IOException, InterruptedException {

        String requestUrl = "https://graph.microsoft.com/v1.0/me";

        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(requestUrl))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + TokenConstants.ACCESS_TOKEN)
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        User user = objectMapper.readValue(response.body(), User.class);

        tokenManager.refreshToken();

        return user;
    }

    @Override
    public void createMeeting() throws IOException, InterruptedException {
        String requestUrl = "https://graph.microsoft.com/v1.0/me/onlineMeetings";

        //Today's date and 10.00
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startDateTime = now.withHour(10).withMinute(0).withSecond(0).withNano(0);
        LocalDateTime endDateTime = startDateTime.plusMinutes(30);

        // DateTimeFormatter for formatting the time
        DateTimeFormatter formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME;


        Map<String, String> dateTimeMap = new HashMap<>();
        dateTimeMap.put("startDateTime", startDateTime.atOffset(ZoneOffset.UTC).format(formatter));
        dateTimeMap.put("endDateTime", endDateTime.atOffset(ZoneOffset.UTC).format(formatter));
        dateTimeMap.put("subject", "User Token Meeting");

        //map to string
        ObjectMapper objectMapper = new ObjectMapper();
        String requestBody = objectMapper.writeValueAsString(dateTimeMap);


        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(requestUrl))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer "+ TokenConstants.ACCESS_TOKEN)
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        log.info("MEET RESPONSE : " + response.body());
        log.info("Meet Created successfully");

        JsonNode jsonResponse = objectMapper.readTree(response.body());


        String joinUrl = jsonResponse.path("joinUrl").asText();

        emailService.sendSimpleMessage("Meeting Alert", joinUrl);

    }
/*
    @Override
    public void sendMail(String joinMeetingUrl) throws IOException, InterruptedException {
        String requestUrl = "https://graph.microsoft.com/v1.0/me/sendMail";

        String body = MailHelper.createMailBody(joinMeetingUrl);

        HttpRequest httpRequest  = HttpRequest.newBuilder()
                .uri(URI.create(requestUrl))
                .header("Content-Type","application/json")
                .header("Authorization","Bearer "+ TokenConstants.ACCESS_TOKEN)
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .build();

        HttpResponse<String> response = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());

        log.info("Mail Response Body : " + response.body() );

        log.info("Mail Sent successfully");
        tokenManager.refreshToken();

    }

*/


}
