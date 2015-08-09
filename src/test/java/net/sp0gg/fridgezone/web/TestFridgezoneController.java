package net.sp0gg.fridgezone.web;

import static org.hamcrest.Matchers.hasItems;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.time.Clock;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import net.sp0gg.fridgezone.config.TestDataConfig;
import net.sp0gg.fridgezone.config.WebConfig;
import net.sp0gg.fridgezone.data.repository.FridgezoneRepository;
import net.sp0gg.fridgezone.domain.Item;

@ContextConfiguration(classes = {TestDataConfig.class})
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("dev")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class TestFridgezoneController {

	@Test
	public void shouldReturnItemListInModel() throws Exception {
		System.out.println("Running shouldReturnItemListInModel");
		List<Item> expectedItems = new ArrayList<>();
		Item i1 = new Item();
		Item i2 = new Item();
		Item i3 = new Item();
		i1.setName("Pop");
		i2.setName("Cheese");
		i3.setName("Eggs");
		expectedItems.add(i1);
		expectedItems.add(i2);
		expectedItems.add(i3);

		FridgezoneRepository mockRepo = mock(FridgezoneRepository.class);
		when(mockRepo.findAll()).thenReturn(expectedItems);

		FridgezoneController fridgezoneController = new FridgezoneController(mockRepo);

		MockMvc mockMvc = MockMvcBuilders.standaloneSetup(fridgezoneController).build();
		mockMvc.perform(get("/"))
            .andExpect(view().name("inventory"))
            .andExpect(model().attributeExists("items"))
            .andExpect(model().attribute("items", hasItems(expectedItems.toArray())))
            ;
	}

	@Test
	public void addItemShouldReturnToInventoryScreenWithNewItemDisplayed() throws Exception {
		System.out.println("Running addItemShouldReturnToInventoryScreenWithNewItemDisplayed");

        List<Item> expectedItems = new ArrayList<>();
        Item i1 = new Item();
        Item i2 = new Item();
        Item i3 = new Item();
        Item newItem = new Item();
        i1.setName("Pop");
        i2.setName("Cheese");
        i3.setName("Eggs");
        newItem.setName("Grapes");
        expectedItems.add(i1);
        expectedItems.add(i2);
        expectedItems.add(i3);
        expectedItems.add(newItem);

        FridgezoneRepository mockRepo = mock(FridgezoneRepository.class);

        FridgezoneController fridgezoneController = new FridgezoneController(mockRepo);
        when(mockRepo.save(newItem)).thenReturn(newItem);
        when(mockRepo.findAll()).thenReturn(expectedItems);

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(fridgezoneController).build();
        mockMvc.perform(post("/addItem").param("name", "Grapes"))
            .andExpect(view().name("inventory"))
            .andExpect(model().attributeExists("items"))
            .andExpect(model().attribute("items", hasItems(expectedItems.toArray())))
            ;
//        verify(mockRepo, atLeastOnce()).save(newItem);
    }


}
