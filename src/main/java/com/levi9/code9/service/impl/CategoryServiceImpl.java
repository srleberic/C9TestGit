package com.levi9.code9.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.levi9.code9.model.Category;
import com.levi9.code9.repository.CategoryRepository;
import com.levi9.code9.service.CategoryService;

/**
 * @author s.racicberic
 *
 */
@Service
public class CategoryServiceImpl implements CategoryService {
	
	@Autowired
	private CategoryRepository categoryRepository;  

	@Override
	public Category findById(Long id) {
		return categoryRepository.findById(id);
	}

	@Override
	public List<Category> findAll() {
		return categoryRepository.findAll();
	}

	@Override
	public Category save(Category category) {
		return categoryRepository.save(category);
	}

	@Override
	public void remove(Long id) throws IllegalArgumentException {
		Category category = categoryRepository.findById(id);
		if (category == null) {
			throw new IllegalArgumentException(String.format(
					"Category with id=%d does not exist.", id));
		}
		categoryRepository.remove(id);
	}

}
