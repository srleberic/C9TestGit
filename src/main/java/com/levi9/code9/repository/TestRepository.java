package com.levi9.code9.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.levi9.code9.model.Question;
import com.levi9.code9.model.Test;

@Repository
public interface TestRepository extends JpaRepository<Test, Long> {
	
	/**
	 * Find and return tests containing the question.
	 * 
	 * @param question
	 *            the question
	 * @return list of tests containing the question
	 */
	@Query(value = "SELECT t FROM Test t where :q MEMBER OF t.questions")
	public List<Test> findByContainsQuestion(@Param("q") Question question);

}
