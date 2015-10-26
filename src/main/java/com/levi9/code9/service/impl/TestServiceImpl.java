/**
 * 
 */
package com.levi9.code9.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.levi9.code9.model.Test;
import com.levi9.code9.repository.TestRepository;
import com.levi9.code9.service.TestService;

/**
 * @author s.racicberic
 *
 */
@Service
public class TestServiceImpl implements TestService {
	
	@Autowired
	private TestRepository testRepository;

	/* (non-Javadoc)
	 * @see com.levi9.code9.service.CrudService#findById(java.lang.Long)
	 */
	@Override
	public Test findOne(Long id) {
		return testRepository.findOne(id);
	}

	/* (non-Javadoc)
	 * @see com.levi9.code9.service.CrudService#findAll()
	 */
	@Override
	public List<Test> findAll() {
		return testRepository.findAll();
	}

	/* (non-Javadoc)
	 * @see com.levi9.code9.service.CrudService#save(com.levi9.code9.model.AbstractBaseEntity)
	 */
	@Override
	@Transactional
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public Test save(Test test) {
		return testRepository.save(test);
	}

	/* (non-Javadoc)
	 * @see com.levi9.code9.service.CrudService#remove(java.lang.Long)
	 */
	@Override
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public void remove(Long id) throws IllegalArgumentException {
		Test test = testRepository.findOne(id);
		if (test == null) {
			throw new IllegalArgumentException(String.format(
							"Test with id=%d does not exist.",
							id));
		}
		testRepository.delete(id);
	}

}
