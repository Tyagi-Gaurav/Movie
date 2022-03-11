package com.gt.scr.movie.test;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

public class TestDataSetup {
    private static final String hostName = "localhost";
    private static final int port = 8080;
    private static final String[] user_role_firstNames = {"Jack", "John", "William", "Harry", "Chris", "Charles"};
    private static final String[] admin_role_firstNames = {"Frank", "Roger", "Melvyn"};
    private static final String[] lastNames = {"Beans", "Fitz", "Nutt", "Evans", "Timpson", "Redwood"};

    public static void main(String[] args) {
        new TestDataSetup().run();
    }

    private void run() {
        RestTemplate restTemplate = getRestTemplate();

        createUsers(restTemplate, user_role_firstNames, "USER");
        createUsers(restTemplate, admin_role_firstNames, "ADMIN");
    }

    private void createUsers(RestTemplate restTemplate, String[] firstNames, String role) {
        String uri = String.format("http://%s:%d/api/user/account/create", hostName, port);
        for (int i = 0; i < firstNames.length; i++) {
            String payload = String.format("{\"userName\" : \"%s\", \"password\" : \"%s\",\"firstName\" : \"%s\", \"lastName\" : " +
                            "\"%s\", \"role\" : \"%s\"}",
                    firstNames[i].toLowerCase(),
                    RandomStringUtils.randomAlphabetic(8),
                    firstNames[i],
                    lastNames[i],
                    role);

            //System.out.println(payload);

            HttpHeaders headers = new HttpHeaders();
            headers.set(HttpHeaders.CONTENT_TYPE, "application/vnd+account.create.v1+json");
            HttpEntity<String> request = new HttpEntity<>(payload, headers);
            restTemplate.postForEntity(uri, request, String.class);
        }
    }

    private RestTemplate getRestTemplate() {
        SimpleClientHttpRequestFactory simpleClientHttpRequestFactory = new SimpleClientHttpRequestFactory();
        simpleClientHttpRequestFactory.setConnectTimeout(10000);
        return new RestTemplate(simpleClientHttpRequestFactory);
    }
}
