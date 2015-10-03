package net.sp0gg.fridgezone.data;

import net.sp0gg.fridgezone.config.TestDataConfig;
import net.sp0gg.fridgezone.data.dao.ItemDao;
import net.sp0gg.fridgezone.data.repository.ItemRepository;
import net.sp0gg.fridgezone.domain.Item;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

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
    public void shouldRemoveTagsWhenPassingEmptyTagList(){
        Item testItem = itemRepo.findOne(2L);
        testItem.setTags(new ArrayList<>());
        itemDao.update(testItem);
        Item updatedItem = itemRepo.findOne(2L);

        assertNotNull(updatedItem.getTags());
        assertEquals(0, updatedItem.getTags().size());
        assertTrue(updatedItem.getTags().isEmpty());
    }
}
