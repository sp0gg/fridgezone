package net.sp0gg.fridgezone.data;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import net.sp0gg.fridgezone.config.TestDataConfig;
import net.sp0gg.fridgezone.data.repository.FridgezoneRepository;
import net.sp0gg.fridgezone.domain.Item;

@ContextConfiguration(classes=TestDataConfig.class)
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("dev")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class TestFridgezoneRepository {
	
	@Autowired FridgezoneRepository repo;

	@Test
	public void shouldFindAllItems() {
		System.out.println("Running shouldFindAllItems");
		List<Item> items = repo.findAll();
		System.out.println("Items found:");
		for (Item item : items) {
			System.out.println(item.toString());
		}
		assertEquals("Pop", items.get(0).getName());
		assertEquals(5, items.size());
	}

	@Test
	public void shouldAddItem(){
		System.out.println("Running shouldAddItem");
		Item newItem = new Item();
		newItem.setName("Grapes");
		repo.save(newItem);
        Item i2 = repo.findOne((long) 6);
		assertEquals("Grapes", i2.getName());
	}
}
