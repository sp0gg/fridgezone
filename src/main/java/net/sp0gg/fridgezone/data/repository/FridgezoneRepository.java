package net.sp0gg.fridgezone.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.sp0gg.fridgezone.domain.Item;

@Repository
public interface FridgezoneRepository extends JpaRepository<Item, Long> {
	
}
