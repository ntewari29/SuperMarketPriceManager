import java.math.BigDecimal;

class Inventory {
    private final Boolean hasPromotion;
    private final String serialNumber;
    private final String itemName;
    private final BigDecimal itemPrice;
    private final Integer storeQty;

    Inventory(String serialNumber, String itemName, BigDecimal itemPrice, Integer storeQty, Boolean hasPromotion) {
        this.serialNumber = serialNumber;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.storeQty = storeQty;
        this.hasPromotion = hasPromotion;
    }

    public Boolean getHasPromotion() {
        return hasPromotion;
    }

    String getSerialNumber() {
        return serialNumber;
    }

    String getItemName() {
        return itemName;
    }

    BigDecimal getItemPrice() {
        return itemPrice;
    }

    Integer getStoreQty() {
        return storeQty;
    }
}