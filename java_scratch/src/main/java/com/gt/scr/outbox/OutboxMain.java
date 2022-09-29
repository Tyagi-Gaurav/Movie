package com.gt.scr.outbox;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.reactive.config.EnableWebFlux;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * One key challenge when using messaging is atomically updating the database and publishing a message. A good
 * solution is to use the Transactional outbox pattern and first write the message to the database as part of the
 * database transaction. A separate process then retrieves the message from the database using either the
 * Polling publisher pattern or the Transaction log tailing pattern and publishes it to the message broker.
 */
@SpringBootApplication
@ComponentScan({"com.gt.scr.outbox"})
@EnableWebFlux
public class OutboxMain {
    public static void main(String[] args) throws SQLException {
        /*
            1. Use Spring config to start database with 2 tables
            2. Use Spring config to start kafka with single topic
            3. Single Message Processor to read from kafka topic
            4. 3 Message Pollers to read, update version & status, send to kafka and delete the record.
            5. 2 parallel Traffic creators
            6. 3 Main processors that receive traffic and insert records in DB in 2 tables.
         */
        final SpringApplication springApplication = new SpringApplication(OutboxMain.class);
        final ConfigurableApplicationContext applicationContext = springApplication.run(args);

        final DataSource dataSource = applicationContext.getBean(DataSource.class);
        System.out.println(dataSource.getConnection());
    }
}
