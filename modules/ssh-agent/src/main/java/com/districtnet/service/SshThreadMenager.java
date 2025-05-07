package com.districtnet.service;

import jakarta.annotation.PreDestroy;
import org.springframework.stereotype.Service;


import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class SshThreadMenager {

    private final ConcurrentHashMap<String, ExecutorService> executors = new ConcurrentHashMap<>();

    public ExecutorService getExecutorFor(String threadName) {
        return executors.computeIfAbsent(threadName, key -> Executors.newSingleThreadExecutor());
    }

    public void submit(String threadname,Runnable runnable) {
        getExecutorFor(threadname).submit(runnable);
    }

    @PreDestroy
    public void shutdown() {
        executors.forEach((key, executor) -> executor.shutdown());
    }




}
