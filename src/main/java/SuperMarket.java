import java.util.ArrayList;
import java.util.List;

public class SuperMarket {
    List<Inventory> inventoryList = new ArrayList<>();
    Double cartPrice = 0d;
    Double individualItemPrice = 0d;

    public List<Inventory> initializeInventory() {
        return inventoryList;
    }

    public void addItem(Inventory inventory) {
        if (inventory.itemPrice > 0 && inventory.storeQty > 0) {
            inventoryList.add(inventory);
        }
    }

    public Double totalValueOfCart() {
        for (int i = 0; i < inventoryList.size(); i++) {
            if (inventoryList.get(i).hasPromotion) {
                cartPrice = calculatePromotion();
            }
            else {
                individualItemPrice = inventoryList.get(i).itemPrice;
                cartPrice = cartPrice + individualItemPrice;
            }
        }
        return cartPrice;
    }

    private Double calculatePromotion() {
        return 0d;
    }

    public Integer itemsInTheCart() {
        return inventoryList.size();
    }
}
