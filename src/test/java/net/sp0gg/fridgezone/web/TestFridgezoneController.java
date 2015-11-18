package net.sp0gg.fridgezone.web;

import net.sp0gg.fridgezone.config.TestDataConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@ContextConfiguration(classes = {TestDataConfig.class})
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("local")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class TestFridgezoneController {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Test
    public void rootPathShouldRedirectToInventoryPage() throws Exception {
        log.info("Running rootShouldRedirectToInventoryPage");
        FridgezoneController fridgezoneController = new FridgezoneController();
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(fridgezoneController).build();
            mockMvc.perform(get("/")).andExpect(view().name("redirect:/inventory"));
    }



}
