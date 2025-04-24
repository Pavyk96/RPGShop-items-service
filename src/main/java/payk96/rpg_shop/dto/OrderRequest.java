package payk96.rpg_shop.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import payk96.rpg_shop.model.Order;

import java.util.List;

public record OrderRequest(
        @NotBlank String customerId,
        @NotNull List<Long> itemIds
) {
    public Order toModel() {
        return Order.builder()
                .customerId(customerId)
                .itemIds(itemIds)
                .build();
    }
}
