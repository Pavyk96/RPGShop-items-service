package payk96.rpg_shop.metrics;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.DistributionSummary;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Component
public class OrderMetrics {

    private final Map<Long, AtomicInteger> itemCounts = new ConcurrentHashMap<>();
    private final MeterRegistry meterRegistry;
    private final DistributionSummary basketSizeSummary;

    public OrderMetrics(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;

        // ✅ метрика для размера корзины
        this.basketSizeSummary = DistributionSummary.builder("orders.basket.size")
                .description("Размер корзины в заказе (кол-во товаров)")
                .baseUnit("items")
                .register(meterRegistry);
    }

    public void recordItems(List<Long> itemIds) {
        itemIds.forEach(id -> {
            itemCounts.computeIfAbsent(id, k -> {
                Counter counter = Counter.builder("orders.items.count")
                        .tag("itemId", String.valueOf(k))
                        .description("Количество заказов по каждому товару")
                        .register(meterRegistry);
                return new AtomicInteger();
            }).incrementAndGet();

            meterRegistry.counter("orders.items.count", "itemId", String.valueOf(id)).increment();
        });

        // ✅ обновляем статистику размера корзины
        recordBasketSize(itemIds.size());
    }

    public void recordBasketSize(int size) {
        basketSizeSummary.record(size);
    }

    public Map<Long, Integer> getPopularItems() {
        return itemCounts.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().get()));
    }
}
