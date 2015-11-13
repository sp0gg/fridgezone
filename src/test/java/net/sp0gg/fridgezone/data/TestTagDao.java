package net.sp0gg.fridgezone.data;

import net.sp0gg.fridgezone.config.TestDataConfig;
import net.sp0gg.fridgezone.data.dao.interfaces.TagDao;
import net.sp0gg.fridgezone.data.repository.TagRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;

/**
 * Created by sp0gg on 10/24/15.
 */
@ContextConfiguration(classes=TestDataConfig.class)
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("local")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class TestTagDao {

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private TagDao tagDao;

    @Test
    public void shouldFindDistinctTags(){
        assertEquals(5, tagDao.findAllNames("testUser1").size());
    }
}
