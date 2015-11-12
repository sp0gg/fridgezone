package net.sp0gg.fridgezone.service;

import net.sp0gg.fridgezone.data.dao.interfaces.ItemDao;
import net.sp0gg.fridgezone.domain.Item;
import net.sp0gg.fridgezone.domain.Tag;
import net.sp0gg.fridgezone.service.impl.ItemServiceImpl;
import net.sp0gg.fridgezone.service.interfaces.ItemService;
import net.sp0gg.fridgezone.util.Constants;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

/**
 * Created by sp0gg on 10/25/15.
 */
public class TestItemService {

    private ItemDao mockItemDao = mock(ItemDao.class);

    private ItemService itemService = new ItemServiceImpl(mockItemDao);


    @Test
    public void shouldAddShoppingTagToFavoriteItemsWhenLow(){
        Item favoriteOnlyItem = mockFavoriteOnlyItem();
        Item favoriteAndShoppingItem = mockFavoriteAndShoppingItem();

        when(mockItemDao.update(any(Item.class))).thenReturn(new Item());

        itemService.updateItem(favoriteOnlyItem);
        verify(mockItemDao).update(favoriteAndShoppingItem);
    }

    @Test
    public void shouldAddShoppingTagToFavoriteItemsWhenOut(){
        Item favoriteOnlyItem = mockFavoriteOnlyItem();
        Item favoriteAndShoppingItem = mockFavoriteAndShoppingItem();
        favoriteOnlyItem.setStockLevel(Constants.STOCK_LEVEL_OUT);
        favoriteAndShoppingItem.setStockLevel(Constants.STOCK_LEVEL_OUT);
        when(mockItemDao.update(any(Item.class))).thenReturn(new Item());

        itemService.updateItem(favoriteOnlyItem);
        verify(mockItemDao).update(favoriteAndShoppingItem);
    }

    @Test
    public void shouldNOTAddShoppingTagToFavoriteItemsWhenStocked(){
        Item favoriteOnlyItem = mockFavoriteOnlyItem();
        Item favoriteAndShoppingItem = mockFavoriteAndShoppingItem();
        favoriteOnlyItem.setStockLevel(Constants.STOCK_LEVEL_STOCKED);
        favoriteAndShoppingItem.setStockLevel(Constants.STOCK_LEVEL_STOCKED);
        when(mockItemDao.update(any(Item.class))).thenReturn(new Item());

        itemService.updateItem(favoriteOnlyItem);
        verify(mockItemDao).update(favoriteOnlyItem);
    }

    private Item mockFavoriteAndShoppingItem() {
        Tag favoriteTag = new Tag();
        favoriteTag.setName(Constants.FAVORITE_TAG_NAME);
        Tag shoppingTag = new Tag();
        shoppingTag.setName(Constants.SHOPPING_TAG_NAME);

        List<Tag> tags = new ArrayList<>();
        tags.add(favoriteTag);
        tags.add(shoppingTag);

        Item favoriteAndShoppingItem = new Item();
        favoriteAndShoppingItem.setName("Pizza");
        favoriteAndShoppingItem.setStockLevel("Low");
        favoriteAndShoppingItem.setTags(tags);
        return favoriteAndShoppingItem;
    }

    private Item mockFavoriteOnlyItem() {
        Tag favoriteTag = new Tag();
        favoriteTag.setName(Constants.FAVORITE_TAG_NAME);

        List<Tag> tags = new ArrayList<>();
        tags.add(favoriteTag);

        Item favoriteOnlyItem = new Item();
        favoriteOnlyItem.setName("Pizza");
        favoriteOnlyItem.setStockLevel("Low");
        favoriteOnlyItem.setTags(tags);
        return favoriteOnlyItem;
    }
}