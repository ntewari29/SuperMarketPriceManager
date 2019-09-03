import java.util.List;

public class Inventory {
    public final String serialNumber;
    public final String itemName;
    public final Double itemPrice;
    public final Integer storeQty;
    public final Boolean hasPromotion;

    public Inventory(String serialNumber, String itemName, Double itemPrice, Integer storeQty, Boolean hasPromotion) {
        this.serialNumber = serialNumber;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.storeQty = storeQty;
        this.hasPromotion = hasPromotion;
    }
}
