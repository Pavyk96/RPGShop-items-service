package payk96.rpg_shop.dto;

import payk96.rpg_shop.model.Item;
import payk96.rpg_shop.model.ItemRarity;
import payk96.rpg_shop.model.ItemType;
import jakarta.annotation.Nullable;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class ItemRequest {
    @NotBlank
    private String name;
    @NotNull
    private ItemType type;
    @NotNull
    private ItemRarity rarity;
    @Positive
    private int price;
    @Nullable
    private String description;
    @Nullable
    private Map<String, Integer> stats;

    public static Item toModel(ItemRequest request) {
        return Item.builder()
                .name(request.getName())
                .type(request.getType())
                .rarity(request.getRarity())
                .price(request.getPrice())
                .description(request.getDescription())
                .stats(request.getStats())
                .build();
    }
}
