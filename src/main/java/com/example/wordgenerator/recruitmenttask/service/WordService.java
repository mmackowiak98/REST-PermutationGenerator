package com.example.wordgenerator.recruitmenttask.service;

import com.example.wordgenerator.recruitmenttask.entity.Words;
import org.springframework.scheduling.annotation.Async;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface WordService {
    @Async
    CompletableFuture<List<Words>> saveAndWriteGenerated(int maxLength, int minLength, int howMany, String chars);
    @Async
    CompletableFuture<List<Words>> findAll();

    int threadCounter();



}
