package com.agrotech.post.infrastructure.profile;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;


@Component
public class AdvisorRestClient  {
    private final RestTemplate restTemplate;
    private final String baseUrl;

    public  AdvisorRestClient(RestTemplate restTemplate,
                                      @Value("http://localhost:8093") String baseUrl) {
        this.restTemplate = restTemplate;
        this.baseUrl = baseUrl;
    }

    public Optional<Boolean> checkAdvisorExists(Long advisorId) {
       try{
           var response = restTemplate.getForEntity(baseUrl + "/api/v1/advisors/" + advisorId, Boolean.class);
           if (response.getStatusCode().is2xxSuccessful()){
                return Optional.ofNullable(response.getBody());
              } else {
                return Optional.empty();
           }
       } catch (Exception e) {
           return Optional.empty();
       }
    }
}


