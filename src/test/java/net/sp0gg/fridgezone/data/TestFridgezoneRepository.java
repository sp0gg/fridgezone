package net.sp0gg.fridgezone.data;

import net.sp0gg.fridgezone.config.TestDataConfig;
import net.sp0gg.fridgezone.data.repository.ItemRepository;
import net.sp0gg.fridgezone.data.repository.TagRepository;
import net.sp0gg.fridgezone.domain.Item;
import net.sp0gg.fridgezone.domain.Tag;
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

import static org.junit.Assert.*;

@ContextConfiguration(classes=TestDataConfig.class)
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("local")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class TestFridgezoneRepository {

    Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
    ItemRepository itemRepo;

    @Autowired
    TagRepository tagRepo;

	@Test
	public void shouldFindAllItems() {
		log.info("Running shouldFindAllItems");
		List<Item> items = itemRepo.findAll();
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
        List<Tag> tags = new ArrayList<>();
        Tag tag = new Tag();
        tag.setName("favorite");
        tag.setItem(newItem);
        tags.add(tag);
        newItem.setTags(tags);
		Item returned = itemRepo.save(newItem);
		assertEquals("Grapes", returned.getName());
        assertEquals("Out", returned.getStockLevel());
        assertEquals(1, returned.getOptimalQuantity());
        assertEquals("favorite", returned.getTags().get(0).getName());
        log.info(returned.getTags().get(0).getItem().getId().toString());
    }

    @Test
    public void shouldUpdateItem(){
        log.info("Running shouldUpdateItem");

        Item cheese = new Item();
        cheese.setId(3L);
        cheese.setStockLevel("Stocked");
        cheese.setName("Cheese");
        cheese.setOptimalQuantity(2);

        Item eggs = new Item();
        eggs.setStockLevel("Surplus");
        eggs.setId(5L);
        eggs.setName("Eggs");
        eggs.setOptimalQuantity(2);

        List<Tag> tags = new ArrayList<>();
        Tag tag = new Tag();
        tag.setName("favorite");
        tag.setItem(eggs);
        tags.add(tag);
        eggs.setTags(tags);

        List<Item> items = new ArrayList<>();
        items.add(cheese);
        items.add(eggs);
        itemRepo.save(items);
        tagRepo.save(eggs.getTags());

        Item cheeseRetrieved = itemRepo.findOne(3l);
        Item eggsRetrieved = itemRepo.findOne(5l);
        assertEquals("Stocked", cheeseRetrieved.getStockLevel());
        assertEquals(2, cheeseRetrieved.getOptimalQuantity());
        assertEquals("Surplus", eggsRetrieved.getStockLevel());
        assertEquals(2, eggsRetrieved.getOptimalQuantity());
        assertEquals("favorite", eggsRetrieved.getTags().get(0).getName());
    }

    @Test
    public void learningToRemoveExistingTags(){
        Item testItem = itemRepo.findOne(2L);
        tagRepo.delete(testItem.getTags());
        testItem.setTags(new ArrayList<>());
        itemRepo.save(testItem);
        Item updatedItem = itemRepo.findOne(2L);

        assertNotNull(updatedItem.getTags());
        assertEquals(0, updatedItem.getTags().size());
        assertTrue(updatedItem.getTags().isEmpty());
    }
}
