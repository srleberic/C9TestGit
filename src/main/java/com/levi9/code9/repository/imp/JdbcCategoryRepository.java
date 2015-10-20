/**
 * 
 */
package com.levi9.code9.repository.imp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.IncorrectResultSetColumnCountException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.levi9.code9.model.Category;
import com.levi9.code9.repository.CategoryRepository;

/**
 * @author s.racicberic
 *
 */
@Repository
public class JdbcCategoryRepository implements CategoryRepository {
	
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public void init(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	

	/* (non-Javadoc)
	 * @see com.levi9.code9.repository.BaseRepository#findAll()
	 */
	@Override
	public List<Category> findAll() {
		return jdbcTemplate.query("select * from category", 
				new CategoryRowMapper());
	}

	/* (non-Javadoc)
	 * @see com.levi9.code9.repository.BaseRepository#findById(java.lang.Long)
	 */
	@Override
	public Category findById(Long id) {
		List<Category> resultList = jdbcTemplate.query("select * from category where id = ?", new Object[] {id}, 
				new CategoryRowMapper());
		if (resultList.size() == 1) {
			return resultList.get(0);
		}
		if (resultList.size() > 1) {
			throw new IncorrectResultSetColumnCountException(1, 
					resultList.size());
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see com.levi9.code9.repository.BaseRepository#save(java.lang.Object)
	 */
	@Override
	public Category save(Category entity) {
		jdbcTemplate.update("replace into category (id, name) values (?, ?)", 
				entity.getId(), entity.getName());
		return entity;
	}

	/* (non-Javadoc)
	 * @see com.levi9.code9.repository.BaseRepository#remove(java.lang.Long)
	 */
	@Override
	public void remove(Long id) throws IllegalArgumentException {
		jdbcTemplate.update("delete from category where id = ?", id);
	}
	
	private static final class CategoryRowMapper implements RowMapper<Category> {
		
		private static final String COLUMN_ID = "id";
		private static final String COLUMN_NAME = "name";

		@Override
		public Category mapRow(ResultSet rs, int rowNum) throws SQLException {
			Category category = new Category();
			category.setId(rs.getLong(COLUMN_ID));
			category.setName(rs.getString(COLUMN_NAME));
			return category;
		}
	}

}
