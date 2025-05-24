package payk96.rpg_shop.controller;

import io.swagger.v3.oas.annotations.Operation;
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

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Tag(name = "ItemController", description = "Контроллер для работы с предметами. Для всех методов требуется авторизация — access token можно получить через /auth/login, передав username и password.")
public class ItemController {
    private final ItemService service;

    @Operation(summary = "Создать новый предмет")
    @PostMapping("/items")
    @ResponseStatus(HttpStatus.CREATED)
    public ItemResponse createItem(@RequestBody @Valid ItemRequest request) {
        return service.createItem(request);
    }

    @Operation(summary = "Обновить предмет по ID")
    @PutMapping("/items/{id}")
    public ItemResponse updateItem(@PathVariable long id,
                                   @RequestBody @Valid ItemRequest request) {
        return service.updateItemById(id, request);
    }

    @Operation(summary = "Получить список всех предметов")
    @GetMapping("/items")
    public List<ItemResponse> getAllItems() {
        return service.getAll();
    }

    @Operation(summary = "Получить предмет по ID")
    @GetMapping("/items/{id}")
    public ItemResponse getItemById(@PathVariable long id) {
        return service.getById(id);
    }

    @Operation(summary = "Удалить предмет по ID")
    @DeleteMapping("/items/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteItem(@PathVariable long id) {
        service.deleteById(id);
    }

    @Operation(summary = "Поиск предметов по имени")
    @GetMapping("/items/search")
    public List<ItemResponse> searchItems(
            @RequestParam String name,
            @RequestParam(required = false, defaultValue = "10") int limit) {
        return service.searchByName(name, limit);
    }

    @Operation(summary = "Фильтрация предметов")
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
