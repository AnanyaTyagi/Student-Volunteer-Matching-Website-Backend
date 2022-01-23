package com.cntrl.alt.debt.gyandaan.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
public class TaskExecutorMatchingVolunteers {

    @Bean
    public Executor taskExecutor() {
        int cores = Runtime.getRuntime().availableProcessors();
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(cores);
        executor.setMaxPoolSize(cores);
        executor.setQueueCapacity(500);
        executor.setThreadNamePrefix("MatchingVolunteers-");
        executor.initialize();
        return executor;
    }
}
