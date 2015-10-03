package net.sp0gg.fridgezone.data.rest;

import net.sp0gg.fridgezone.data.dao.ItemDao;
import net.sp0gg.fridgezone.data.repository.ItemRepository;
import net.sp0gg.fridgezone.domain.Item;
import net.sp0gg.fridgezone.domain.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by sp0gg on 8/21/15.
 */

@RestController
@RequestMapping("/api")
public class ItemRestController {

    Logger log = LoggerFactory.getLogger(this.getClass());

    private ItemDao itemDao;
    private ItemRepository itemRepo;

    @Autowired
    public ItemRestController(ItemDao itemDao, ItemRepository itemRepo){
        this.itemDao = itemDao;
        this.itemRepo = itemRepo;
    }

    @RequestMapping(value = "/items", method = RequestMethod.GET)
    public @ResponseBody Collection<Item> findAll(){return applyTagItemRelations(itemRepo.findAll());}

    @RequestMapping(value = "/items", method = RequestMethod.POST)
    public @ResponseBody Item addItem(@RequestBody Item item) {
        item = (applyTagItemRelations(item));
        Item itemReturned = itemRepo.save(item);
        itemReturned = applyTagItemRelations(itemReturned);
        return itemReturned;
    }

    @RequestMapping(value = "/items/{id}", method = RequestMethod.PUT)
    public @ResponseBody Item updateItem(@RequestBody Item item) {
        item = (applyTagItemRelations(item));
        Item itemReturned = itemDao.update(item);
        itemReturned = applyTagItemRelations(itemReturned);
        return itemReturned;
    }

    private Item applyTagItemRelations(Item item){
        if(item.getTags() != null) {
            List<Tag> tags = item.getTags();
            for (Tag tag : tags) {
                tag.setItem(item);
                tag.setItemId(item.getId());
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

}