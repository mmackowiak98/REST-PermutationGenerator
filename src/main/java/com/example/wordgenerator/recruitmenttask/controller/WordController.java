package com.example.wordgenerator.recruitmenttask.controller;


import com.example.wordgenerator.recruitmenttask.service.WordService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;


@RestController
public class WordController {

    private final WordService wordService;


    public WordController(WordService wordService) {
        this.wordService = wordService;
    }

    @GetMapping("/get")
    public CompletableFuture<ResponseEntity> getWords() {
        return wordService.findAll().thenApply(ResponseEntity::ok);
    }

    @RequestMapping(value = "/generate", method = RequestMethod.POST)
    public CompletableFuture<ResponseEntity> generateAndSave(@RequestParam("maxLength") int maxLength,
                                      @RequestParam("minLength") int minLength,
                                      @RequestParam("howMany") int howMany,
                                      @RequestParam("chars") String chars) {
        return wordService.saveAndWriteGenerated(maxLength, minLength, howMany, chars).thenApply(ResponseEntity::ok);
    }

    @GetMapping("/threads")
    public int howManyThreadsRunning(){
        return wordService.threadCounter();
    }


}
