package com.levi9.code9.repository.imp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.levi9.code9.model.Category;
import com.levi9.code9.model.Question;
import com.levi9.code9.model.Test;
import com.levi9.code9.repository.QuestionRepository;
import com.levi9.code9.repository.TestRepository;

/**
 * @author s.racicberic
 *
 */
@Repository
public class JdbcTestRepository implements TestRepository {
	
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private QuestionRepository questionRepository;
	
	@Autowired
	private void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public List<Test> findAll() {
		TestRowCallbackHandler testRowCallbackHandler = new TestRowCallbackHandler();
		jdbcTemplate.query("select t.id, t.name, t.create_date, t.created_by, "
				+ "q.id, q.content, c.id, c.name "
				+ "from test_questions tq "
				+ "left join question q on tq.question_id = q.id "
				+ "right join test t on tq.test_id = t.id "
				+ "left join category c on q.category_id = c.id", 
				testRowCallbackHandler);
		return testRowCallbackHandler.getTests();
	}

	@Override
	public Test findById(Long id) {
		TestRowCallbackHandler testRowCallbackHandler = new TestRowCallbackHandler();
		jdbcTemplate.query("select t.id, t.name, t.create_date, t.created_by, "
				+ "q.id, q.content, c.id, c.name "
				+ "from test_questions tq "
				+ "left join question q on tq.question_id = q.id "
				+ "right join test t on tq.test_id = t.id "
				+ "left join category c on q.category_id = c.id "
				+ "where t.id = ?", 
				testRowCallbackHandler, id);
		return testRowCallbackHandler.getTest();
	}

	@Override
	public Test save(final Test entity) {
		KeyHolder holder = new GeneratedKeyHolder();
		if (entity.getId() != null) {
			jdbcTemplate
					.update("update test set name = ?, create_date = ?, created_by = ? where id = ?",
							entity.getName(), entity.getCreateDate(),
							entity.getCreatedBy(), entity.getId());
		} else {
			jdbcTemplate.update(new PreparedStatementCreator() {
				@Override
				public PreparedStatement createPreparedStatement(
						Connection connection) throws SQLException {
					PreparedStatement ps = connection
							.prepareStatement(
									"insert into test (name, create_date, created_by) values (?, ?, ?)",
									Statement.RETURN_GENERATED_KEYS);
					ps.setString(1, entity.getName());
					ps.setDate(2, new java.sql.Date(entity.getCreateDate()
							.getTime()));
					ps.setString(3, entity.getCreatedBy());
					return ps;
				}
			}, holder);
			entity.setId(holder.getKey().longValue());
		}
		updateQuestions(entity);
		return entity;
	}

	@Override
	public void remove(Long id) throws IllegalArgumentException {
		jdbcTemplate.update("delete from test_questions where test_id = ?", id);
		jdbcTemplate.update("delete from test where id = ?", id);
	}
	
	/**
	 * Helper implementation for updating list of questions for a test. It first
	 * removes questions that are no longer in the list, and than inserts the
	 * new records or updates existing.
	 * 
	 * @param test
	 *            the Test which contains questions to be updated
	 */
	private void updateQuestions(Test test) {
		List<Question> existingQuestions = questionRepository
				.findQuestionsByTestId(test.getId());
		for (Question existingQuestion : existingQuestions) {
			if (!test.getQuestions().contains(existingQuestion)) {
				jdbcTemplate.update(
						"delete from test_questions where question_id = ?",
						existingQuestion.getId());
			}
		}
		for (Question question : test.getQuestions()) {
			if (!existingQuestions.contains(question)) {
				jdbcTemplate
						.update("insert into test_questions (test_id, question_id) values (?, ?)",
								test.getId(), question.getId());
			}
		}
	}
	
	/**
	 * RowCallbackHandler for mapping join query results into graph of Question,
	 * Category and Answer entities.
	 */
	private static final class TestRowCallbackHandler implements
			RowCallbackHandler {

		private List<Test> result = new ArrayList<>();

		private Test currentTest = null;
		private Question currentQuestion = null;
		private Category currentCategory = null;

		@Override
		public void processRow(ResultSet resultSet) throws SQLException {
			final Long testId = resultSet.getLong("t.id");
			if (currentTest == null || !testId.equals(currentTest.getId())) {
				currentTest = new Test();
				currentTest.setId(testId);
				currentTest.setName(resultSet.getString("t.name"));
				currentTest.setCreateDate(resultSet.getDate("t.create_date"));
				currentTest.setCreatedBy(resultSet.getString("t.created_by"));
				result.add(currentTest);
			}

			final Long questionId = resultSet.getLong("q.id");
			if (!resultSet.wasNull()
					&& (currentQuestion == null || !questionId
							.equals(currentQuestion.getId()))) {
				currentQuestion = new Question();
				currentQuestion.setId(questionId);
				currentQuestion.setContent(resultSet.getString("q.content"));
				currentTest.getQuestions().add(currentQuestion);
			}

			final Long categoryId = resultSet.getLong("c.id");
			if (!resultSet.wasNull()
					&& (currentCategory == null || !categoryId
							.equals(currentCategory.getId()))) {
				currentCategory = new Category();
				currentCategory.setId(categoryId);
				currentCategory.setName(resultSet.getString("c.name"));
				currentQuestion.setCategory(currentCategory);
			}
		}

		public Test getTest() {
			if (result.isEmpty()) {
				return null;
			}
			if (result.size() > 1) {
				throw new IllegalStateException(
						"More than one test in a result.");
			}
			return result.get(0);
		}

		public List<Test> getTests() {
			return result;
		}
	}

}
