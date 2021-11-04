package com.gt.scr.movie;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.support.TestPropertySourceUtils;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.utility.DockerImageName;

import static org.testcontainers.containers.MySQLContainer.MYSQL_PORT;


public class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    static MySQLContainer mySQL = new MySQLContainer<>(DockerImageName.parse("mysql"))
            .withUsername("root")
            .withPassword("password")
            .withDatabaseName("movieMetadata")
            .withReuse(true);

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        mySQL.start();

        // Override MySql configuration
        String mysqlContainerIP = "mysql.host=" + mySQL.getContainerIpAddress();
        String mysqlContainerPort = "mysql.port=" + mySQL.getMappedPort(MYSQL_PORT);
        String mysqlContainerUser = "mysql.user=root";

        TestPropertySourceUtils.addInlinedPropertiesToEnvironment(applicationContext,
                mysqlContainerIP,
                mysqlContainerPort,
                mysqlContainerUser);
    }
}