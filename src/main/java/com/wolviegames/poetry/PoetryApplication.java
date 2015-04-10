package com.wolviegames.poetry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class PoetryApplication {

    public static void main(String[] args){
        ApplicationContext ctx = SpringApplication.run(PoetryApplication.class, args);

        System.out.println("Starting Poetry Genetic Algorithm Web Application...");
    }
}
