package net.sp0gg.fridgezone.data.rest;

import net.sp0gg.fridgezone.data.dao.interfaces.TagDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

/**
 * Created by sp0gg on 10/23/15.
 */
@RestController
@RequestMapping("/api")
public class TagRestController {

    Logger log = LoggerFactory.getLogger(this.getClass());

    TagDao tagDao;

    @RequestMapping(value = "/tags", method = RequestMethod.GET)
    public @ResponseBody
    Collection<String> findAll(){
        log.info("Retrieving tag name list");
        return tagDao.findAllNames();
    }

    @Autowired
    public TagRestController(TagDao tagDao){
        this.tagDao = tagDao;
    }
}
