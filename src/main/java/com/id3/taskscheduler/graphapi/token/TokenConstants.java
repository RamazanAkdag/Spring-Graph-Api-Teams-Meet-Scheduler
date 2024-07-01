package com.id3.taskscheduler.graphapi.token;

public class TokenConstants {

    public static final String CLIENT_ID = "";
    public static String REFRESH_TOKEN = "";
    public static final String REDIRECT_URI = "http://localhost:8080/";
    public static final String CLIENT_SECRET = "";
    public static final String TENANT_ID = "";

    public static String ACCESS_TOKEN = "";
    public static void setAccessAndRefreshToken(String accessToken, String refreshToken) {
        ACCESS_TOKEN = accessToken;
        REFRESH_TOKEN = refreshToken;
    }
}
