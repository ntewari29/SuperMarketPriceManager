import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@Data
@RequiredArgsConstructor
class Inventory {
    private final String serialNumber;
    private final String itemName;
    private final BigDecimal itemPrice;
    private final Integer storeQty;
    private final Boolean hasPromotion;
}
