import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

class SuperMarket {
    public static final String paddedSpaces = "           ";
    private List<Cart> myCart = new ArrayList<>();
    private List<Item> itemList = new ArrayList<>();
    private List<Promotions> promoList = new ArrayList<>();
    BigDecimal priceWithoutDiscount = null;
    Item item;
    Cart cart;

    List<Item> initializeInventory() {
        return itemList;
    }

    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    Date date = new Date();
    String todaysDate = formatter.format(date);
    StringBuilder sb = new StringBuilder();
    String header = "\t\tSuperMarket Pricing Manager\n" +
            "\t\t\t\t\t\tDate: " + todaysDate + "\n" +
            "\n" +
            "Item No.\tDescription\t\tQuantity\t\tTotal Price\n" +
            "---------------------------------------------------------------";
    String receiptClosure = "---------------------------------------------------------------";
    String grandTotal = "\t\t\t\t\tGrand Total: $";
    String savings = "\t\t\t\t\t  You Saved: $";
    String tailer = "\t\tThanks for Visiting! Have a Nice Day";
    BigDecimal promotionValue;

    void addItemToInventory(Item item) {
        String serialNumber = item.getSerialNumber();
        boolean hasSerialNo = (serialNumber != null && !serialNumber.equals(""));
        if (hasSerialNo
                && item.getItemPrice().compareTo(BigDecimal.ZERO) > 0
                && item.getStoreQty() > 0) {
            itemList.add(item);
        }
        else {
            Feedback.report(item);
        }
    }

    void addPromo(Promotions promotions) {
        if (promotions.getPromoPrice().compareTo(BigDecimal.ZERO) > 0 && promotions.getPromoQty() > 0) {
            promoList.add(promotions);
        }
    }

    BigDecimal totalValueOfCart() {
        BigDecimal cartPrice = BigDecimal.ZERO;
        for (Cart cart : myCart) {
            for (Item item : itemList) {
                if (cart.getItem().getItemName().equals(item.getItemName())) {
                    BigDecimal individualItemPrice = item.getItemPrice().multiply(BigDecimal.valueOf(cart.getQty()));
                    cartPrice = cartPrice.add(individualItemPrice);
                }
            }
        }
        BigDecimal promotionValue = calculatePromotion();
        return cartPrice.subtract(promotionValue);
    }

    int itemsInTheCart() {
        return myCart.size();
    }

    String generateReceipt(List<Cart> myCart) {
        BigDecimal itemPrice;
        if (itemList.isEmpty()) {
            sb.append(header + "\n\n");
            sb.append(receiptClosure + "\n");
            sb.append(grandTotal + "\n");
            sb.append(savings + "\n");
            sb.append(tailer);
            return sb.toString();
        } else {
            sb.append(header + "\n");
            for (Cart cart : myCart) {
                Item item = cart.getItem();
                int checkoutQty = cart.getQty();
                sb.append(item.getSerialNumber() + paddedSpaces);
                sb.append(cart.getItem().getItemName());
                for (int i = cart.getItem().getItemName().length(); i < 16; i++) {
                    sb.append(" ");
                }
                sb.append(checkoutQty);
                for (Promotions promotions : promoList) {
                    if (promotions.getItemOnPromo().equals(cart.getItem().getItemName()) && promotions.getPromoQty().equals(cart.getQty())) {
                        itemPrice = cart.getItem().getItemPrice().multiply(BigDecimal.valueOf(cart.getQty())).subtract(calculatePromotion());
                        sb.append("               " + itemPrice + "\n");
                        break;
                    } else if (!promotions.getItemOnPromo().equals(cart.getItem().getItemName()) && !promotions.getPromoQty().equals(cart.getQty())) {
                        itemPrice = cart.getItem().getItemPrice().multiply(BigDecimal.valueOf(cart.getQty()));
                        sb.append("               " + itemPrice + "\n");
                        break;
                    }
                }
            }
        }
        sb.append(receiptClosure + "\n");
        sb.append(grandTotal + "  " + totalValueOfCart() + "\n");
        sb.append(savings + "  " + calculatePromotion() + "\n");
        sb.append(tailer);
        return sb.toString();
    }

    boolean addItemsToTheCart(Item itemName, int qty) {
        if (qty <= itemName.getStoreQty()) {
            return myCart.add(new Cart(itemName, qty));
        } else {
            Feedback.report(qty, itemName);
        }
        return false;
    }

    List<Cart> getMyCart() {
        return myCart;
    }

    List<Item> getItemList() {
        return itemList;
    }

    private BigDecimal calculatePromotion() {
        promotionValue = BigDecimal.ZERO;
        for (Cart cart : myCart) {
            for (Promotions promotion : promoList) {
                Item item = cart.getItem();
                if (item.getItemName().equals(promotion.getItemOnPromo())) {
                    if (cart.getQty() >= promotion.getPromoQty()) {
                        BigDecimal itemPrice = item.getItemPrice();
                        BigDecimal promotionApplyTimes = BigDecimal.valueOf(cart.getQty() / promotion.getPromoQty());
                        BigDecimal qtyWithoutPromoLeft = BigDecimal.valueOf(cart.getQty() % promotion.getPromoQty());

                        BigDecimal priceWithoutDiscount = itemPrice.multiply(BigDecimal.valueOf(cart.getQty()));
                        BigDecimal priceWithDiscount = promotion.getPromoPrice().multiply(promotionApplyTimes)
                                .add(itemPrice.multiply(qtyWithoutPromoLeft));

                        promotionValue = promotionValue.add(priceWithoutDiscount.subtract(priceWithDiscount));
                    }
                }
            }
        }
        return promotionValue;
    }
}