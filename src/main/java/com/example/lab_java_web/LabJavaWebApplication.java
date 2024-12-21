package com.example.lab_java_web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class LabJavaWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(LabJavaWebApplication.class, args);
    }

}
