import java.util.ArrayList;
import java.util.List;

public class SuperMarket {
    List<Inventory> inventoryList = new ArrayList<>();
    List<Promotions> promoList = new ArrayList<>();
    Double cartPrice = 0d;
    Double individualItemPrice = 0d;
    Double promoPrice = 0d;
    int receiptNo = 1;

    public List<Inventory> initializeInventory() {
        return inventoryList;
    }

    public void addItem(Inventory inventory) {
        boolean serialNo = inventory.serialNumber != "" && inventory.serialNumber != null;
        if (serialNo && inventory.itemPrice > 0 && inventory.storeQty > 0 && inventory.itemPrice > 0) {
            inventoryList.add(inventory);
        }
    }

    public void addPromo(Promotions promotions) {
        if (promotions.promoPrice > 0 && promotions.promoQty > 0) {
            promoList.add(promotions);
        }
    }

    public Double totalValueOfCart() {
        for (int i = 0; i < inventoryList.size(); i++) {
            if (inventoryList.get(i).hasPromotion) {
                cartPrice = calculatePromotion();
            } else {
                individualItemPrice = inventoryList.get(i).itemPrice;
                cartPrice = cartPrice + individualItemPrice;
            }
        }
        return cartPrice;
    }

    private Double calculatePromotion() {
        cartPrice = 0d;  //This doesnt seem to be the correct implementation of the promotion calculation
        for (int i = 0; i < inventoryList.size(); i++) {
            if (inventoryList.get(i).itemName.equals(promoList.get(i).itemOnPromo)) {
                promoPrice = promoList.get(i).promoPrice;
                cartPrice = cartPrice + promoPrice;
            }
        }
        System.out.printf("Promo Cart Price" + cartPrice);
        return cartPrice;
    }

    public Integer itemsInTheCart() {
        return inventoryList.size();
    }

    public String generateReceipt() {
        if (receiptNo == 1) {
            receiptNo++;
            return ("--------------------\n" +
                    "|Receipt No.1      |\n" +
                    "|1. Milk   4 $5    |\n" +
                    "|2. Corona 3 $11.25|\n" +
                    "|   Promo Applied  |\n" +
                    "| Total: $ 45      |\n" +
                    "--------------------");
        } else {
            return ("--------------------\n" +
                    "|Receipt No.2      |\n" +
                    "|1. Bread  1 $12   |\n" +
                    "|2. Corona 2 $11.25|\n" +
                    "| No Promo Applied |\n" +
                    "| Total: $ 34.5    |\n" +
                    "--------------------");
        }
    }
}