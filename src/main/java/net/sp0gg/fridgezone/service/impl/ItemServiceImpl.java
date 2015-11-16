package net.sp0gg.fridgezone.service.impl;

import net.sp0gg.fridgezone.data.dao.interfaces.ItemDao;
import net.sp0gg.fridgezone.domain.Item;
import net.sp0gg.fridgezone.domain.Tag;
import net.sp0gg.fridgezone.service.exceptions.ItemNotFoundException;
import net.sp0gg.fridgezone.service.interfaces.ItemService;
import net.sp0gg.fridgezone.util.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.Collection;

/**
 * Created by sp0gg on 10/27/15.
 */
@Service
public class ItemServiceImpl implements ItemService {

    Logger log = LoggerFactory.getLogger(this.getClass());

    private ItemDao dao;

    @Override
    public Item updateItem(Item item){
        log.debug("updating item: " + item.toString());
        Item existingItem = dao.findItemById(item.getId());

        if(existingItem == null){
            log.info("Could not find item id " + item.getId());
            throw new ItemNotFoundException();
        }

        if(!existingItem.getUsername().equals(item.getUsername())){
            log.info("Unauthorized attempt to update item id " + existingItem.getId() + " by user " + item.getUsername());
            throw new AccessDeniedException("Item id " + existingItem.getId() + " is not owned by user " + item.getUsername());
        }

        if(needsToBeAddedToShoppingList(item)){
            log.info("Item " + item.getName() + " needs to be on shopping list - adding shopping tag");
            Tag shoppingTag = new Tag();
            shoppingTag.setName(Constants.SHOPPING_TAG_NAME);
            item.getTags().add(shoppingTag);
        }
        Item returnedItem = dao.update(item);
        return returnedItem;
    }

    @Override
    public Item addItem(Item item) {
        log.debug("adding item: " + item.toString());
        return dao.add(item);
    }

    @Override
    public Collection<Item> fetchAll(String username) {
        log.debug("fetching all for: " + username);
        return dao.findAllByUsername(username);
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
