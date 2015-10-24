package net.sp0gg.fridgezone.data;

import net.sp0gg.fridgezone.config.TestDataConfig;
import net.sp0gg.fridgezone.data.repository.ItemRepository;
import net.sp0gg.fridgezone.data.repository.TagRepository;
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

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by sp0gg on 10/23/15.
 */
@ContextConfiguration(classes=TestDataConfig.class)
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("local")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class TestTagRepository {

    Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    ItemRepository itemRepo;

    @Autowired
    TagRepository tagRepo;

    @Test
    public void shouldFindDistinctTags() {
        log.info("Running shouldFindDistinctTags");
        List<String> tags = tagRepo.findDistinctTagNames();
        log.info("Tags found:");
        for (String tag : tags) {
            log.info(tag);
        }
        assertEquals(5, tags.size());
    }
}