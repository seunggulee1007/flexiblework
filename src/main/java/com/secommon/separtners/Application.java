package com.secommon.separtners;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Slf4j
@SpringBootApplication
@ConfigurationPropertiesScan
@EnableJpaAuditing
public class Application {

    public static void main ( String[] args ) {
        SpringApplication.run( Application.class, args );
    }

}
