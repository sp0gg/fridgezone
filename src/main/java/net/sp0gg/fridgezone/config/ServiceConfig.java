package net.sp0gg.fridgezone.config;

import net.sp0gg.fridgezone.service.interfaces.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Created by sp0gg on 10/29/15.
 */
@Configuration
@ComponentScan(basePackages = "net.sp0gg.fridgezone.service")
public class ServiceConfig {
    @Autowired
    private ItemService itemService;
}
