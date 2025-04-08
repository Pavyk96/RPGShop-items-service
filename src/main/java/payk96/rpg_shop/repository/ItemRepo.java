package payk96.rpg_shop.repository;

import payk96.rpg_shop.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepo extends JpaRepository<Item, Long> {
    List<Item> findAllByNameContainingIgnoreCase(String name);
}
