package com.gt.scr.movie.test.config;

import com.gt.scr.movie.test.domain.TestAccountCreateRequestDTO;
import org.springframework.stereotype.Component;

@Component
public class ScenarioContext {

    private TestAccountCreateRequestDTO userCredentialsRequest;
    private TestAccountCreateRequestDTO adminCredentialsRequest;
    private String regularUserId;
    private String lastUserName;


    public String getLastUserName() {
        return lastUserName;
    }

    public void setLastUserName(String lastUserName) {
        this.lastUserName = lastUserName;
    }

    public void storeCredentialsRequest(TestAccountCreateRequestDTO testAccountCreateRequestDTO) {
        if (testAccountCreateRequestDTO.role().equals("USER")) {
            this.userCredentialsRequest = testAccountCreateRequestDTO;
        } else {
            this.adminCredentialsRequest = testAccountCreateRequestDTO;
        }
    }

    public TestAccountCreateRequestDTO getUserCredentialsRequest() {
        return userCredentialsRequest;
    }

    public TestAccountCreateRequestDTO getAdminCredentialsRequest() {
        return adminCredentialsRequest;
    }

    public void setUserIdForRegularUser(String userId) {
        this.regularUserId = userId;
    }

    public String getRegularUserId() {
        return regularUserId;
    }

    public TestAccountCreateRequestDTO getCredentials(String role) {
        if (role.equals("USER")) {
            return this.userCredentialsRequest;
        } else {
            return this.adminCredentialsRequest;
        }
    }
}