/**
 * 
 */
package com.levi9.code9.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.levi9.code9.model.Category;
import com.levi9.code9.model.Question;
import com.levi9.code9.service.CategoryService;
import com.levi9.code9.service.QuestionService;

/**
 * @author Srle
 *
 */
@Controller
@RequestMapping("/questions")
public class QuestionController {
	
	/**
	 * Question service
	 */
	@Autowired
	private QuestionService questionService;
	
	@Autowired
	private CategoryService categoryService;
	
	/**
	 * Retrieves all questions per category and returns them as model attribute
	 * <code>questions</code>.
	 * 
	 * @return list of questions per category
	 */
	@RequestMapping(method = RequestMethod.GET)
	@ModelAttribute("questions")
	public Map<String, List<Question>> get() {
		Map<String, List<Question>> retVal = new HashMap<>();
		List<Category> categories = categoryService.findAll();
		List<Question> uncategorizedQuestions = questionService.findAll();
		
		for (Category c : categories) {
			List<Question> questions = questionService.findByCategoryId(c.getId());
			if (!questions.isEmpty()) {
				uncategorizedQuestions.removeAll(questions);
				retVal.put(c.getName(), questions);
			}
		}
		if (!uncategorizedQuestions.isEmpty()) {
			retVal.put("Uncategorized", uncategorizedQuestions);
		}
		return retVal;
	}

	/**
	 * Returns the view for adding new question. Adds empty question as model
	 * attribute <code>question</code> and list of all categories as model
	 * attribute <code>categories</code>.
	 * 
	 * @param model
	 * @return the name of the view for adding/editing a question
	 */
	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public String getNew(Model model) {
		model.addAttribute("question", new Question());
		model.addAttribute("categories", categoryService.findAll());
		return "addEditQuestion";
	}
	
	/**
	 * Returns the view for editing a question. Adds question, found by passed
	 * id, as model attribute <code>question</code> and list of all categories
	 * as model attribute <code>categories</code>.
	 * 
	 * @param id
	 * @param model  the model object map
	 * @return
	 */
	@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
	public String getEdit(@PathVariable Long id, Model model) {
		model.addAttribute("question", questionService.findById(id));
		model.addAttribute("categories", categoryService.findAll());
		return "addEditQuestion";
	}
	
	/**
	 * Removes the question object with the specified id.
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/remove/{id}", method = RequestMethod.GET)
	public String remove(@PathVariable Long id) {
		questionService.remove(id);
		return "redirect:..";
	}
	
	@RequestMapping(params = "save", method = RequestMethod.POST)
	public String save(Question question) {
		questionService.save(question);
		return "redirect:questions";
	}
	
	/**
	 * @return
	 */
	@RequestMapping(params = "cancel", method = RequestMethod.POST)
	public String cancel() {
		return "redirect:questions";
	}
}
