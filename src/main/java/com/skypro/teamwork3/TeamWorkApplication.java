package com.skypro.teamwork3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;


@SpringBootApplication
public class TeamWorkApplication {
    public static void main(String[] args) {
        SpringApplication.run(TeamWorkApplication.class, args);
    }
}
