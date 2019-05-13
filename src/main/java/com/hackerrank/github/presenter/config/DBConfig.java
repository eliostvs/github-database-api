package com.hackerrank.github.presenter.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EntityScan(basePackages = {"com.hackerrank.github.persistence.jpa.entities"})
@EnableJpaRepositories(basePackages = {"com.hackerrank.github.persistence.jpa.repositories"})
public class DBConfig {
}
