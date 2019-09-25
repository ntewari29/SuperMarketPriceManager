import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class SuperMarket {
    private List<Cart> myCart = new ArrayList<>();
    private List<Inventory> inventoryList = new ArrayList<>();
    private List<Promotions> promoList = new ArrayList<>();
    private BigDecimal cartPrice = BigDecimal.ZERO;

    List<Inventory> initializeInventory() {
        return inventoryList;
    }

    void addItemToInventory(Inventory inventory) {
        boolean hasSerialNo = (inventory.serialNumber != "" || inventory.serialNumber != null);
        if (hasSerialNo && inventory.itemPrice.compareTo(BigDecimal.ZERO) > 0 && inventory.storeQty > 0 && inventory.itemPrice.compareTo(BigDecimal.ZERO) > 0) {
            inventoryList.add(inventory);
        }
    }

    void addPromo(Promotions promotions) {
        if (promotions.promoPrice.compareTo(BigDecimal.ZERO) > 0 && promotions.promoQty > 0) {
            promoList.add(promotions);
        }
    }

    BigDecimal totalValueOfCart() {
        cartPrice = BigDecimal.valueOf(0d);
        for (Cart cart : myCart) {
            for (Inventory inventory : inventoryList) {
                if (cart.itemName.equals(inventory.itemName)) {
                    BigDecimal individualItemPrice = inventory.itemPrice.multiply(BigDecimal.valueOf(cart.qty));
                    cartPrice = cartPrice.add(individualItemPrice);
                }
            }
        }
        return cartPrice;
    }

    private BigDecimal calculatePromotion() {
        return cartPrice;
    }

    public Integer itemsInTheInventory() {
        return inventoryList.size();
    }

    int itemsInTheCart() {
        return myCart.size();
    }

    String generateReceipt() {
        if (inventoryList.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            String headAndtail = "--------------------";
            String boundary = "|                  |";
            sb.append(headAndtail + "\n");
            for (int i = 0; i <= 4; i++) {
                sb.append(boundary + "\n");
            }
            sb.append(headAndtail);
            return sb.toString();
        }         return null;
    }

    void addItemsToTheCart(Inventory itemName, int qty) {
        myCart.add(new Cart(itemName, qty));
    }

    List<Inventory> getInventoryList() {
        return inventoryList;
    }
}