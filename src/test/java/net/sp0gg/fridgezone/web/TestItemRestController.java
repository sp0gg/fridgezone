package net.sp0gg.fridgezone.web;

import net.sp0gg.fridgezone.config.TestDataConfig;
import net.sp0gg.fridgezone.data.repository.ItemRepository;
import net.sp0gg.fridgezone.data.rest.ItemRestController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

/**
 * Created by sp0gg on 9/19/15.
 */
@ContextConfiguration(classes = {TestDataConfig.class})
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("local")
public class TestItemRestController {

    Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    ItemRepository repo;

    @Test
    public void shouldReturnItemList() throws Exception {
        log.info("Running shouldReturnItemList");
        ItemRestController itemController = new ItemRestController(repo);
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(itemController).build();
        String controllerResponse = mockMvc.perform(get("/api/items")).andReturn().getResponse().getContentAsString();
        log.info("shouldReturnItemList returned: " + controllerResponse);
    }
}
