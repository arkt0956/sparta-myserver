package com.nbcamp.myserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class MyserverApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyserverApplication.class, args);
    }

}
