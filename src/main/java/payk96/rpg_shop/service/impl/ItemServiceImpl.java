package payk96.rpg_shop.service.impl;

import payk96.rpg_shop.dto.ItemRequest;
import payk96.rpg_shop.dto.ItemResponse;
import payk96.rpg_shop.exception.ItemNotFoundException;
import payk96.rpg_shop.model.Item;
import payk96.rpg_shop.repository.ItemRepo;
import payk96.rpg_shop.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
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
}
