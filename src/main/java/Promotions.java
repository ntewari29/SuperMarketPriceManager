import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@Data
@RequiredArgsConstructor
class Promotions {
    private final String serialNumber;
    private final String itemOnPromo;
    private final BigDecimal promoPrice;
    private final Integer promoQty;
}
