package net.sp0gg.fridgezone.data.dao;

import net.sp0gg.fridgezone.data.repository.ItemRepository;
import net.sp0gg.fridgezone.data.repository.TagRepository;
import net.sp0gg.fridgezone.domain.Item;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by sp0gg on 10/3/15.
 */
@Component
public class ItemDaoImpl implements ItemDao {
    Logger log = LoggerFactory.getLogger(this.getClass());

    private ItemRepository itemRepo;
    private TagRepository tagRepo;

    public Item update(Item item) {
        if (item.getTags().isEmpty()) {
            tagRepo.deleteByItem(item);
        }else{
            tagRepo.save(item.getTags());
        }

        return itemRepo.save(item);
    }

    @Autowired
    public ItemDaoImpl(ItemRepository itemRepo, TagRepository tagRepo){
        this.itemRepo = itemRepo;
        this.tagRepo = tagRepo;
    }

}
