package com.gt.scr.movie;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.TestPropertySourceUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.utility.DockerImageName;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.testcontainers.containers.MySQLContainer.MYSQL_PORT;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest(classes = Application.class,
        properties = {
                "management.endpoint.health.enabled=true",
                "management.endpoints.web.exposure.include=*",
                "management.server.port=0",
                "management.port=0"
        },
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(initializers = ApplicationTest.Initializer.class)
@EnableAutoConfiguration
class ApplicationTest {
    @Autowired
    private WebApplicationContext webApplicationContext;

    @LocalServerPort
    private int port;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldLoadApplication() {
        assertThat(webApplicationContext).isNotNull();
    }

    @Test
    void shouldReturnStatusAsOK() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/status")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        assertThat(mvcResult).isNotNull();
        assertThat(mvcResult.getResponse().getContentAsString()).isEqualTo("OK");
    }

    public static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

        static MySQLContainer mySQL = new MySQLContainer<>(DockerImageName.parse("mysql"))
                .withUsername("root")
                .withPassword("password")
                .withDatabaseName("movieMetadata");

        @Override
        public void initialize(ConfigurableApplicationContext applicationContext) {
            mySQL.start();

            // Override Redis configuration
            String mysqlContainerIP = "mysql.host=" + mySQL.getContainerIpAddress();
            String mysqlContainerPort = "mysql.port=" + mySQL.getMappedPort(MYSQL_PORT);
            String mysqlContaineruser = "mysql.user=root";
            TestPropertySourceUtils.addInlinedPropertiesToEnvironment(applicationContext,
                    mysqlContainerIP,
                    mysqlContainerPort,
                    mysqlContaineruser);
        }
    }
}