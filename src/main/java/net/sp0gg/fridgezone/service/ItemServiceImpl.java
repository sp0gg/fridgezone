package net.sp0gg.fridgezone.service;

import net.sp0gg.fridgezone.data.dao.ItemDao;
import net.sp0gg.fridgezone.domain.Item;
import net.sp0gg.fridgezone.domain.Tag;
import net.sp0gg.fridgezone.util.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by sp0gg on 10/27/15.
 */
@Service
public class ItemServiceImpl implements ItemService {

    Logger log = LoggerFactory.getLogger(this.getClass());

    private ItemDao dao;

    @Override
    public Item updateItem(Item item) {
        if(item.containsTagName(Constants.FAVORITE_TAG_NAME) && !item.containsTagName(Constants.SHOPPING_TAG_NAME) && (item.getStockLevel() == Constants.STOCK_LEVEL_OUT || item.getStockLevel() == Constants.STOCK_LEVEL_LOW)){
            log.debug("Item " + item.getName() + " is a favorite item and is low or out - adding shopping tag");
            Tag shoppingTag = new Tag();
            shoppingTag.setName(Constants.SHOPPING_TAG_NAME);
            item.getTags().add(shoppingTag);
        }
        Item returnedItem = dao.update(item);
        return returnedItem;
    }

    @Autowired
    public ItemServiceImpl(ItemDao dao){
        this.dao = dao;
    }
}
