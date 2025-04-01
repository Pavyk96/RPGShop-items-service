package payk96.rpg_shop.dto;

import payk96.rpg_shop.model.Item;
import payk96.rpg_shop.model.ItemRarity;
import payk96.rpg_shop.model.ItemType;
import jakarta.persistence.Entity;
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
public class ItemResponse {
    private Long id;
    private String name;
    private ItemType type;
    private ItemRarity rarity;
    private int price;
    private String description;
    private Map<String, Integer> stats;

    public static ItemResponse toResponse(Item item) {
        return ItemResponse.builder()
                .id(item.getId())
                .name(item.getName())
                .type(item.getType())
                .rarity(item.getRarity())
                .price(item.getPrice())
                .description(item.getDescription())
                .stats(item.getStats())
                .build();
    }
}
