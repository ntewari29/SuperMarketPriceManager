import javax.swing.*;

public interface Feedback {
    static void report(int qty, Inventory itemName) {
        try {
            String infoMessage = "Quantity added to cart " + qty + " for " + itemName.getItemName() + " is greater than available Store quantity " + itemName.getStoreQty();
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