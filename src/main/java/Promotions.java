import java.math.BigDecimal;

class Promotions {
    private final String serialNumber;
    private final String itemOnPromo;
    private final BigDecimal promoPrice;
    private final Integer promoQty;

    Promotions(String serialNumber, String itemOnPromo, BigDecimal promoPrice, Integer promoQty) {
        this.serialNumber = serialNumber;
        this.itemOnPromo = itemOnPromo;
        this.promoPrice = promoPrice;
        this.promoQty = promoQty;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public String getItemOnPromo() {
        return itemOnPromo;
    }

    public BigDecimal getPromoPrice() {
        return promoPrice;
    }

    public Integer getPromoQty() {
        return promoQty;
    }
}
