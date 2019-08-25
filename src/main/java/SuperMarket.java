import java.util.ArrayList;
import java.util.List;

public class SuperMarket {
    List<Inventory> inventoryList = new ArrayList<>();

    public List<Inventory> initializeInventory() {
        return inventoryList;
    }

    public void addItem(Inventory inventory) {
        inventoryList.add(inventory);
    }
}
