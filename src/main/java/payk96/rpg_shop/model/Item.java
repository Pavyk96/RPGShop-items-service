package payk96.rpg_shop.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "items")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private ItemType type;

    @Enumerated(EnumType.STRING)
    private ItemRarity rarity;

    private int price;

    private String description;

    @ElementCollection
    @CollectionTable(name = "item_stats", joinColumns = @JoinColumn(name = "item_id"))
    @MapKeyColumn(name = "stat_name")
    @Column(name = "stat_value")
    private Map<String, Integer> stats;
}

