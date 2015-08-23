package net.sp0gg.fridgezone.data.rest;

import net.sp0gg.fridgezone.data.repository.ItemRepository;
import net.sp0gg.fridgezone.domain.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

/**
 * Created by sp0gg on 8/21/15.
 */

@RestController
@RequestMapping("/api")
public class ItemRestController {

    @Autowired
    private ItemRepository itemRepo;

    @RequestMapping(value = "/items", method = RequestMethod.GET)
    public @ResponseBody Collection<Item> findAll(){
        return itemRepo.findAll();
    }

    @RequestMapping(value = "/items", method = RequestMethod.POST)
    public @ResponseBody Item addItem(@RequestBody Item item) {return itemRepo.save(item);}

    @RequestMapping(value = "/items/{id}", method = RequestMethod.PUT)
    public @ResponseBody Item updateItem(@RequestBody Item item) {return itemRepo.save(item);}

}
