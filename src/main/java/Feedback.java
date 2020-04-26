import javax.swing.*;

public interface Feedback {
    static void report(int qty, Inventory itemName) {
        try {
            String infoMessage = "Quantity added to cart " + qty + " for " + itemName.getItemName() + " is greater than available Store quantity " + itemName.getStoreQty();
            JOptionPane.showMessageDialog(null, infoMessage, "InfoBox: " + "Error Response", JOptionPane.INFORMATION_MESSAGE);
        } catch (NullPointerException ne) {
            System.out.println(ne);
        }
    }
}