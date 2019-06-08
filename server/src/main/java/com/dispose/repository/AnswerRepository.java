package com.dispose.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dispose.model.Answer;
import com.dispose.model.Result;

public interface AnswerRepository extends JpaRepository<Answer, Long> {

	Optional<List<Answer>> findByResult(Result result);
}
