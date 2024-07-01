package com.id3.taskscheduler.graphapi.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class User {

    private String[] businessPhones;
    private String displayName;
    private String givenName;
    private String jobTitle;
    private String mail;
    private String mobilePhone;
    private String officeLocation;
    private String preferredLanguage;
    private String surname;
    private String userPrincipalName;
    private String id;
}
