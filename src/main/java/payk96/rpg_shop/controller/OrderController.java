package payk96.rpg_shop.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "OrderController", description = "Контроллер для работы с заказами. Для всех методов требуется авторизация — access token можно получить через /auth/login, передав username и password.")
public class OrderController {
    private final OrderService orderService;

    @Operation(summary = "Создать новый заказ, вызывает уведомление на почту")
    @PostMapping("/orders")
    public OrderResponse create(@RequestBody OrderRequest request) {
        return orderService.createOrder(request);
    }

    @Operation(summary = "Получить заказ по ID")
    @GetMapping("/orders/{id}")
    public OrderResponse getById(@PathVariable Long id) {
        return orderService.getOrder(id);
    }

    @Operation(summary = "Получить список всех заказов")
    @GetMapping("/orders")
    public List<OrderResponse> getAll() {
        return orderService.getAll();
    }

    @Operation(summary = "Обновить статус заказа по ID")
    @PutMapping("/orders/{id}")
    public OrderResponse update(@PathVariable Long id, @RequestBody OrderStatus request) {
        return orderService.updateOrderStatus(id, request);
    }

    @Operation(summary = "Удалить заказ по ID")
    @DeleteMapping("/orders/{id}")
    public void delete(@PathVariable Long id) {
        orderService.deleteOrder(id);
    }
}
