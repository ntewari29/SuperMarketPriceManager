public class Promotions {
    public final String serialNumber;
    public final String itemOnPromo;
    public final Double promoPrice;
    public final Integer promoQty;

    public Promotions(String serialNumber, String itemOnPromo, Double promoPrice, Integer promoQty) {
        this.serialNumber = serialNumber;
        this.itemOnPromo = itemOnPromo;
        this.promoPrice = promoPrice;
        this.promoQty = promoQty;
    }
}
