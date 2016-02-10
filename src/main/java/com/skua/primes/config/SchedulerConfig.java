package com.skua.primes.config;

import com.skua.primes.service.PrimesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
public class SchedulerConfig {

    @Autowired
    private PrimesService primesService;

    /*
     * Run every 15 min.
     */
    @Scheduled(cron = "0 0/15 * * * *")
    public void triggerPendingTasks() throws Exception {
        this.primesService.pruneCache(30L);
    }
}
