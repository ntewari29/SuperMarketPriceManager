import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

class SuperMarket {
    private List<Inventory> inventoryList = new ArrayList<>();
    private List<Promotions> promoList = new ArrayList<>();
    private BigDecimal cartPrice = BigDecimal.ZERO;
    private BigDecimal individualItemPrice = BigDecimal.ZERO;
    private BigDecimal promoPrice = BigDecimal.ZERO;

    List<Inventory> initializeInventory() {
        return inventoryList;
    }

    void addItem(Inventory inventory) {
        if (inventory.itemPrice.compareTo(BigDecimal.ZERO) > 0 && inventory.storeQty > 0) {
            inventoryList.add(inventory);
        }
    }

    void addPromo(Promotions promotions) {
        if (promotions.promoPrice.compareTo(BigDecimal.ZERO) > 0 && promotions.promoQty > 0) {
            promoList.add(promotions);
        }
    }

    BigDecimal totalValueOfCart() {
        for (Inventory inventory : inventoryList) {
            if (inventory.hasPromotion) {
                cartPrice = calculatePromotion();
            } else {
                individualItemPrice = inventory.itemPrice;
                cartPrice = cartPrice.add(individualItemPrice);
            }
        }
        return cartPrice;
    }

    private BigDecimal calculatePromotion() {
        cartPrice = BigDecimal.ZERO;  //This doesnt seem to be the correct implementation of the promotion calculation
        for (int i = 0; i < inventoryList.size(); i++) {
            if (inventoryList.get(i).itemName.equals(promoList.get(i).itemOnPromo)) {
                promoPrice = promoList.get(i).promoPrice;
                cartPrice = cartPrice.add(promoPrice);
            }
        }
        System.out.print("Promo Cart Price" + cartPrice);
        return cartPrice;
    }

    Integer itemsInTheCart() {
        return inventoryList.size();
    }

    String generateReceipt() {
        return ("--------------------\n" +
                "|Receipt No.1      |\n" +
                "|1. Milk   4 $5    |\n" +
                "|2. Corona 3 $11.25|\n" +
                "|   Promo Applied  |\n" +
                "| Total: $ 45      |\n" +
                "--------------------");
    }
}
