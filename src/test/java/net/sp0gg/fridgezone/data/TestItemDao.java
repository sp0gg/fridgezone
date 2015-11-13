package net.sp0gg.fridgezone.data;

import net.sp0gg.fridgezone.config.TestDataConfig;
import net.sp0gg.fridgezone.data.dao.interfaces.ItemDao;
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
    public void shouldFindAllItemsByUsername(){
        List<Item> itemsReturned = itemDao.findAllByUsername("testUser1");
        assertEquals(7, itemsReturned.size());
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
    public void shouldRemoveExistingTagsNotPassedIn() throws Exception{
        Item testItem = itemRepo.findOne(4L);
        List<Tag> tags = new ArrayList<>();
        Tag tag = new Tag();
        tag.setName("favorite");
        tags.add(tag);
        testItem.setTags(tags);
        itemDao.update(testItem);
        Item updatedItem = itemRepo.findOne(4L);

        assertNotNull(updatedItem.getTags());
        assertEquals(1, updatedItem.getTags().size());
        assertEquals("favorite", updatedItem.getTags().get(0).getName());
    }

    @Test
    public void shouldAddItemWithTags(){
        Item item = new Item();
        item.setName("New Item");
        item.setStockLevel("Stocked");
        item.setUsername("testUser1");
        List<Tag> tags = new ArrayList<>();
        Tag tag = new Tag();
        tag.setName("Test tag");
        tags.add(tag);
        item.setTags(tags);
        itemDao.add(item);

        Item addedItem = itemRepo.findOne(9L);
        assertNotNull(addedItem);
        assertNotNull(addedItem.getTags());
        assertEquals(1, addedItem.getTags().size());
        assertEquals(item.getTags().get(0).getName(), addedItem.getTags().get(0).getName());
    }
}
