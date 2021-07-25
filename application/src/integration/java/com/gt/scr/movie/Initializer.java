package com.gt.scr.movie;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.support.TestPropertySourceUtils;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.utility.DockerImageName;

import static org.testcontainers.containers.MySQLContainer.MYSQL_PORT;


public class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    private static final int REDIS_PORT = 6379;

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