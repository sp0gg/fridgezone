package net.sp0gg.fridgezone.config;

import net.sp0gg.fridgezone.data.dao.ItemDao;
import net.sp0gg.fridgezone.data.dao.TagDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Created by sp0gg on 10/3/15.
 */
@Configuration
@ComponentScan(basePackages = "net.sp0gg.fridgezone.data.dao")
public class DaoConfig {
    @Autowired
    private ItemDao itemDao;
    @Autowired
    private TagDao tagDao;
}
