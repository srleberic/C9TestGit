/**
 * 
 */
package com.levi9.code9.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.Pattern;

/**
 * @author s.racicberic
 *
 */
@Embeddable
public class Answer implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4601905999366595902L;
	
	@Column(nullable = false, length = 255)
	@Pattern(regexp = "^(?=\\s*\\S).*$")
	private String content;
	
	@Column(nullable = false)
	private boolean correct;

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
	 * @return the correct
	 */
	public boolean isCorrect() {
		return correct;
	}

	/**
	 * @param correct the correct to set
	 */
	public void setCorrect(boolean correct) {
		this.correct = correct;
	}
	

}
