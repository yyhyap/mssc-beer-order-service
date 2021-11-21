package com.yyh.msscbeerorderservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Task Configuration - enable asyc tasks
 */
// enable use of @Schedule
@EnableScheduling
// enable use of @Async
@EnableAsync
@Configuration
public class TaskConfig {
    @Bean
    TaskExecutor taskExecutor() {
        return new SimpleAsyncTaskExecutor();
    }
}
