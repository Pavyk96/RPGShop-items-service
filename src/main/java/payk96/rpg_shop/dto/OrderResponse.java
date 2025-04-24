package payk96.rpg_shop.dto;

import payk96.rpg_shop.model.Order;
import payk96.rpg_shop.model.OrderStatus;

import java.time.LocalDateTime;
import java.util.List;

public record OrderResponse(
        Long id,
        String customerId,
        List<Long> itemIds,
        LocalDateTime createdAt,
        OrderStatus status
) {
    public static OrderResponse toResponse(Order order) {
        return new OrderResponse(
                order.getId(),
                order.getCustomerId(),
                order.getItemIds(),
                order.getCreatedAt(),
                order.getStatus()
        );
    }
}