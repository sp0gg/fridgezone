package net.sp0gg.fridgezone.web;

import static org.hamcrest.Matchers.hasItems;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

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
import net.sp0gg.fridgezone.data.repository.ItemRepository;
import net.sp0gg.fridgezone.domain.Item;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@ContextConfiguration(classes = {TestDataConfig.class})
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("local")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class TestFridgezoneController {

//    @Autowired
//    public ViewResolver viewRes;

    public ViewResolver viewRes(){
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix("/WEB-INF/views/");
        resolver.setSuffix(".jsp");
        resolver.setExposeContextBeansAsAttributes(true);
        return resolver;
    }

    @Test
    public void rootPathShouldRedirectToInventoryPage() throws Exception {
        System.out.println("Running rootShouldRedirectToInventoryPage");
        ItemRepository mockRepo = mock(ItemRepository.class);
        FridgezoneController fridgezoneController = new FridgezoneController(mockRepo);
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(fridgezoneController).build();
            mockMvc.perform(get("/")).andExpect(view().name("redirect:/inventory"));
    }

	@Test
	public void inventoryPathShouldReturnItemListInModel() throws Exception {
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

		ItemRepository mockRepo = mock(ItemRepository.class);
		when(mockRepo.findAll()).thenReturn(expectedItems);

		FridgezoneController fridgezoneController = new FridgezoneController(mockRepo);

		MockMvc mockMvc = MockMvcBuilders.standaloneSetup(fridgezoneController).setViewResolvers(viewRes()).build();
		mockMvc.perform(get("/inventory"))
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

        ItemRepository mockRepo = mock(ItemRepository.class);

        FridgezoneController fridgezoneController = new FridgezoneController(mockRepo);
        when(mockRepo.save(newItem)).thenReturn(newItem);
        when(mockRepo.findAll()).thenReturn(expectedItems);

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(fridgezoneController).build();
        mockMvc.perform(post("/addItem").param("name", "Grapes").param("quantity", "60"))
            .andExpect(view().name("redirect:/inventory"))
//            .andExpect(model().attributeExists("items"))
//            .andExpect(model().attribute("items", hasItems(expectedItems.toArray())))
            ;
//        verify(mockRepo, atLeastOnce()).save(newItem);
    }

    @Test
    public void updateItemShouldReturnToInventoryScreenWithNewItemQuantitiesDisplayed() throws Exception {
        System.out.println("Running updateItemShouldReturnToInventoryScreenWithNewItemQuantitiesDisplayed");

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

        ItemRepository mockRepo = mock(ItemRepository.class);

        FridgezoneController fridgezoneController = new FridgezoneController(mockRepo);
        when(mockRepo.save(newItem)).thenReturn(newItem);
        when(mockRepo.findAll()).thenReturn(expectedItems);

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(fridgezoneController).build();
        mockMvc.perform(post("/addItem").param("name", "Grapes").param("quantity", "60").param("id", "6"))
                .andExpect(view().name("redirect:/inventory"))
                ;

    }

}
