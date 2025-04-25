package com.app.challenge;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;

@Slf4j
@SpringBootApplication
@SuppressWarnings({"PMD", "checkstyle:HideUtilityClassConstructor"})
public class ChallengeApplication {

    public static void main(String[] args) {
        int mb = 1024 * 1024;
        MemoryMXBean memoryBean = ManagementFactory.getMemoryMXBean();
        long xmx = memoryBean.getHeapMemoryUsage().getMax() / mb;
        long xms = memoryBean.getHeapMemoryUsage().getInit() / mb;
        log.info("Initial Memory (xms): {} MB", xms);
        log.info("Max Memory (xmx): {} MB", xmx);
        SpringApplication.run(ChallengeApplication.class, args);
    }

}
