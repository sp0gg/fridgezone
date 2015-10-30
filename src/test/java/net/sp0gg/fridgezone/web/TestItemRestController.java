package net.sp0gg.fridgezone.web;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.sp0gg.fridgezone.config.TestDataConfig;
import net.sp0gg.fridgezone.data.dao.ItemDao;
import net.sp0gg.fridgezone.data.rest.ItemRestController;
import net.sp0gg.fridgezone.domain.Item;
import net.sp0gg.fridgezone.domain.Tag;
import net.sp0gg.fridgezone.service.ItemService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by sp0gg on 9/19/15.
 */
@ContextConfiguration(classes = {TestDataConfig.class})
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("local")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class TestItemRestController {

    Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    ItemDao itemDao;

    @Autowired
    ItemService itemService;

    MockMvc mockMvc;
    ItemRestController itemController;

    String baseUrl = "/api/items";

    @Before
    public void setUp(){
        itemController = new ItemRestController(itemDao, itemService);
        mockMvc = MockMvcBuilders.standaloneSetup(itemController).build();
    }

    @Test
    public void shouldReturnItemList() throws Exception {
        log.info("Running shouldReturnItemList");
        String controllerResponse = mockMvc.perform(get(baseUrl)).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
        log.info("shouldReturnItemList returned: " + controllerResponse);
    }

    @Test
    public void shouldAddItem() throws Exception{
        log.info("Running shouldAddItem");
        Item item = generateDummyItem();
        List<Tag> tags = generateDummyTags();
        item.setTags(tags);

        String jsonString = objectToJson(item);
        log.info("testing with url: " + baseUrl);

        String controllerResponse = mockMvc.perform(post(baseUrl).content(jsonString).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
        log.info("shouldAddItem returned: " + controllerResponse);
    }

    @Test
    public void shouldUpdateItem() throws Exception {
        log.info("Running shouldUpdateItem");

        Item item = generateDummyItem();
        item.setId(3L);
        item.setOptimalQuantity(2);
        item.setStockLevel("Out");

        List<Tag> tags = generateDummyTags();
        Tag tag2 = new Tag();
        tag2.setName("testTag2");
        tags.add(tag2);
        item.setTags(tags);

        String jsonString = objectToJson(item);
        String updateUrl = baseUrl + "/" + item.getId();
        log.info("testing with url: " + updateUrl);

        String controllerResponse = mockMvc.perform(put(updateUrl).content(jsonString).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
        log.info("shouldUpdateItem returned: " + controllerResponse);
    }

    private Item generateDummyItem(){
        Item item = new Item();
        item.setName("TestItem");
        item.setOptimalQuantity(11);
        item.setStockLevel("Stocked");
        return item;
    }

    private List<Tag> generateDummyTags() {
        List<Tag> tags = new ArrayList<>();
        Tag tag = new Tag();
        tag.setName("favorite");
        tag.setId(null);
        tags.add(tag);
        return tags;
    }

    private String objectToJson(Item item) {
        Gson gson = new Gson();
        String itemJson = gson.toJson(item);
        JsonParser jsonParser = new JsonParser();
        JsonObject json = (JsonObject)jsonParser.parse(itemJson);
        String jsonString = json.toString();
        log.info("here is our json: " + jsonString);
        return jsonString;
    }
}