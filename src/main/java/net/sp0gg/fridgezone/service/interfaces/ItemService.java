package net.sp0gg.fridgezone.service.interfaces;

import net.sp0gg.fridgezone.domain.Item;

import java.util.Collection;

/**
 * Created by sp0gg on 10/27/15.
 */
public interface ItemService {
    Item updateItem(Item item);
    Item addItem(Item item);
    Collection<Item> fetchAll();
}
