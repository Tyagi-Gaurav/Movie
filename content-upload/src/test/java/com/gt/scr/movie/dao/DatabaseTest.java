package com.gt.scr.movie.dao;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

import javax.sql.DataSource;
import java.net.URL;

import static org.assertj.core.api.Assertions.assertThat;

@Import(DatabaseTest.TestDatabaseContextConfiguration.class)
public abstract class DatabaseTest {
    private static final Logger LOG = LoggerFactory.getLogger(DatabaseTest.class);

    @TestConfiguration
    static class TestDatabaseContextConfiguration {

        @Bean
        public DataSource inMemoryEventDataSource() {
            ComboPooledDataSource cpds = new ComboPooledDataSource();

            try {
                String databaseName = RandomStringUtils.randomAlphabetic(6);
                LOG.info("Initializing in-memory database for dbName: {}", databaseName);
                URL resource = StreamMetaDataRepositoryTest.TestDatabaseContextConfiguration.class.getClassLoader()
                        .getResource("db.changelog/dbchangelog.sql");
                assertThat(resource).describedAs("Unable to find sql file to create database").isNotNull();
                String tempFile = resource.toURI().getRawPath();
                cpds.setDriverClass("org.h2.Driver");
                cpds.setAutoCommitOnClose(true);
                String jdbcUrl = String.format("jdbc:h2:mem:%s;MODE=MySQL;" +
                        "DB_CLOSE_ON_EXIT=TRUE;INIT=RUNSCRIPT FROM '%s'", databaseName, tempFile);
                cpds.setJdbcUrl(jdbcUrl);
                LOG.info("Using JDBC Url: {}", jdbcUrl);
                LOG.info("Number of connections: {}", cpds.getNumConnections());

            } catch (Exception e) {
                throw new IllegalArgumentException(e);
            }

            return cpds;
        }
    }
}
