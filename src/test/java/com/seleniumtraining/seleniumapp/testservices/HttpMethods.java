package com.seleniumtraining.seleniumapp.testservices;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

@Service
public class HttpMethods {

    // metodi Auth0:n JWT-tokenin hakemiseen, ei käytössä enään mutta jätetään
    // varalta
    public String getJwtTokenFromAuth0(String username, String password, String auth0Domain, String audience,
            String clientId, String clientSecret) {

        String endpointUrl = "%soauth/token".formatted(auth0Domain);

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));

        HttpEntity<String> request = new HttpEntity<>("""
                {
                    "grant_type": "password",
                    "username": "%s",
                    "password": "%s",
                    "client_id": "%s",
                    "client_secret": "%s",
                    "audience": "%s",
                    "scope": "read:yritykset write:yritykset delete:yritykset admin:all"
                }
                """.formatted(username, password, clientId, clientSecret, audience), headers);

        ResponseEntity<String> response = restTemplate.postForEntity(endpointUrl, request, String.class);

        // poimitaan jwt vastauksesta
        String responseBody = response.getBody();
        if (responseBody != null && responseBody.contains("access_token")) {
            return responseBody.split("\"access_token\":\"")[1].split("\"")[0];
        } else {
            throw new RuntimeException("JWT-tokenin haku epäonnistui: " + response.getStatusCode());
        }
    }

}
