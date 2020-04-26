public interface Feedback {
    static void report(int qty, Inventory itemName) {
        try {
            System.out.println("Quantity added to cart " + qty + " for " + itemName.getItemName() + " is greater than available Store quantity " + itemName.getStoreQty());
        } catch (NullPointerException ne) {
            System.out.println(ne);
        }
    }
}