package com.dmitrijs.wishlist;

import com.dmitrijs.wishlist.Exceptions.ResourceNotFoundException;
import com.dmitrijs.wishlist.Model.Wish;
import com.dmitrijs.wishlist.Service.WishService;
import org.springframework.boot.test.context.SpringBootTest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;
import javax.sql.DataSource;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WishServiceTest {

	@Autowired
	private DataSource dataSource;

	@Autowired
	private WishService _wishService;

	@Rule
	public ExpectedException exceptionRule = ExpectedException.none();

	@Test
	public void testSaveWish() throws Exception {
		Wish wish = new Wish();
		wish.setMatter("new bottle of wine");
		wish.setQuantity(5L);
		wish.setUserId(20000L);

		_wishService.save(wish);
		assertNotNull(wish.getId());

		Wish findWish = _wishService.findById(wish.getId());
		assertEquals("new bottle of wine", findWish.getMatter());
		assertEquals((Long)(5L), findWish.getQuantity());
	}

	@Test
	public void testUpdateWish() throws Exception{
		Wish wish = new Wish();
		wish.setMatter("new car");
		wish.setQuantity(1L);
		wish.setUserId(20002L);

		_wishService.save(wish);

		// update record
		wish.setQuantity(2L);
		_wishService.update(wish);

		// test after update
		Wish findWish = _wishService.findById(wish.getId());
		assertEquals((Long)2L, findWish.getQuantity());
	}

	@Test
	public void testDeleteWish() throws Exception {
		Wish wish = new Wish();
		wish.setMatter("new pencil");
		wish.setQuantity(40L);
		wish.setUserId(20003L);

		_wishService.save(wish);
		// test delete
		_wishService.deleteById(wish.getId());

		// query after delete
		exceptionRule.expect(ResourceNotFoundException.class);
		_wishService.findById(wish.getId());
	}
}
