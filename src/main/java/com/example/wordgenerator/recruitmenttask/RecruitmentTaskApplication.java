package com.example.wordgenerator.recruitmenttask;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class RecruitmentTaskApplication {

    public static void main(String[] args) {
        SpringApplication.run(RecruitmentTaskApplication.class, args);
    }

}
