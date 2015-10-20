package net.sp0gg.fridgezone.data.dao;

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
    Logger log = LoggerFactory.getLogger(this.getClass());

    private ItemRepository itemRepo;
    private TagRepository tagRepo;

    @Override
    public List<Item> findAll() {
        return itemRepo.findAll();
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

    private List<Item> applyTagItemRelations(List<Item> items){
        List<Item> returnItems = new ArrayList<>();
        for (Item item : items) {
            item = applyTagItemRelations(item);
            returnItems.add(item);
        }
        return returnItems;
    }

    @Autowired
    public ItemDaoImpl(ItemRepository itemRepo, TagRepository tagRepo){
        this.itemRepo = itemRepo;
        this.tagRepo = tagRepo;
    }

}