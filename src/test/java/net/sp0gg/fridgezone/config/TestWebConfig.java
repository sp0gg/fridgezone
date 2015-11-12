package net.sp0gg.fridgezone.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * Created by sp0gg on 11/11/15.
 */
@EnableWebMvc
@ComponentScan(basePackages = {"net.sp0gg.fridgezone.config", "net.sp0gg.fridgezone.data.rest"})
public class TestWebConfig {
}
