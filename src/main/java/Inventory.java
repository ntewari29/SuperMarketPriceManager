import java.math.BigDecimal;

class Inventory {
    private final Boolean hasPromotion;
    final String serialNumber;
    final String itemName;
    final BigDecimal itemPrice;
    final Integer storeQty;

    Inventory(String serialNumber, String itemName, BigDecimal itemPrice, Integer storeQty, Boolean hasPromotion) {
        this.serialNumber = serialNumber;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.storeQty = storeQty;
        this.hasPromotion = hasPromotion;
    }
}
