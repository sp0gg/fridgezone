package net.sp0gg.fridgezone.data.dao.interfaces;

import net.sp0gg.fridgezone.domain.Item;

import java.util.List;

/**
 * Created by sp0gg on 10/3/15.
 */
public interface ItemDao {
    List<Item> findAllByUsername(String username);
    Item findItemById(Long id);
    Item add(Item item);
    Item update(Item item);
}