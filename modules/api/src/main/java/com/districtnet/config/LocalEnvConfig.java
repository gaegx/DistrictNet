package com.districtnet.config;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
@Profile("local") 
public class LocalEnvConfig {

    @Bean
    public Dotenv dotenv() {
        return Dotenv.configure()
                .directory("/home/gaegxh/districtnet/.env")
                .load();
    }

    @Bean
    public DataSource dataSource(Dotenv dotenv) {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl(dotenv.get("SPRING_DATASOURCE_URL"));
        dataSource.setUsername(dotenv.get("SPRING_DATASOURCE_USERNAME"));
        dataSource.setPassword(dotenv.get("SPRING_DATASOURCE_PASSWORD"));
        return dataSource;
    }
}
