package payk96.rpg_shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import payk96.rpg_shop.model.Order;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByCustomerId(String customerId);
}
