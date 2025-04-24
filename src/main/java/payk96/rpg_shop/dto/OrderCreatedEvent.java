package payk96.rpg_shop.dto;

import payk96.rpg_shop.model.ItemRarity;
import payk96.rpg_shop.model.ItemType;

import java.time.LocalDateTime;
import java.util.List;

public record OrderCreatedEvent(
        Long orderId,
        String customerId,
        List<OrderItemDto> items,
        LocalDateTime createdAt
) {
    public record OrderItemDto(
            Long id,
            String name,
            ItemType type,
            ItemRarity rarity,
            int price
    ) {}
}
