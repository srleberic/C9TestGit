/**
 * 
 */
package com.levi9.code9.model;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;

/**
 * @author s.racicberic
 *
 */
public class Question extends AbstractBaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3407072150894856625L;
	
	/**
	 * Content of the question
	 */
	@Pattern(regexp = "^(?=\\s*\\S).*$")
	private String content;
	
	/**
	 * Category of the question
	 */
	private Category category;
	
	/**
	 * list of possible answers
	 */
	@Valid
	private List<Answer> answers;
	
	public Question() {
		super();
		answers = new ArrayList<>();
	}

	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @param content the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * @return the category
	 */
	public Category getCategory() {
		return category;
	}

	/**
	 * @param category the category to set
	 */
	public void setCategory(Category category) {
		this.category = category;
	}

	/**
	 * @return the answers
	 */
	public List<Answer> getAnswers() {
		return answers;
	}

	/**
	 * @param answers the answers to set
	 */
	public void setAnswers(List<Answer> answers) {
		this.answers = answers;
	}

}
