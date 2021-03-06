package net.sp0gg.fridgezone.data.dao.impl;

import net.sp0gg.fridgezone.data.dao.interfaces.ItemDao;
import net.sp0gg.fridgezone.data.repository.ItemRepository;
import net.sp0gg.fridgezone.data.repository.TagRepository;
import net.sp0gg.fridgezone.domain.Item;
import net.sp0gg.fridgezone.domain.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sp0gg on 10/3/15.
 */
@Component
public class ItemDaoImpl implements ItemDao {

    private ItemRepository itemRepo;
    private TagRepository tagRepo;

    @Override
    public List<Item> findAllByUsername(String username) {
        return itemRepo.findAllByUsername(username);
    }

    @Override
    public Item findItemById(Long id) {
        return itemRepo.findOne(id);
    }

    public Item add(Item item) {
        Item savedItem = itemRepo.save(item);
        item = applyTagItemRelations(item);
        tagRepo.save(item.getTags());
        return savedItem;
    }

    public Item update(Item item) {
        item = applyTagItemRelations(item);
        tagRepo.deleteByItem(item);
        tagRepo.save(item.getTags());
        return itemRepo.save(item);
    }

    private Item applyTagItemRelations(Item item){
        if(item.getTags() != null) {
            List<Tag> tags = item.getTags();
            for (Tag tag : tags) {
                tag.setItem(item);
                tag.setItemId(item.getId());
                tag.setId(null);
            }
            item.setTags(tags);
        }
        return item;
    }

    @Autowired
    public ItemDaoImpl(ItemRepository itemRepo, TagRepository tagRepo){
        this.itemRepo = itemRepo;
        this.tagRepo = tagRepo;
    }
}