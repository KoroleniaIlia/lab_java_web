package com.example.lab_java_web.config;

import com.example.lab_java_web.repository.implementation.NaturalIdRepositoryImpl;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
@Configuration
@EnableJpaAuditing
@EnableJpaRepositories(value = "com.example.lab_java_web.repository",
        repositoryBaseClass = NaturalIdRepositoryImpl.class)
public class JpaRepositoryConfiguration {
}