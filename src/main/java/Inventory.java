public class Inventory {
    public final String serialNumber;
    public final String itemName;
    public final Double itemPrice;

    public Inventory(String serialNumber, String itemName, Double itemPrice) {
        this.serialNumber = serialNumber;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
    }
}
