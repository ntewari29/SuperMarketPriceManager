import java.math.BigDecimal;
import java.util.ArrayList;
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
        String serialNumber = inventory.getSerialNumber();
        boolean hasSerialNo = (serialNumber != null && !serialNumber.equals(""));
        if (hasSerialNo
                && inventory.getItemPrice().compareTo(BigDecimal.ZERO) > 0
                && inventory.getStoreQty() > 0) {
            inventoryList.add(inventory);
        }
    }

    void addPromo(Promotions promotions) {
        if (promotions.getPromoPrice().compareTo(BigDecimal.ZERO) > 0 && promotions.getPromoQty() > 0) {
            promoList.add(promotions);
        }
    }

    BigDecimal totalValueOfCart() {
        cartPrice = BigDecimal.valueOf(0d);
        for (Cart cart : myCart) {
            for (Inventory inventory : inventoryList) {
                if (cart.getItem().getItemName().equals(inventory.getItemName())) {
                    BigDecimal individualItemPrice = inventory.getItemPrice().multiply(BigDecimal.valueOf(cart.getQty()));
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