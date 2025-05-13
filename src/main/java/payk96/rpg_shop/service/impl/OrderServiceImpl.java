package payk96.rpg_shop.service.impl;

import io.micrometer.core.annotation.Timed;
import lombok.extern.slf4j.Slf4j;
import payk96.rpg_shop.dto.OrderRequest;
import payk96.rpg_shop.dto.OrderResponse;
import payk96.rpg_shop.exception.IllegalOrderStatusException;
import payk96.rpg_shop.exception.OrderNotFoundException;
import payk96.rpg_shop.kafka.KafkaOrderEventProducer;
import payk96.rpg_shop.metrics.OrderMetrics;
import payk96.rpg_shop.model.Order;
import payk96.rpg_shop.model.OrderStatus;
import payk96.rpg_shop.repository.OrderRepository;
import payk96.rpg_shop.service.ItemService;
import payk96.rpg_shop.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final KafkaOrderEventProducer kafkaProducer;
    private final ItemService itemService;
    private final OrderMetrics orderMetrics;


    @Override
    public List<OrderResponse> getAll() {
        return orderRepository.findAll().stream().map(OrderResponse::toResponse).toList();
    }

    @Timed(value = "orders.create.time", description = "Время выполнения создания заказа")
    @Override
    @Transactional
    public OrderResponse createOrder(OrderRequest request) {
        Order order = Order.builder()
                .customerId(request.customerId())
                .itemIds(request.itemIds())
                .createdAt(LocalDateTime.now())
                .status(OrderStatus.NEW)
                .build();

        Order savedOrder = orderRepository.save(order);
        orderMetrics.recordItems(request.itemIds());
        orderMetrics.recordBasketSize(request.itemIds().size());
        try {
            kafkaProducer.sendOrderCreatedEvent(savedOrder);
        } catch (Exception e) {
            log.error("Failed to send order created event", e);
        }

        return OrderResponse.toResponse(savedOrder);
    }

    @Override
    @Transactional(readOnly = true)
    public OrderResponse getOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException(orderId));
        return OrderResponse.toResponse(order);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderResponse> getOrdersByCustomer(String customerId) {
        return orderRepository.findByCustomerId(customerId).stream()
                .map(OrderResponse::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public OrderResponse updateOrderItems(Long orderId, List<Long> newItemIds) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException(orderId));

        if (order.getStatus() != OrderStatus.NEW) {
            throw new IllegalOrderStatusException("Cannot modify items in order with status: " + order.getStatus());
        }

        order.setItemIds(newItemIds);
        Order updatedOrder = orderRepository.save(order);
        return OrderResponse.toResponse(updatedOrder);
    }

    @Override
    @Transactional
    public OrderResponse updateOrderStatus(Long orderId, OrderStatus newStatus) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException(orderId));

        validateStatusTransition(order.getStatus(), newStatus);

        order.setStatus(newStatus);
        Order updatedOrder = orderRepository.save(order);
        return OrderResponse.toResponse(updatedOrder);
    }

    @Override
    @Transactional
    public void deleteOrder(Long orderId) {
        if (!orderRepository.existsById(orderId)) {
            throw new OrderNotFoundException(orderId);
        }
        orderRepository.deleteById(orderId);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean containsItem(Long orderId, Long itemId) {
        return orderRepository.findById(orderId)
                .map(order -> order.getItemIds().contains(itemId))
                .orElseThrow(() -> new OrderNotFoundException(orderId));
    }


    private void validateStatusTransition(OrderStatus currentStatus, OrderStatus newStatus) {
        if (currentStatus == OrderStatus.CANCELLED || currentStatus == OrderStatus.COMPLETED) {
            throw new IllegalOrderStatusException("Cannot change status from " + currentStatus);
        }

        if (newStatus == OrderStatus.NEW && currentStatus != OrderStatus.NEW) {
            throw new IllegalOrderStatusException("Cannot revert to NEW status");
        }
    }
}