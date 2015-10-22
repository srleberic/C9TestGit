package com.levi9.code9.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;

/**
 * @author Srle
 *
 */
@Entity
@Table(name = "test")
public class Test extends AbstractBaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -707161561380217235L;
	
	@Column(nullable = false, length = 255)
	@Pattern(regexp = "^(?=\\s*\\S).*$")
	private String name;
	
	@Column(name = "create_date", nullable = false, length = 255)
	private Date createDate;
	
	@Column(name = "created_by", nullable = false, length = 255)
	@Pattern(regexp = "^(?=\\s*\\S).*$")
	private String createdBy;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "test_questions")
	private Set<Question> questions;
	
	public Test() {
		questions = new HashSet<>();
		createDate = new Date();
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the createDate
	 */
	public Date getCreateDate() {
		return createDate;
	}

	/**
	 * @param createDate the createDate to set
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	/**
	 * @return the createdBy
	 */
	public String getCreatedBy() {
		return createdBy;
	}

	/**
	 * @param createdBy the createdBy to set
	 */
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 * @return the questions
	 */
	public Set<Question> getQuestions() {
		return questions;
	}

	/**
	 * @param questions the questions to set
	 */
	public void setQuestions(Set<Question> questions) {
		this.questions = questions;
	}
	
	public void addQuestion(Question question) {
		this.questions.add(question);
	}
	
	public void removeQuestion(Question question) {
		this.questions.remove(question);
	}
	
}
