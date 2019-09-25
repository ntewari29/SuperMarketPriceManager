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

    private Inventory milk = new Inventory("1", "Milk",  BigDecimal.valueOf(5), 1, true);
    private Inventory corona = new Inventory("2", "Corona",  BigDecimal.valueOf(11.25), 3, true);
    private Inventory bread = new Inventory("3", "Bread",  BigDecimal.valueOf(12d), 1, false);
    private Inventory whisky = new Inventory("4", "Whisky",  BigDecimal.valueOf(526), 1, false);

    @Before
    public void setUp() {
        superMarket = new SuperMarket();
    }

    private void initializeInventory() {
        superMarket.addItemToInventory(milk);
        superMarket.addItemToInventory(corona);
        superMarket.addItemToInventory(bread);
        superMarket.addItemToInventory(whisky);
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
    public void checkThatItemsWithInvalidAttributesCannotBeAddedToTheCart() {
        Inventory garlic = new Inventory("", "Garlic", BigDecimal.valueOf(5.4), 1, false);
        Inventory moisturiser = new Inventory("2a", "Moisturiser", BigDecimal.valueOf(-50), 10, false);
        Inventory pitabread = new Inventory("100", "", BigDecimal.valueOf(15), 0, false);
        superMarket.addItemToInventory(garlic);
        superMarket.addItemToInventory(moisturiser);
        superMarket.addItemToInventory(pitabread);
        assertThat(superMarket.getInventoryList(), is(empty()));
    }

    @Test
    public void initiateStoreInventoryAndAssertOnTheItemName() {
        Inventory milk = new Inventory("1", "Milk", BigDecimal.valueOf(5), 1, false);
        List<Inventory> inventory = superMarket.initializeInventory();
        superMarket.addItemToInventory(milk);
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
        superMarket.addItemToInventory(milk);
        superMarket.addItemToInventory(corona);
        superMarket.addItemToInventory(whisky);

        assertEquals(BigDecimal.valueOf(5), milk.itemPrice);
    }

    @Test
    public void calculateTotalValueOfItemsAddedInTheCart() {
        initializeInventory();
        includePromotions();
        superMarket.addItemsToTheCart(milk, 1);
        superMarket.addItemsToTheCart(corona, 1);
        superMarket.addItemsToTheCart(whisky, 1);
        superMarket.addItemsToTheCart(bread, 1);
        assertThat(superMarket.totalValueOfCart(), is(554.25));
    }

    @Test
    public void totalNumberOfItemsInTheCart() {
        initializeInventory();
        superMarket.addItemsToTheCart(milk,1);
        superMarket.addItemsToTheCart(corona, 1);
        superMarket.addItemsToTheCart(whisky, 1);
        superMarket.addItemsToTheCart(bread, 1);
        assertThat(superMarket.itemsInTheCart(), is(4));
    }

    /*@Test
    public void verifyPromotionsOnTheItemsAddedToTheCart() {
        initializeInventory();
        includePromotions();
        Cart milk = new Cart("Milk", 4);
        Cart corona = new Cart("Corona", 3);
        superMarket.addItemsToTheCart(milk);
        superMarket.addItemsToTheCart(corona);
        assertThat(superMarket.totalValueOfCart(), is(45d));
    }*/
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
}