package com.levi9.code9.model;

import javax.validation.constraints.Pattern;

/**
 * @author s.racicberic
 *
 */
public class Category extends AbstractBaseEntity {
	
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -5853533806683734518L;
	
	/**
	 * Category name
	 */
	@Pattern(regexp = "^(?=\\s*\\S).*$")
	private String name;
	
	/**
	 * @return name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

}
