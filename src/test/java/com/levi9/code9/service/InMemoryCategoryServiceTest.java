/**
 * 
 */
package com.levi9.code9.service;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.levi9.code9.model.Category;
import com.levi9.code9.service.memory.InMemoryCategoryService;

/**
 * @author s.racicberic
 *
 */
public class InMemoryCategoryServiceTest {
	
	private CategoryService categoryService;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		categoryService = new InMemoryCategoryService();
		
		Category categoryJava = new Category();
		categoryJava.setId(1L);
		categoryJava.setName("Java");
		Category categoryC = new Category();
		categoryC.setId(2L);
		categoryC.setName("C#");
		
		categoryService.save(categoryJava);
		categoryService.save(categoryC);
	}

	/**
	 * Test method for {@link com.levi9.code9.service.memory.InMemoryCategoryService#findById(java.lang.Long)}.
	 */
	@Test
	public void testFindById() {
		Category cat = categoryService.findById(1L);
		Assert.assertNotNull(cat);
		Assert.assertEquals("Java", cat.getName());
	}

	/**
	 * Test method for {@link com.levi9.code9.service.memory.InMemoryCategoryService#findAll()}.
	 */
	@Test
	public void testFindAll() {
		List<Category> cats = categoryService.findAll();
		Assert.assertEquals(2, cats.size());
		Category cat1 = cats.get(0);
		Category cat2 = cats.get(1);
		if (cat1.getId().equals(1L)) {
			Assert.assertEquals("Java", cat1.getName());
			Assert.assertTrue(cat2.getId().equals(2L) && cat2.getName().equals("C#"));
		} else {
			Assert.assertTrue(cat1.getId().equals(2L) && cat1.getName().equals("C#"));
			Assert.assertTrue(cat2.getId().equals(1L) && cat2.getName().equals("Java"));
		}
	}

	/**
	 * Test method for {@link com.levi9.code9.service.memory.InMemoryCategoryService#save(com.levi9.code9.model.Category)}.
	 */
	@Test
	public void testSave() {
		Category cat = new Category();
		cat.setName("New Category");
		Category saved = categoryService.save(cat);
		Assert.assertNotNull(saved.getId());
		Assert.assertEquals("New Category", categoryService.findById(saved.getId()).getName());
	}

	/**
	 * Test method for {@link com.levi9.code9.service.memory.InMemoryCategoryService#remove(java.lang.Long)}.
	 */
	@Test
	public void testRemove() {
		Assert.assertNotNull(categoryService.findById(1L));
		Assert.assertNotNull(categoryService.findById(2L));
		
		categoryService.remove(1L);
		
		Assert.assertNull(categoryService.findById(1L));
		Assert.assertNotNull(categoryService.findById(2L));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testRemoveIllegalArgument() {
		Assert.assertNull(categoryService.findById(3L));		
		categoryService.remove(3L);
	}

}
