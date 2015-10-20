package com.levi9.code9.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.levi9.code9.model.Answer;
import com.levi9.code9.model.Question;
import com.levi9.code9.repository.QuestionRepository;
import com.levi9.code9.service.QuestionService;

@Service
public class QuestionServiceImpl implements QuestionService {
	
	@Autowired
	private QuestionRepository questionRepository;

	@Override
	public Question findById(Long id) {
		return questionRepository.findById(id);
	}

	@Override
	public List<Question> findAll() {
		return questionRepository.findAll();
	}

	@Override
	public Question save(Question question) {
		return questionRepository.save(question);
	}

	@Override
	public void remove(Long id) throws IllegalArgumentException {
		Question question = questionRepository.findById(id);
		if (question == null) {
			throw new IllegalArgumentException(String.format(
							"Question with id=%d does not exist.",
							id));
		}
		questionRepository.remove(id);
	}

	@Override
	public List<Question> findByCategoryId(Long categoryId) {
		return questionRepository.findQuestionsByCategoryId(categoryId);
	}

	@Override
	public void addAnswer(Question question) {
		Answer answer = new Answer();
		question.getAnswers().add(answer);
	}

	@Override
	public void removeAnswer(Question question, Long answerId)
			throws IllegalArgumentException {
		System.out.println(answerId);
		if (answerId == null || answerId < 0
				|| answerId > question.getAnswers().size() - 1) {
			throw new IllegalArgumentException(String.format(
					"Error: Tried to delete non-existing entity with id=%d.",
					answerId));
		}
		question.getAnswers().remove(answerId.intValue());
	}

}
