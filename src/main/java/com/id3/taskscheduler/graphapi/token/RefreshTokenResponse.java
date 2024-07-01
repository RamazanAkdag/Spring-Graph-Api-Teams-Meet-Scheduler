package com.id3.taskscheduler.graphapi.token;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RefreshTokenResponse {
    private String token_type;
    private String scope;
    private String expires_in;
    private String ext_expires_in;
    private String access_token;
    private String refresh_token;
}
