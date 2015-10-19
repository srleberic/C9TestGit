package com.levi9.code9.repository;

import java.util.List;

/**
 * @author s.racicberic
 *
 * @param <E>
 */
public interface BaseRepository<E> {
	
	List<E> findAll();
	
	E findById(Long id);
	
	E save(E entity);
	
	void remove(Long id) throws IllegalArgumentException;
	
}
