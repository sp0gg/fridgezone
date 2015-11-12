package net.sp0gg.fridgezone.web;

import net.sp0gg.fridgezone.config.TestDataConfig;
import net.sp0gg.fridgezone.config.TestWebConfig;
import net.sp0gg.fridgezone.data.dao.interfaces.TagDao;
import net.sp0gg.fridgezone.data.rest.TagRestController;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by sp0gg on 10/23/15.
 */
@ContextConfiguration(classes = {TestWebConfig.class})
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("local")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class TestTagRestController {
    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private TagDao tagDao;

    @Autowired
    WebApplicationContext context;

    MockMvc mockMvc;
    TagRestController tagController;

    String baseUrl = "/api/tags";

    @Before
    public void setUp(){
        tagController = new TagRestController(tagDao);
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity()).build();
    }

    @Test
    @WithMockUser
    public void shouldReturnDistinctTagList() throws Exception {
        log.info("Running shouldReturnDistinctTagList");
        String controllerResponse = mockMvc.perform(get(baseUrl)).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
        log.info("shouldReturnDistinctTagList returned: " + controllerResponse);
    }
}
