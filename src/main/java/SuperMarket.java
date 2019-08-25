import java.util.ArrayList;
import java.util.List;

public class SuperMarket {
    List<Inventory> inventoryList = new ArrayList<>();
    Double cartPrice = 0d;
    Double idividualItemPrice = 0d;

    public List<Inventory> initializeInventory() {
        return inventoryList;
    }

    public void addItem(Inventory inventory) {
        inventoryList.add(inventory);
    }

    public Double totalValueOfCart() {
        for (int i = 0; i < inventoryList.size(); i++) {
            idividualItemPrice = inventoryList.get(i).itemPrice;
            cartPrice = cartPrice + idividualItemPrice;
        }
        return cartPrice;
    }
}
