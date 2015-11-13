package net.sp0gg.fridgezone.data.dao.interfaces;

import java.util.List;

/**
 * Created by sp0gg on 10/23/15.
 */
public interface TagDao {
    List<String> findAllNames(String username);
}