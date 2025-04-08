package payk96.rpg_shop.service;

import payk96.rpg_shop.dto.ItemRequest;
import payk96.rpg_shop.dto.ItemResponse;
import payk96.rpg_shop.model.ItemRarity;
import payk96.rpg_shop.model.ItemType;

import java.util.List;

public interface ItemService {
    ItemResponse createItem(ItemRequest request);
    ItemResponse updateItemById(long id, ItemRequest request);
    List<ItemResponse> getAll();
    ItemResponse getById(long id);
    void deleteById(long id);
    List<ItemResponse> searchByName (String name, int limit);
    List<ItemResponse> filterItems(Integer minPrice, Integer maxPrice, ItemType type, ItemRarity rarity);
}
