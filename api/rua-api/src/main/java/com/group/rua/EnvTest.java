package com.group.rua;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class EnvTest {

    @Value("${DBPASS:NULL}")
    private String dbPass;

    @Value("${spring.datasource.url:NULL}")
    private String datasourceUrl;

    private final JdbcTemplate jdbcTemplate;

    public EnvTest(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @PostConstruct
    public void test() {

        System.out.println("========== DEBUG ==========");

        System.out.println("ENV DBPASS = [" + System.getenv("DBPASS") + "]");
        System.out.println("SPRING DBPASS = [" + dbPass + "]");

        System.out.println("ENV SPRING_DATASOURCE_URL = ["
                + System.getenv("SPRING_DATASOURCE_URL") + "]");

        System.out.println("SPRING DATASOURCE URL = ["
                + datasourceUrl + "]");

        try {
            String currentDb = jdbcTemplate.queryForObject(
                    "SELECT DATABASE()",
                    String.class
            );

            System.out.println("DATABASE() = [" + currentDb + "]");
        }
        catch (Exception e) {
            System.out.println("Error obteniendo DATABASE():");
            e.printStackTrace();
        }

        System.out.println("===========================");
    }
}