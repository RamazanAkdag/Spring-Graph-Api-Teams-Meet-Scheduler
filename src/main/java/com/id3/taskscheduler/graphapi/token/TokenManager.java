package com.id3.taskscheduler.graphapi.token;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;

@Component
@RequiredArgsConstructor
@Slf4j
public class TokenManager{

    private final ObjectMapper objectMapper;
    private final HttpClient client;





    public RefreshTokenResponse refreshToken() throws IOException, InterruptedException {

        Map<Object, Object> data = new HashMap<>();
        data.put("grant_type", "refresh_token");
        data.put("client_id", TokenConstants.CLIENT_ID);
        data.put("refresh_token", TokenConstants.REFRESH_TOKEN);
        data.put("redirect_uri", TokenConstants.REDIRECT_URI);
        data.put("client_secret", TokenConstants.CLIENT_SECRET);
        data.put("scope", "User.Read Mail.Read");

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://login.microsoftonline.com/" + TokenConstants.TENANT_ID + "/oauth2/v2.0/token"))
                .header("Content-Type", "application/x-www-form-urlencoded")
                .POST(ofFormData(data))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());



        RefreshTokenResponse res = objectMapper.readValue(response.body(), RefreshTokenResponse.class);

        TokenConstants.setAccessAndRefreshToken(res.getAccess_token(), res.getRefresh_token());

        log.info("Refreshed Access token : " + res.getAccess_token());
        return res;

    }

    private static HttpRequest.BodyPublisher ofFormData(Map<Object, Object> data) {
        StringJoiner sj = new StringJoiner("&");
        for (Map.Entry<Object, Object> entry : data.entrySet()) {
            sj.add(entry.getKey() + "=" + entry.getValue());
        }
        return HttpRequest.BodyPublishers.ofString(sj.toString());
    }
}
