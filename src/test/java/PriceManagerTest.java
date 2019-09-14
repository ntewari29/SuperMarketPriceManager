import org.junit.Test;

import java.util.List;

import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class PriceManagerTest {

    SuperMarket superMarket = new SuperMarket();

    Inventory milk = new Inventory("1", "Milk", 5.0, 1, true);
    Inventory corona = new Inventory("2", "Corona", 11.25, 3, true);
    Inventory bread = new Inventory("2", "Bread", 12d, 0, false);
    Inventory whisky = new Inventory("3", "Ardbeg", 526.0, 1, false);

    int receiptNo = 1;

    public void initializeInventory() {
        superMarket.addItem(milk);
        superMarket.addItem(corona);
        superMarket.addItem(bread);
        superMarket.addItem(whisky);
    }

    public void includePromotions() {
        Promotions milk = new Promotions("1", "Milk", 15.0, 4);
        Promotions corona = new Promotions("2", "Corona", 30.0, 3);
        superMarket.addPromo(milk);
        superMarket.addPromo(corona);
    }

    @Test
    public void checkTheStoreInventory() {
        List<Inventory> inventory = superMarket.initializeInventory();
        assertThat(inventory, is(empty()));
    }

    @Test
    public void checkThatItemsWithInvalidAttributesCannotBeAddedToTheCart() {
        Inventory garlic = new Inventory("","Garlic", 5.4, 1, false);
        Inventory moisturiser = new Inventory("2a","Moisturiser", -50.0, 10, false);
        Inventory pitabread = new Inventory("100","", 15d, 0, false);
        superMarket.addItem(garlic);
        superMarket.addItem(moisturiser);
        superMarket.addItem(pitabread);
        assertThat(superMarket.inventoryList, is(empty()));
    }

    @Test
    public void intiateStoreInventoryAndAssertOnTheItemName() {
        Inventory milk = new Inventory("1", "Milk", 5.0, 1, false);
        List<Inventory> inventory = superMarket.initializeInventory();
        superMarket.addItem(milk);
        assertThat(inventory, is(asList(milk)));
    }

    @Test
    public void verifyThatItemsWithValidPriceCanBeAddedToTheInventory() {
        List<Inventory> inventory = superMarket.initializeInventory();
        assertThat("Invalid Price of the order being added to the Inventory", inventory, is(empty()));
        assertThat("Invalid Qty of the order being added to the Inventory", inventory, is(empty()));
    }

    @Test
    public void addItemsToInventoryAndAssertAgainstThePrice() {
        Inventory milk = new Inventory("1", "Milk", 5.0, 1, false);
        Inventory corona = new Inventory("2", "Corona", 11.25, 1, false);
        Inventory whisky = new Inventory("3", "Ardbeg", 526.0, 1, false);
        superMarket.addItem(milk);
        superMarket.addItem(corona);
        superMarket.addItem(whisky);
        assertThat(milk.itemPrice, is(5.0));
    }

    @Test
    public void calculateTotalValueOfItemsAddedToTheInventory() {
        Inventory milk = new Inventory("1", "Milk", 5.0, 1, false);
        Inventory corona = new Inventory("2", "Corona", 11.25, 1, false);
        Inventory whisky = new Inventory("3", "Ardbeg", 526.0, 1, false);
        Inventory bread = new Inventory("4", "Bread", 12.0, 1, false);
        superMarket.addItem(milk);
        superMarket.addItem(corona);
        superMarket.addItem(whisky);
        superMarket.addItem(bread);
        assertThat(superMarket.totalValueOfCart(), is(554.25));
    }

    @Test
    public void totalNumberOfItemsInTheCart() {
        Inventory milk = new Inventory("1", "Milk", 5.0, 1, false);
        Inventory corona = new Inventory("2", "Corona", 11.25, 1, false);
        Inventory whisky = new Inventory("3", "Ardbeg", 526.0, 1, false);
        Inventory bread = new Inventory("4", "Bread", 12.0, 1, false);
        superMarket.addItem(milk);
        superMarket.addItem(corona);
        superMarket.addItem(whisky);
        superMarket.addItem(bread);
        assertThat(superMarket.itemsInTheCart(), is(4));
    }

    @Test
    public void verifyPromotionsOnTheItemsAddedToTheCart() {
        includePromotions();
        Inventory milk = new Inventory("1", "Milk", 5.0, 4, true);
        Inventory corona = new Inventory("2", "Corona", 11.25, 3, true);
        superMarket.addItem(milk);
        superMarket.addItem(corona);
        assertThat(superMarket.totalValueOfCart(), is(45d));
    }

    @Test
    public void blankReceiptWhenNoItemIsAddedToTheCart() {
        assertThat(superMarket.generateReceipt(),
                is("--------------------\n" +
                        "|                  |\n" +
                        "|                  |\n" +
                        "|                  |\n" +
                        "|                  |\n" +
                        "|                  |\n" +
                        "--------------------"));
    }

    @Test
    public void generateReceiptAfterCheckout() {
        includePromotions();
        superMarket.addItem(milk);
        superMarket.addItem(corona);
        assertThat(superMarket.generateReceipt(),
                is("--------------------\n" +
                        "|Receipt No.1      |\n" +
                        "|1. Milk   4 $5    |\n" +
                        "|2. Corona 3 $11.25|\n" +
                        "|   Promo Applied  |\n" +
                        "| Total: $ 45      |\n" +
                        "--------------------"));
    }
}