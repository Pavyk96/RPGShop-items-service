package payk96.rpg_shop.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import payk96.rpg_shop.dto.OrderRequest;
import payk96.rpg_shop.dto.OrderResponse;
import payk96.rpg_shop.model.OrderStatus;
import payk96.rpg_shop.service.OrderService;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/orders")
    public OrderResponse create(@RequestBody OrderRequest request) {
        return orderService.createOrder(request);
    }

    @GetMapping("/orders/{id}")
    public OrderResponse getById(@PathVariable Long id) {
        return orderService.getOrder(id);
    }

    @GetMapping("/orders")
    public List<OrderResponse> getAll() {
        return orderService.getAll();
    }

    @PutMapping("/orders/{id}")
    public OrderResponse update(@PathVariable Long id, @RequestBody OrderStatus request) {
        return orderService.updateOrderStatus(id, request);
    }

    @DeleteMapping("/orders/{id}")
    public void delete(@PathVariable Long id) {
        orderService.deleteOrder(id);
    }
}