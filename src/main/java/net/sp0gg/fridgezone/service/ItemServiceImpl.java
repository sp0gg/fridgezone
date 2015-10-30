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
        if(needsToBeAddedToShoppingList(item)){
            log.debug("Item " + item.getName() + " needs to be on shopping list - adding shopping tag");
            Tag shoppingTag = new Tag();
            shoppingTag.setName(Constants.SHOPPING_TAG_NAME);
            item.getTags().add(shoppingTag);
        }

        Item returnedItem = dao.update(item);
        return returnedItem;
    }

    private boolean needsToBeAddedToShoppingList(Item item) {
        boolean isFavoriteItemWithoutShoppingTag = item.containsTagName(Constants.FAVORITE_TAG_NAME) && !item.containsTagName(Constants.SHOPPING_TAG_NAME);
        boolean isStockLevelLowOrOut = item.getStockLevel().equals(Constants.STOCK_LEVEL_OUT) || item.getStockLevel().equals(Constants.STOCK_LEVEL_LOW);
        return isFavoriteItemWithoutShoppingTag && isStockLevelLowOrOut;
    }

    @Autowired
    public ItemServiceImpl(ItemDao dao){
        this.dao = dao;
    }
}