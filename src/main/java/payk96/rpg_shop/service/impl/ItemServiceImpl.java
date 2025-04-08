package payk96.rpg_shop.service.impl;

import org.apache.commons.lang3.StringUtils;
import payk96.rpg_shop.dto.ItemRequest;
import payk96.rpg_shop.dto.ItemResponse;
import payk96.rpg_shop.exception.ItemNotFoundException;
import payk96.rpg_shop.model.Item;
import payk96.rpg_shop.model.ItemRarity;
import payk96.rpg_shop.model.ItemType;
import payk96.rpg_shop.repository.ItemRepo;
import payk96.rpg_shop.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    private final ItemRepo repo;

    @Override
    public ItemResponse createItem(ItemRequest request) {
        Item item = ItemRequest.toModel(request);
        return ItemResponse.toResponse(repo.save(item));
    }

    @Override
    public ItemResponse updateItemById(long id, ItemRequest request) {
        Item existingItem = repo.findById(id)
                .orElseThrow(() -> new ItemNotFoundException("Item not found with id: " + id));

        Item updatedItem = ItemRequest.toModel(request);
        updatedItem.setId(existingItem.getId());

        return ItemResponse.toResponse(repo.save(updatedItem));
    }

    @Override
    public List<ItemResponse> getAll() {
        return repo.findAll().stream()
                .map(ItemResponse::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ItemResponse getById(long id) {
        return repo.findById(id)
                .map(ItemResponse::toResponse)
                .orElseThrow(() -> new ItemNotFoundException("Item not found with id: " + id));
    }

    @Override
    public void deleteById(long id) {
        if (!repo.existsById(id)) {
            throw new ItemNotFoundException("Item not found with id: " + id);
        }
        repo.deleteById(id);
    }

    public List<ItemResponse> searchByName(String name, int limit) {
        if (name == null || name.trim().isEmpty()) {
            return Collections.emptyList();
        }

        String searchTerm = name.toLowerCase();
        List<Item> allItems = repo.findAll();

        return allItems.stream()
                .map(item -> {
                    String itemName = item.getName().toLowerCase();
                    boolean exactMatch = itemName.equals(searchTerm);
                    boolean startsWith = itemName.startsWith(searchTerm);
                    boolean contains = itemName.contains(searchTerm);
                    int distance = StringUtils.getLevenshteinDistance(itemName, searchTerm);

                    return new AbstractMap.SimpleEntry<>(
                            item,
                            new int[] {
                                    exactMatch ? 0 : 1,
                                    startsWith ? 0 : 1,
                                    contains ? 0 : 1,
                                    distance,
                                    itemName.length()
                            }
                    );
                })
                .sorted((e1, e2) -> {
                    int[] key1 = e1.getValue();
                    int[] key2 = e2.getValue();
                    for (int i = 0; i < key1.length; i++) {
                        if (key1[i] != key2[i]) {
                            return Integer.compare(key1[i], key2[i]);
                        }
                    }
                    return 0;
                })
                .limit(limit)
                .map(AbstractMap.SimpleEntry::getKey)
                .map(ItemResponse::toResponse)
                .collect(Collectors.toList());
    }

    public List<ItemResponse> filterItems(Integer minPrice, Integer maxPrice, ItemType type, ItemRarity rarity) {
        return repo.findAll().stream()
                .filter(item -> minPrice == null || item.getPrice() >= minPrice)
                .filter(item -> maxPrice == null || item.getPrice() <= maxPrice)
                .filter(item -> type == null || item.getType() == type)
                .filter(item -> rarity == null || item.getRarity() == rarity)
                .map(ItemResponse::toResponse)
                .collect(Collectors.toList());
    }
}