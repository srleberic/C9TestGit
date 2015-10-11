/**
 * 
 */
package com.levi9.code9.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.levi9.code9.service.QuestionService;

/**
 * @author Srle
 *
 */
@Controller
@RequestMapping("/questions")
public class QuestionController {
	
	@Autowired
	private QuestionService questionService;

}
