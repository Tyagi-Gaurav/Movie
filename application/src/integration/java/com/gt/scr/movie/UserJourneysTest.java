package com.gt.scr.movie;

import com.gt.scr.movie.resource.domain.AccountCreateRequestDTO;
import com.gt.scr.movie.resource.domain.LoginRequestDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@EnableAutoConfiguration
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = Application.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(initializers = Initializer.class)
@AutoConfigureMockMvc
public class UserJourneysTest {
    @Autowired
    private ScenarioExecutor scenarioExecutor;

    @Test
    public void createUserTest() {
        AccountCreateRequestDTO accountCreateRequestDTO =
                TestObjectBuilder.userAccountCreateRequestDTO();
        scenarioExecutor
                .when().userIsCreatedWith(accountCreateRequestDTO)
                .then().statusIs(204);
    }

    @Test
    public void createUserTestWithInvalidData() {
        AccountCreateRequestDTO invalidAccountCreateRequest =
                TestObjectBuilder.invalidUserAccountCreateRequestDTO();
        scenarioExecutor
                .when().userIsCreatedWith(invalidAccountCreateRequest)
                .then().statusIs(400);
    }

    @Test
    public void createUserAndLoginTest() {
        AccountCreateRequestDTO accountCreateRequestDTO =
                TestObjectBuilder.userAccountCreateRequestDTO();
        LoginRequestDTO loginRequestDTO = TestObjectBuilder.loginRequestUsing(accountCreateRequestDTO).build();
        scenarioExecutor
                .when().userIsCreatedWith(accountCreateRequestDTO)
                .then().statusIs(204)
                .and().adminUserLoginsWith(loginRequestDTO)
                .then().statusIs(200);
    }
}
