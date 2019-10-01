import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
class Cart {
    private final Inventory item;
    private final int qty;
}