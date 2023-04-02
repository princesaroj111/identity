package com.demo.identity.digimocker;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public class DigiMockerConnector
{
    private static DigiMockerResponse fetchCollegeCredential(String name, String email) throws JsonProcessingException
    {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:3000/api/docs/IITKDegree";

        // create header
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("auth-token", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOiI2NDI5MDZlNjllYWQ0NDM4OWRiY2VhYTYiLCJpYXQiOjE2ODA0MTExMzR9.mKzjDsgf3JooKYzOylREH9JdaYni7mSbRz3sDQZWAfk");

        // create request body
        DigiMockerRequest request = new DigiMockerRequest();
        request.setEmail(email);
        request.setName(name);

        // create http entity with headers and request body
        HttpEntity<DigiMockerRequest> requestEntity = new HttpEntity<>(request, headers);

        // send post request
        ResponseEntity<List> responseEntity = restTemplate.postForEntity(url, requestEntity, List.class);
        return new ObjectMapper().readValue(new ObjectMapper().writeValueAsString(responseEntity.getBody().get(0)), DigiMockerResponse.class);
    }

    public static boolean isCollegeCredentialValid(String name, String email, String collegeName)
    {
        try
        {
            DigiMockerResponse response = fetchCollegeCredential(name, email);
            return response != null && response.getEmail().equals(email) && response.getCollegeName().equals(collegeName);
        }
        catch (Exception e)
        {
            return false;
        }
    }
}
