package net.sp0gg.fridgezone.data.repository;

import net.sp0gg.fridgezone.domain.Item;
import net.sp0gg.fridgezone.domain.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by sp0gg on 10/2/15.
 */
@Repository
@Transactional
public interface TagRepository extends JpaRepository<Tag, Long> {
    void deleteByItem(Item item);
}
