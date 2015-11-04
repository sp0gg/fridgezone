package net.sp0gg.fridgezone.data.dao.impl;

import net.sp0gg.fridgezone.data.dao.interfaces.TagDao;
import net.sp0gg.fridgezone.data.repository.TagRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by sp0gg on 10/24/15.
 */
@Component
public class TagDaoImpl implements TagDao {

    private final TagRepository tagRepository;

    @Override
    public List<String> findAllNames() {
        return tagRepository.findDistinctTagNames();
    }

    @Autowired
    public TagDaoImpl(TagRepository tagRepository){
        this.tagRepository = tagRepository;
    }
}
