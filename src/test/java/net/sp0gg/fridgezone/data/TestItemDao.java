package net.sp0gg.fridgezone.data;

import net.sp0gg.fridgezone.config.TestDataConfig;
import net.sp0gg.fridgezone.data.dao.ItemDao;
import net.sp0gg.fridgezone.data.repository.ItemRepository;
import net.sp0gg.fridgezone.domain.Item;
import net.sp0gg.fridgezone.domain.Tag;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by sp0gg on 10/3/15.
 */
@ContextConfiguration(classes=TestDataConfig.class)
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("local")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class TestItemDao {

    @Autowired
    private ItemRepository itemRepo;

    @Autowired
    private ItemDao itemDao;

    @Test
    public void shouldFindAllItems(){
        List<Item> itemsReturned = itemDao.findAll();
        assertEquals(5, itemsReturned.size());
    }

    @Test
    public void shouldRemoveTagsWhenPassingEmptyTagList(){
        Item testItem = itemRepo.findOne(2L);
        testItem.setTags(new ArrayList<>());
        itemDao.update(testItem);
        Item updatedItem = itemRepo.findOne(2L);

        assertNotNull(updatedItem.getTags());
        assertEquals(0, updatedItem.getTags().size());
        assertTrue(updatedItem.getTags().isEmpty());
    }

    @Test
    public void shouldAddItemWithTags(){
        Item item = new Item();
        item.setName("New Item");
        item.setStockLevel("Stocked");
        List<Tag> tags = new ArrayList<>();
        Tag tag = new Tag();
        tag.setName("Test tag");
        tags.add(tag);
        item.setTags(tags);
        itemDao.add(item);

        Item addedItem = itemRepo.findOne(6L);
        assertNotNull(addedItem);
        assertNotNull(addedItem.getTags());
        assertEquals(1, addedItem.getTags().size());
        assertEquals(item.getTags().get(0).getName(), addedItem.getTags().get(0).getName());
    }
}
