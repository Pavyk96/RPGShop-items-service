package payk96.rpg_shop.controller;

import payk96.rpg_shop.dto.ItemRequest;
import payk96.rpg_shop.dto.ItemResponse;
import payk96.rpg_shop.model.ItemRarity;
import payk96.rpg_shop.model.ItemType;
import payk96.rpg_shop.service.ItemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ItemController {
    private final ItemService service;

    @PostMapping("/items")
    @ResponseStatus(HttpStatus.CREATED)
    public ItemResponse createItem(@RequestBody @Valid ItemRequest request) {
        return service.createItem(request);
    }

    @PutMapping("/items/{id}")
    public ItemResponse updateItem(@PathVariable long id,
                                   @RequestBody @Valid ItemRequest request) {
        return service.updateItemById(id, request);
    }

    @GetMapping("/items")
    public List<ItemResponse> getAllItems() {
        return service.getAll();
    }

    @GetMapping("/items/{id}")
    public ItemResponse getItemById(@PathVariable long id) {
        return service.getById(id);
    }

    @DeleteMapping("/items/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteItem(@PathVariable long id) {
        service.deleteById(id);
    }

    @GetMapping("/items/search")
    public List<ItemResponse> searchItems(
            @RequestParam String name,
            @RequestParam(required = false, defaultValue = "10") int limit) {
        return service.searchByName(name, limit);
    }

    @GetMapping("/items/filter")
    public List<ItemResponse> filterItems(
            @RequestParam(required = false) Integer minPrice,
            @RequestParam(required = false) Integer maxPrice,
            @RequestParam(required = false) ItemType type,
            @RequestParam(required = false) ItemRarity rarity
    ) {
        return service.filterItems(minPrice, maxPrice, type, rarity);
    }
}