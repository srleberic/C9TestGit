package com.levi9.code9.service.memory;

import org.springframework.stereotype.Service;

import com.levi9.code9.model.Test;
import com.levi9.code9.service.TestService;

@Service
public class InMemoryTestService extends AbstractInMemoryService<Test> 
	implements TestService {

}
