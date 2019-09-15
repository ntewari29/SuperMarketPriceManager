import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class PriceManagerTest {

    private SuperMarket superMarket;

    @Before
    public void setUp() {
        superMarket = new SuperMarket();
    }

    private void includePromotions() {
        Promotions milk = new Promotions("1", "Milk", BigDecimal.valueOf(15), 4);
        Promotions corona = new Promotions("2", "Corona", BigDecimal.valueOf(30), 3);
        superMarket.addPromo(milk);
        superMarket.addPromo(corona);
    }

    @Test
    public void checkTheStoreInventory() {
        List<Inventory> inventory = superMarket.initializeInventory();
        assertThat(inventory, is(empty()));
    }

    @Test
    public void initiateStoreInventoryAndAssertOnTheItemName() {
        Inventory milk = new Inventory("1", "Milk", BigDecimal.valueOf(5), 1, false);
        List<Inventory> inventory = superMarket.initializeInventory();
        superMarket.addItem(milk);
        assertThat(inventory, is(Collections.singletonList(milk)));
    }

    @Test
    public void verifyThatItemsWithValidPriceCanBeAddedToTheInventory() {
        List<Inventory> inventory = superMarket.initializeInventory();
        assertThat("Invalid Price of the order being added to the Inventory", inventory, is(empty()));
        assertThat("Invalid Qty of the order being added to the Inventory", inventory, is(empty()));
    }

    @Test
    public void addItemsToInventoryAndAssertAgainstThePrice() {
        Inventory milk = new Inventory("1", "Milk", BigDecimal.valueOf(5), 1, false);
        Inventory corona = new Inventory("2", "Corona", BigDecimal.valueOf(11.25), 1, false);
        Inventory whisky = new Inventory("3", "Ardbeg", BigDecimal.valueOf(526), 1, false);
        superMarket.addItem(milk);
        superMarket.addItem(corona);
        superMarket.addItem(whisky);

        assertEquals(BigDecimal.valueOf(5), milk.itemPrice);
    }

    @Test
    public void calculateTotalValueOfItemsAddedToTheInventory() {
        Inventory milk = new Inventory("1", "Milk", BigDecimal.valueOf(5), 1, false);
        Inventory corona = new Inventory("2", "Corona", BigDecimal.valueOf(11.25), 1, false);
        Inventory whisky = new Inventory("3", "Ardbeg", BigDecimal.valueOf(526), 1, false);
        Inventory bread = new Inventory("4", "Bread", BigDecimal.valueOf(12), 1, false);
        superMarket.addItem(milk);
        superMarket.addItem(corona);
        superMarket.addItem(whisky);
        superMarket.addItem(bread);

        assertEquals(0, superMarket.totalValueOfCart().compareTo(BigDecimal.valueOf(554.25)));
    }

    @Test
    public void totalNumberOfItemsInTheCart() {
        Inventory milk = new Inventory("1", "Milk", BigDecimal.valueOf(5), 1, false);
        Inventory corona = new Inventory("2", "Corona", BigDecimal.valueOf(11.25), 1, false);
        Inventory whisky = new Inventory("3", "Ardbeg", BigDecimal.valueOf(526), 1, false);
        Inventory bread = new Inventory("4", "Bread", BigDecimal.valueOf(12), 1, false);
        superMarket.addItem(milk);
        superMarket.addItem(corona);
        superMarket.addItem(whisky);
        superMarket.addItem(bread);

        assertThat(superMarket.itemsInTheCart(), is(4));
    }

    @Test
    public void verifyPromotionsOnTheItemsAddedToTheCart() {
        includePromotions();
        Inventory milk = new Inventory("1", "Milk", BigDecimal.valueOf(5), 1, false);
        Inventory corona = new Inventory("2", "Corona", BigDecimal.valueOf(11.25), 1, false);
        superMarket.addItem(milk);
        superMarket.addItem(corona);

        assertEquals(0, superMarket.totalValueOfCart().compareTo(BigDecimal.valueOf(45)));
    }

    @Test
    public void generateReceiptAfterCheckout() {
        includePromotions();
        Inventory milk = new Inventory("1", "Milk", BigDecimal.valueOf(5), 1, false);
        Inventory corona = new Inventory("2", "Corona", BigDecimal.valueOf(11.25), 1, false);
        superMarket.addItem(milk);
        superMarket.addItem(corona);
        assertThat(superMarket.generateReceipt(), is("--------------------\n" +
                "|Receipt No.1      |\n" +
                "|1. Milk   4 $5    |\n" +
                "|2. Corona 3 $11.25|\n" +
                "|   Promo Applied  |\n" +
                "| Total: $ 45      |\n" +
                "--------------------"));
    }
}