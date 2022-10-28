package com.example.wordgenerator.recruitmenttask.repository;

import com.example.wordgenerator.recruitmenttask.entity.Words;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.scheduling.annotation.Async;

import java.util.List;

public interface WordRepository extends JpaRepository<Words, Long> {

    @Override
    @Async
    <S extends Words> List<S> saveAll(Iterable<S> entities);
}
