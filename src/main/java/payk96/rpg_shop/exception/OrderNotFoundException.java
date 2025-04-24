package payk96.rpg_shop.exception;

public class OrderNotFoundException extends RuntimeException {
  public OrderNotFoundException(Long orderId) {
    super("Order not found with id: " + orderId);
  }
}
