class Cart {
    private final Item item;
    private final int qty;

    Cart(Item item, Integer qty) {
        this.item = item;
        this.qty = qty;
    }

    Item getItem() {
        return item;
    }

    int getQty() {
        return qty;
    }
}