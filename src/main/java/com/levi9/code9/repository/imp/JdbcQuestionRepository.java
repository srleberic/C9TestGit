package com.levi9.code9.repository.imp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.levi9.code9.model.Answer;
import com.levi9.code9.model.Category;
import com.levi9.code9.model.Question;
import com.levi9.code9.repository.QuestionRepository;


/**
 * @author s.racicberic
 *
 */
@Repository
public class JdbcQuestionRepository implements QuestionRepository {
	
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public List<Question> findAll() {
		QuestionRowCallbackHandler questionRowCallbackHandler = new QuestionRowCallbackHandler();
		jdbcTemplate.query("select q.id, q.content, c.id, c.name, a.id, a.content, a.correct from question q "
				+ "left join category c on q.category_id = c.id "
				+ "left join answer a on a.question_id = q.id ", questionRowCallbackHandler);
		return questionRowCallbackHandler.getQuestions();
	}

	@Override
	public Question findById(Long id) {
		QuestionRowCallbackHandler questionRowCallbackHandler = new QuestionRowCallbackHandler();
		jdbcTemplate.query("select q.id, q.content, c.id, c.name, a.id, a.content, a.correct from question q "
				+ "left join category c on q.category_id = c.id "
				+ "left join answer a on a.question_id = q.id "
				+ "where q.id = ?", 
				questionRowCallbackHandler, id);
		return questionRowCallbackHandler.getQuestion();
	}

	@Override
	public Question save(final Question entity) {
		KeyHolder holder = new GeneratedKeyHolder();
		if (entity.getId() != null) {
			Long catId = null;
			if (entity.getCategory() != null) {
				catId = entity.getCategory().getId();
			}
			jdbcTemplate.update("update question set content = ?, category_id = ? where id = ?", 
					entity.getContent(), catId, entity.getId());
		} else {
			jdbcTemplate.update(new PreparedStatementCreator() {
				
				@Override
				public PreparedStatement createPreparedStatement(Connection conn)
						throws SQLException {
					PreparedStatement stmt = conn.prepareStatement(
							"insert into question (content, category_id) values (?, ?)",
							Statement.RETURN_GENERATED_KEYS);
					stmt.setString(1, entity.getContent());
					if (entity.getCategory() != null) {
						stmt.setLong(2, entity.getCategory().getId());
					} else {
						stmt.setNull(2, Types.BIGINT);
					}
					return stmt;
				}
			}, holder);
			entity.setId(holder.getKey().longValue());
		}
		updateAnswers(entity);
		return entity;
	}
	
	@Override
	public void remove(Long id) throws IllegalArgumentException {
		jdbcTemplate.update("delete from question where id = ?", id);
	}

	@Override
	public List<Question> findQuestionsByCategoryId(Long categoryId) {
		QuestionRowCallbackHandler questionRowCallbackHandler = new QuestionRowCallbackHandler();
		jdbcTemplate.query("select q.id, q.content, c.id, c.name, a.id, a.content, a.correct from question q "
				+ "left join category c on q.category_id = c.id "
				+ "left join answer a on a.question_id = q.id "
				+ "where c.id = ?", questionRowCallbackHandler, categoryId);
		return questionRowCallbackHandler.getQuestions();
	}

	@Override
	public List<Question> findQuestionsByTestId(Long testId) {
		QuestionRowCallbackHandler questionRowCallbackHandler = new QuestionRowCallbackHandler();
		jdbcTemplate.query("select q.id, q.content, c.id, c.name, a.id, a.content, a.correct from question q "
				+ "left join category c on q.category_id = c.id "
				+ "left join answer a on a.question_id = q.id "
				+ "left join test_questions tq on tq.question_id = q.id "
				+ "where tq.test_id = ?", questionRowCallbackHandler, testId);
		return questionRowCallbackHandler.getQuestions();
	}
	
	private void updateAnswers(Question question) {
		List<Answer> existingAnswers = jdbcTemplate.query(
				"select id, content, correct from answer where question_id = ?", 
				new AnswerRowMapper(), question.getId());
		for (Answer a : existingAnswers) {
			if (!question.getAnswers().contains(a)) {
				jdbcTemplate.update(
						"delete from answer where question_id = ? and id = ?",
						question.getId(), a.getId());
			}
		}
		for (Answer answer : question.getAnswers()) {
			jdbcTemplate.update(
					"replace into answer (id, content, correct, question_id) values (?, ?, ?, ?)",
					answer.getId(), answer.getContent(),
					answer.isCorrect(), question.getId());
		}
	}
	
	private static final class QuestionRowCallbackHandler implements 
			RowCallbackHandler {
		
		private List<Question> result = new ArrayList<>();
		
		private Question currentQuestion = null;
		private Category currentCategory = null;
		private Answer currentAnswer = null;

		@Override
		public void processRow(ResultSet rs) throws SQLException {
			final Long questionId = rs.getLong("q.id");
			if (currentQuestion == null || !questionId.equals(currentQuestion.getId())) {
				currentQuestion = new Question();
				currentQuestion.setId(questionId);
				currentQuestion.setContent(rs.getString("q.content"));
				result.add(currentQuestion);
			}
			
			final Long categoryId = rs.getLong("c.id");
			if (currentCategory == null || 
					!categoryId.equals(currentCategory.getId())) {
				currentCategory = new Category();
				currentCategory.setId(categoryId);
				currentCategory.setName(rs.getString("c.name"));
				currentQuestion.setCategory(currentCategory);
			}
			
			final Long answerId = rs.getLong("a.id");
			if (currentAnswer == null || 
					!answerId.equals(currentAnswer.getId())) {
				currentAnswer = new Answer();
				currentAnswer.setId(answerId);
				currentAnswer.setContent(rs.getString("a.content"));
				currentAnswer.setCorrect(rs.getBoolean("a.correct"));
				currentQuestion.getAnswers().add(currentAnswer);
			}
		}
		
		public Question getQuestion() {
			if (result.isEmpty()) {
				return null;
			}
			if (result.size() > 1) {
				throw new IllegalStateException("More than one question in a result.");
			}
			return result.get(0);
		}
		
		public List<Question> getQuestions() {
			return result;
		}
	}
	
	private static final class AnswerRowMapper implements RowMapper<Answer> {

		@Override
		public Answer mapRow(ResultSet rs, int rowNum) throws SQLException {
			Answer answer = new Answer();
			answer.setId(rs.getLong("id"));
			answer.setContent(rs.getString("content"));
			answer.setCorrect(rs.getBoolean("correct"));
			return answer;
		}
	}

}
