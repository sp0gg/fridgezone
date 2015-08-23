package net.sp0gg.fridgezone.data;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import net.sp0gg.fridgezone.config.TestDataConfig;
import net.sp0gg.fridgezone.data.repository.ItemRepository;
import net.sp0gg.fridgezone.domain.Item;

@ContextConfiguration(classes=TestDataConfig.class)
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("local")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class TestFridgezoneRepository {
	
	@Autowired
    ItemRepository repo;

	@Test
	public void shouldFindAllItems() {
		System.out.println("Running shouldFindAllItems");
		List<Item> items = repo.findAll();
		System.out.println("Items found:");
		for (Item item : items) {
			System.out.println(item.toString());
		}
		assertEquals("Pop", items.get(0).getName());
        assertEquals(new BigDecimal("10.00"), items.get(0).getQuantity());
		assertEquals(5, items.size());
	}

	@Test
	public void shouldAddItem(){
		System.out.println("Running shouldAddItem");
		Item newItem = new Item();
		newItem.setName("Grapes");
        newItem.setQuantity(BigDecimal.valueOf(2.5));
		repo.save(newItem);
        Item i2 = repo.findOne((long) 6);
		assertEquals("Grapes", i2.getName());
        assertEquals(new BigDecimal("2.50"), i2.getQuantity());
	}

    @Test
    public void shouldUpdateQuantities(){
        System.out.println("Running shouldUpdateQuantities");
        Item cheese = new Item();
        Item eggs = new Item();
        cheese.setId(3);
        cheese.setQuantity(BigDecimal.valueOf(50));
        eggs.setQuantity(BigDecimal.valueOf(75));
        eggs.setId(5);
        List<Item> items = new ArrayList<>();
        items.add(cheese);
        items.add(eggs);
        repo.save(items);

        assertEquals(new BigDecimal("50.00"), repo.findOne(Long.valueOf(3)).getQuantity());
        assertEquals(new BigDecimal("75.00"), repo.findOne(Long.valueOf(5)).getQuantity());
    }

//    @Test
//    public void shouldUpdateItem(){
//        System.out.println("Running shouldUpdateItem");
//
//
//        assertEquals(updatedItem, repo.findOne(Long.valueOf(1)));
//    }
}
