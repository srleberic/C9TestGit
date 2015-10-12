/**
 * 
 */
package com.levi9.code9.service.memory;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.levi9.code9.model.Category;
import com.levi9.code9.model.Question;
import com.levi9.code9.service.CategoryService;
import com.levi9.code9.service.QuestionService;
import com.levi9.code9.service.memory.InMemoryCategoryService;

/**
 * @author s.racicberic
 *
 */
public class InMemoryQuestionServiceTest {
	
	private QuestionService service;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		service = new InMemoryQuestionService();
		
		Category categoryJava = new Category();
		categoryJava.setId(1L);
		categoryJava.setName("Java");
		Category categoryC = new Category();
		categoryC.setId(2L);
		categoryC.setName("C#");
		Question q1 = new Question();
		q1.setId(1L);
		q1.setCategory(categoryJava);
		q1.setContent("Test content");
		Question q2 = new Question();
		q2.setId(2L);
		q2.setCategory(categoryJava);
		q2.setContent("Test content");
		Question q3 = new Question();
		q3.setId(3L);
		q3.setCategory(categoryC);
		q3.setContent("Test content");
		Question q4 = new Question();
		q4.setId(4L);
		q4.setCategory(categoryC);
		q4.setContent("Test content");
		Question q5 = new Question();
		q5.setId(5L);
		q5.setContent("Test content");
		
		service.save(q1);
		service.save(q2);
		service.save(q3);
		service.save(q4);
		service.save(q5);
	}

	@Test
	public void testFindByCategoryId() {
		List<Question> questions = service.findByCategoryId(1L);
		
		Assert.assertNotNull(questions);
		Assert.assertTrue(questions.size() == 2);

		questions = service.findByCategoryId(2L);

		Assert.assertNotNull(questions);
		Assert.assertTrue(questions.size() == 2);
		
		questions = service.findByCategoryId(3L);

		Assert.assertNotNull(questions);
		Assert.assertTrue(questions.size() == 0);
	}

}
