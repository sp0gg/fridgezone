package net.sp0gg.fridgezone.data;

import net.sp0gg.fridgezone.config.TestDataConfig;
import net.sp0gg.fridgezone.data.repository.ItemRepository;
import net.sp0gg.fridgezone.domain.Item;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

@ContextConfiguration(classes=TestDataConfig.class)
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("local")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class TestFridgezoneRepository {

    Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
    ItemRepository repo;

	@Test
	public void shouldFindAllItems() {
		log.info("Running shouldFindAllItems");
		List<Item> items = repo.findAll();
		System.out.println("Items found:");
		for (Item item : items) {
			System.out.println(item.toString());
		}
        Item item = items.get(0);
		assertEquals("Pop", item.getName());
        assertEquals("Out", item.getStockLevel());
        assertEquals(2, item.getOptimalQuantity());
		assertEquals(5, items.size());

        assertEquals("favorite", items.get(1).getTags().get(0).getName());
        assertEquals("favorite", items.get(3).getTags().get(0).getName());

    }

	@Test
	public void shouldAddItem(){
		log.info("Running shouldAddItem");
		Item newItem = new Item();
		newItem.setName("Grapes");
        newItem.setStockLevel("Out");
        newItem.setOptimalQuantity(1);
		repo.save(newItem);
        Item i2 = repo.findOne(6l);
		assertEquals("Grapes", i2.getName());
        assertEquals("Out", i2.getStockLevel());
        assertEquals(1, i2.getOptimalQuantity());
	}

    @Test
    public void shouldUpdate(){
        log.info("Running shouldUpdate");
        Item cheese = new Item();
        Item eggs = new Item();
        cheese.setId(3);
        cheese.setStockLevel("Stocked");
        cheese.setName("Cheese");
        cheese.setOptimalQuantity(2);
        eggs.setStockLevel("Surplus");
        eggs.setId(5);
        eggs.setName("Eggs");
        eggs.setOptimalQuantity(2);
        List<Item> items = new ArrayList<>();
        items.add(cheese);
        items.add(eggs);
        repo.save(items);

        Item cheeseRetrieved = repo.findOne(3l);
        Item eggsRetrieved = repo.findOne(5l);
        assertEquals("Stocked", cheeseRetrieved.getStockLevel());
        assertEquals(2, cheeseRetrieved.getOptimalQuantity());
        assertEquals("Surplus", eggsRetrieved.getStockLevel());
        assertEquals(2, eggsRetrieved.getOptimalQuantity());
    }
}
