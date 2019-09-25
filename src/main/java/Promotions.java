import java.math.BigDecimal;

class Promotions {
    private final String serialNumber;
    private final String itemOnPromo;
    final BigDecimal promoPrice;
    final Integer promoQty;

    Promotions(String serialNumber, String itemOnPromo, BigDecimal promoPrice, Integer promoQty) {
        this.serialNumber = serialNumber;
        this.itemOnPromo = itemOnPromo;
        this.promoPrice = promoPrice;
        this.promoQty = promoQty;
    }
}
