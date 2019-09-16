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

    public void initializeInventory() {
        superMarket.addItemToInventory(milk);
        superMarket.addItemToInventory(corona);
        superMarket.addItemToInventory(bread);
        superMarket.addItemToInventory(whisky);
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
        superMarket.addItemToInventory(garlic);
        superMarket.addItemToInventory(moisturiser);
        superMarket.addItemToInventory(pitabread);
        assertThat(superMarket.inventoryList, is(empty()));
    }

    @Test
    public void intiateStoreInventoryAndAssertOnTheItemName() {
        Inventory milk = new Inventory("1", "Milk", 5.0, 1, false);
        List<Inventory> inventory = superMarket.initializeInventory();
        superMarket.addItemToInventory(milk);
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
        superMarket.addItemToInventory(milk);
        superMarket.addItemToInventory(corona);
        superMarket.addItemToInventory(whisky);
        assertThat(milk.itemPrice, is(5.0));
    }

    @Test
    public void calculateTotalValueOfItemsAddedToTheInventory() {
        initializeInventory();
        superMarket.addItemsToTheCart(milk, 1);
        superMarket.addItemsToTheCart(corona, 1);
        superMarket.addItemsToTheCart(whisky, 1);
        superMarket.addItemsToTheCart(bread, 1);
        assertThat(superMarket.totalValueOfCart(), is(554.25));
    }

    @Test
    public void totalNumberOfItemsInTheCart() {
        initializeInventory();
        superMarket.addItemsToTheCart(milk, 1);
        superMarket.addItemsToTheCart(corona, 1);
        superMarket.addItemsToTheCart(whisky, 1);
        superMarket.addItemsToTheCart(bread, 1);
        assertThat(superMarket.itemsInTheCart(), is(4));
    }

    @Test
    public void verifyPromotionsOnTheItemsAddedToTheCart() {
        includePromotions();
        superMarket.addItemsToTheCart(milk, 4);
        superMarket.addItemsToTheCart(corona, 3);
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
        superMarket.addItemsToTheCart(milk, 4);
        superMarket.addItemsToTheCart(corona, 3);
        assertThat(superMarket.generateReceipt(),
                is("--------------------\n" +
                        "|Receipt No.1      |\n" +
                        "|1. Milk   4 $5    |\n" +
                        "|2. Corona 3 $11.25|\n" +
                        "|   Promo Applied  |\n" +
                        "| Total: $ 45      |\n" +
                        "--------------------"));
    }

    @Test
    public void generateMultipleReceiptsAfterCheckout() {
        includePromotions();
        superMarket.addItemsToTheCart(whisky, 2);
        superMarket.addItemsToTheCart(bread, 1);
        assertThat(superMarket.generateReceipt(),
                is("")); //Add receipts
    }
}