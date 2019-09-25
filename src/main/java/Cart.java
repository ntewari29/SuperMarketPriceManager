import java.util.List;

public class Cart {
    public final Inventory itemName;
    public final int qty;

    public Cart(Inventory itemName, Integer qty) {
        this.itemName = itemName;
        this.qty = qty;
    }
}