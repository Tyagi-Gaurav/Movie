package com.gt.scr.movie;

import com.gt.scr.movie.command.TestExecutor;
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
public class CreateUserTest {
    @Autowired
    private TestExecutor testExecutor;

    @Test
    public void createUserTest() {
        AccountCreateRequestDTO accountCreateRequestDTO =
                TestObjectBuilder.accountCreateRequestDTO().build();
        testExecutor
                .when().userIsCreatedWith(accountCreateRequestDTO)
                .then().statusIs(204);
    }

    @Test
    public void createUserAndLoginTest() {
        AccountCreateRequestDTO accountCreateRequestDTO =
                TestObjectBuilder.accountCreateRequestDTO().build();
        LoginRequestDTO loginRequestDTO = TestObjectBuilder.loginRequestUsing(accountCreateRequestDTO).build();
        testExecutor
                .when().userIsCreatedWith(accountCreateRequestDTO)
                .then().statusIs(204)
                .and().userLoginsWith(loginRequestDTO)
                .then().statusIs(200);
    }
}
