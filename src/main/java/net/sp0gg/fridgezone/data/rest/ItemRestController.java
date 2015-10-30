package net.sp0gg.fridgezone.data.rest;

import net.sp0gg.fridgezone.domain.Item;
import net.sp0gg.fridgezone.service.ItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

/**
 * Created by sp0gg on 8/21/15.
 */

@RestController
@RequestMapping("/api")
public class ItemRestController {

    Logger log = LoggerFactory.getLogger(this.getClass());

    private ItemService itemService;

    @Autowired
    public ItemRestController(ItemService itemService){
        this.itemService = itemService;
    }

    @RequestMapping(value = "/items", method = RequestMethod.GET)
    public @ResponseBody Collection<Item> findAll(){return itemService.fetchAll();}

    @RequestMapping(value = "/items", method = RequestMethod.POST)
    public @ResponseBody Item addItem(@RequestBody Item item) {
        Item itemReturned = itemService.addItem(item);
        return itemReturned;
    }

    @RequestMapping(value = "/items/{id}", method = RequestMethod.PUT)
    public @ResponseBody Item updateItem(@RequestBody Item item) {
        Item itemReturned = itemService.updateItem(item);
        return itemReturned;
    }

}