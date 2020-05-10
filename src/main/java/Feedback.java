import javax.swing.*;

public interface Feedback {
    static void report(int qty, Item itemName) {
        try {
            String infoMessage = "Quantity " + qty + " added to the cart for " + itemName.getItemName() + " is greater than the available Store inventory of " + itemName.getStoreQty();
            JOptionPane.showMessageDialog(null, infoMessage, "Error Response: " + "Invalid Order Qty", JOptionPane.ERROR_MESSAGE);
        } catch (NullPointerException ne) {
            System.out.println(ne);
        }
    }

    static void report(Item item) {
        try {
            String infoMessage = "Invalid order attributes for item " + item.getItemName() + " Qty " + item.getStoreQty() + " Price "+ item.getItemPrice() + " Serial Number " + item.getSerialNumber();
            JOptionPane.showMessageDialog(null, infoMessage, "Error Response: " + "Invalid Order Attributes", JOptionPane.ERROR_MESSAGE);
        } catch (NullPointerException ne) {
            System.out.println(ne);
        }
    }
}