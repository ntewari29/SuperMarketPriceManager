class Cart {
    private final Inventory item;
    private final int qty;

    Cart(Inventory item, Integer qty) {
        this.item = item;
        this.qty = qty;
    }

    Inventory getItem() {
        return item;
    }

    int getQty() {
        return qty;
    }
}