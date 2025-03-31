package com.payk96.rpg_shop.controller;

import com.payk96.rpg_shop.dto.ItemRequest;
import com.payk96.rpg_shop.dto.ItemResponse;
import com.payk96.rpg_shop.service.ItemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ItemController {
    private final ItemService service;

    @PostMapping
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
}