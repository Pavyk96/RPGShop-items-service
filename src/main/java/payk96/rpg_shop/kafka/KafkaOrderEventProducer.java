package payk96.rpg_shop.kafka;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import payk96.rpg_shop.dto.OrderCreatedEvent;
import payk96.rpg_shop.model.Order;
import payk96.rpg_shop.service.ItemService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class KafkaOrderEventProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    private final ItemService itemService;

    @Value("${kafka.topic.order-created}")
    private String orderCreatedTopic;

    public void sendOrderCreatedEvent(Order order) {
        List<OrderCreatedEvent.OrderItemDto> items = order.getItemIds().stream()
                .map(itemService::getById)
                .map(item -> new OrderCreatedEvent.OrderItemDto(
                        item.getId(),
                        item.getName(),
                        item.getType(),
                        item.getRarity(),
                        item.getPrice()
                ))
                .toList();

        OrderCreatedEvent event = new OrderCreatedEvent(
                order.getId(),
                order.getCustomerId(),
                items,
                order.getCreatedAt()
        );

        kafkaTemplate.send(orderCreatedTopic, event);
    }
}
