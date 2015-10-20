/**
 * 
 */
package com.levi9.code9.repository;

import java.util.List;

import com.levi9.code9.model.Question;

/**
 * @author s.racicberic
 *
 */
public interface QuestionRepository extends BaseRepository<Question> {
	
	List<Question> findQuestionsByCategoryId(Long categoryId);
	
	List<Question> findQuestionsByTestId(Long testId);

}
