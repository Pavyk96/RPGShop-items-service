package payk96.rpg_shop.service;

import payk96.rpg_shop.dto.OrderRequest;
import payk96.rpg_shop.dto.OrderResponse;
import payk96.rpg_shop.model.OrderStatus;

import java.util.List;

public interface OrderService {
    List<OrderResponse> getAll();

    OrderResponse createOrder(OrderRequest request);

    OrderResponse getOrder(Long orderId);

    List<OrderResponse> getOrdersByCustomer(String customerId);

    OrderResponse updateOrderItems(Long orderId, List<Long> newItemIds);

    OrderResponse updateOrderStatus(Long orderId, OrderStatus newStatus);

    void deleteOrder(Long orderId);

    boolean containsItem(Long orderId, Long itemId);
}
