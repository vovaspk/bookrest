package com.vspk.bookrest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class BookrestApplication {

    public static void main(String[] args) {
        SpringApplication.run(BookrestApplication.class, args);
    }

}
