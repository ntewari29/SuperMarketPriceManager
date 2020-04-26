import lombok.val;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

class SuperMarket {
    public static final String paddedSpaces = "           ";
    private List<Cart> myCart = new ArrayList<>();
    private List<Inventory> inventoryList = new ArrayList<>();
    private List<Promotions> promoList = new ArrayList<>();
    BigDecimal priceWithoutDiscount = null;
    Inventory inventory;
    Cart cart;

    List<Inventory> initializeInventory() {
        return inventoryList;
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
    String tailer = "\t\tThanks for Visting! Have a Nice Day";
    BigDecimal promotionValue;

    void addItemToInventory(Inventory inventory) {
        String serialNumber = inventory.getSerialNumber();
        boolean hasSerialNo = (serialNumber != null && !serialNumber.equals(""));
        if (hasSerialNo
                && inventory.getItemPrice().compareTo(BigDecimal.ZERO) > 0
                && inventory.getStoreQty() > 0) {
            inventoryList.add(inventory);
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
            for (Inventory inventory : inventoryList) {
                if (cart.getItem().getItemName().equals(inventory.getItemName())) {
                    BigDecimal individualItemPrice = inventory.getItemPrice().multiply(BigDecimal.valueOf(cart.getQty()));
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
        if (inventoryList.isEmpty()) {
            sb.append(header + "\n\n");
            sb.append(receiptClosure + "\n");
            sb.append(grandTotal + "\n");
            sb.append(savings + "\n");
            sb.append(tailer);
            return sb.toString();
        } else {
            sb.append(header + "\n");
            for (Cart cart : myCart) {
                Inventory inventory = cart.getItem();
                val checkoutQty = cart.getQty();
                sb.append(inventory.getSerialNumber() + paddedSpaces);
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
                    }
                    else if (!promotions.getItemOnPromo().equals(cart.getItem().getItemName()) && !promotions.getPromoQty().equals(cart.getQty())) {
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

    boolean addItemsToTheCart(Inventory itemName, int qty) {
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

    List<Inventory> getInventoryList() {
        return inventoryList;
    }

    private BigDecimal calculatePromotion() {
        promotionValue = BigDecimal.ZERO;
        for (Cart cart : myCart) {
            for (Promotions promotion : promoList) {
                Inventory item = cart.getItem();
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