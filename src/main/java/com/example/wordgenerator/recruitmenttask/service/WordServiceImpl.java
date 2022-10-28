package com.example.wordgenerator.recruitmenttask.service;

import com.example.wordgenerator.recruitmenttask.configuration.AsyncConfiguration;
import com.example.wordgenerator.recruitmenttask.entity.Words;
import com.example.wordgenerator.recruitmenttask.repository.WordRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.LongStream;

@Service
public class WordServiceImpl implements WordService {

    private static final Logger LOGGER = LoggerFactory.getLogger(WordServiceImpl.class);
    private final WordRepository wordRepository;
    private final ThreadLocal<CopyOnWriteArrayList<Words>> listOfWords = ThreadLocal.withInitial(CopyOnWriteArrayList::new);
    private ThreadLocal<AtomicLong> counter = ThreadLocal.withInitial(AtomicLong::new);
    private final AsyncConfiguration asyncConfiguration;
    public WordServiceImpl(WordRepository wordRepository, AsyncConfiguration asyncConfiguration) {
        this.wordRepository = wordRepository;
        this.asyncConfiguration = asyncConfiguration;
    }


    /**
     *
     * @param maxLength
     * @param minLength
     * @param howMany
     * @return - possibility true/false of generating given amount of strings from given chars
     * Not used - couldn't come up for correct permutation with repetition algo
     */
    private boolean isGeneratingPossible(int maxLength, int minLength, int howMany) {
        long numberOfPossibilities = howManyPossibilities(maxLength, minLength);
        if (numberOfPossibilities < howMany) {
            throw new IllegalArgumentException("Can't make that much permutation from given chars");
        }
        return true;
    }

    private long howManyPossibilities(int maxLength, int minLength) {

        Long sumOfPossibilities = 0L;
        while (minLength <= maxLength) {
            sumOfPossibilities += LongStream.rangeClosed(1, minLength)
                    .reduce(1, (long x, long y) -> x * y);
            minLength++;
        }
        return sumOfPossibilities;
    }

    /**
     * Method writing generated words to file
     */
    @Async
    void writeToFile() {
        try {
            FileWriter fileWriter = new FileWriter("output.txt", true);
            for (Words word : listOfWords.get()) {
                fileWriter.write(word.getWord() + "\n");
            }
            fileWriter.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void generator(String givenChars, int maxLength, int minLength, int howMany) {

        generator("", givenChars, maxLength, minLength, howMany);
    }

    /**
     * Recursive method used to generate string permutation
     *
     * @param buildedString - String that method is building on
     * @param str - given chars
     * @param maxLength - maximum length of string
     * @param minLength - minimum length of string
     * @param howMany - how many strings user wants
     */

    @Async
    void generator(String buildedString, String str, int maxLength, int minLength, int howMany) {
        int strLength = str.length();

        if (!buildedString.equals("")) {

            if (buildedString.length() >= minLength && buildedString.length() <= maxLength && counter.get().get() < howMany) {

                listOfWords.get().add(new Words(buildedString));

                counter.get().getAndIncrement();
            }
        }
        if (strLength > 0) {
            for (int i = 0; i < strLength; i++)
                generator(buildedString + str.charAt(i), str.substring(0, i) + str.substring(i + 1, strLength), maxLength, minLength, howMany);

        }
    }

    @Override
    public CompletableFuture<List<Words>> saveAndWriteGenerated(int maxLength, int minLength, int howMany, String chars) {

        LOGGER.info("Send request to generate words");
        generator(chars, maxLength, minLength, howMany);
        LOGGER.info("Now writing to File");
        writeToFile();
        LOGGER.info("Saving words");
        saveAll(listOfWords.get());
        LOGGER.info("Request completed");
        return CompletableFuture.completedFuture(listOfWords.get());
    }

    @Override
    public CompletableFuture<List<Words>> findAll() {

        LOGGER.info("Request to get a list of words");

        List<Words> all = wordRepository.findAll();

        return CompletableFuture.completedFuture(all);
    }

    @Override
    public CompletableFuture<List<Words>> saveAll(List<Words> words) {
        if (words == null) {
            throw new IllegalArgumentException("Words is null");
        }
        wordRepository.saveAll(words);

        return CompletableFuture.completedFuture(words);
    }

    @Override
    public int threadCounter() {
        return asyncConfiguration.executor.getActiveCount();
    }

}
