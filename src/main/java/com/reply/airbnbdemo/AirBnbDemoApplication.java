package com.reply.airbnbdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class AirBnbDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(AirBnbDemoApplication.class, args);
    }

}
