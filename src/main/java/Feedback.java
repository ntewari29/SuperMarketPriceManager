import javax.swing.*;

public interface Feedback {
    static void report(int qty, Inventory itemName) {
        try {
            String infoMessage = "Quantity " + qty + " added to the cart for " + itemName.getItemName() + " is greater than the available Store inventory " + itemName.getStoreQty();
            JOptionPane.showMessageDialog(null, infoMessage, "Error Response: " + "Invalid Order Qty", JOptionPane.ERROR_MESSAGE);
        } catch (NullPointerException ne) {
            System.out.println(ne);
        }
    }

    static void report(Inventory inventory) {
        try {
            String infoMessage = "Invalid order attributes for item " + inventory.getItemName() + " Qty " + inventory.getStoreQty() + " Price "+ inventory.getItemPrice() + " Serial Number " + inventory.getSerialNumber();
            JOptionPane.showMessageDialog(null, infoMessage, "Error Response: " + "Invalid Order Attributes", JOptionPane.ERROR_MESSAGE);
        } catch (NullPointerException ne) {
            System.out.println(ne);
        }
    }
}