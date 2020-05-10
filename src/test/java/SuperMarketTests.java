import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;


public class SuperMarketTests {

    private SuperMarket superMarket;

    private Item milk = new Item("1", "Milk", BigDecimal.valueOf(5d), 200, true);
    private Item corona = new Item("2", "Corona", BigDecimal.valueOf(11.25), 300, true);
    private Item bread = new Item("3", "Bread", BigDecimal.valueOf(12d), 50, false);
    private Item whisky = new Item("4", "Whisky", BigDecimal.valueOf(526d), 70, false);

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
        Promotions milk = new Promotions("1", "Milk", BigDecimal.valueOf(15d), 4);
        Promotions corona = new Promotions("2", "Corona", BigDecimal.valueOf(30d), 3);
        superMarket.addPromo(milk);
        superMarket.addPromo(corona);
    }

    @Test
    public void checkTheStoreInventory() {
        List<Item> item = superMarket.initializeInventory();
        assertThat(item,is(empty()));
    }

    @Test
    public void checkThatItemsWithInvalidAttributesCannotBeAddedToTheCart() {
        Item garlic = new Item("", "Garlic", BigDecimal.valueOf(5.4), 1, false);
        Item moisturiser = new Item("2a", "Moisturiser", BigDecimal.valueOf(-50), 10, false);
        Item pitabread = new Item("100", "", BigDecimal.valueOf(15), 0, false);

        superMarket.addItemToInventory(garlic);
        superMarket.addItemToInventory(moisturiser);
        superMarket.addItemToInventory(pitabread);

        assertThat(superMarket.getItemList(), is(empty()));
    }

    @Test
    public void initiateStoreInventoryAndAssertOnTheItemName() {
        Item milk = new Item("1", "Milk", BigDecimal.valueOf(5), 1, false);
        List<Item> item = superMarket.initializeInventory();
        superMarket.addItemToInventory(milk);

        assertThat(item,is(Collections.singletonList(milk)));
    }

    @Test
    public void verifyThatItemsWithValidPriceCanBeAddedToTheInventory() {
        List<Item> item = superMarket.initializeInventory();

        assertThat(item,is(empty()));
        assertThat(item,is(empty()));
    }

    @Test
    public void addItemsToInventoryAndAssertAgainstThePrice() {
        Item milk = new Item("1", "Milk", BigDecimal.valueOf(5), 1, false);
        Item corona = new Item("2", "Corona", BigDecimal.valueOf(11.25), 1, false);
        Item whisky = new Item("3", "Ardbeg", BigDecimal.valueOf(526), 1, false);
        superMarket.addItemToInventory(milk);
        superMarket.addItemToInventory(corona);
        superMarket.addItemToInventory(whisky);

        assertThat(milk.getItemPrice(),is(BigDecimal.valueOf(5)));
    }

    @Test
    public void calculateTotalValueOfItemsAddedInTheCart() {
        initializeInventory();
        includePromotions();
        superMarket.addItemsToTheCart(milk, 1);
        superMarket.addItemsToTheCart(corona, 1);
        superMarket.addItemsToTheCart(whisky, 1);
        superMarket.addItemsToTheCart(bread, 1);
        assertThat(superMarket.totalValueOfCart(),is(BigDecimal.valueOf(554.25)));
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
    public void shouldPromotionValue() {
        initializeInventory();
        includePromotions();
        superMarket.addItemsToTheCart(milk, 4);
        superMarket.addItemsToTheCart(corona, 3);

        assertThat(superMarket.totalValueOfCart(), is(BigDecimal.valueOf(45.00).setScale(2)));
    }

    @Test
    public void shouldPromotionValueWhenQtyIsDoubled() {
        initializeInventory();
        includePromotions();
        superMarket.addItemsToTheCart(milk, 8);
        superMarket.addItemsToTheCart(corona, 6);

        assertThat(superMarket.totalValueOfCart(),is(BigDecimal.valueOf(90).setScale(2)));
    }

    @Test
    public void shouldPromotionValueWhenQtyIsLessThanNeeded() {
        initializeInventory();
        includePromotions();
        superMarket.addItemsToTheCart(milk, 7);
        superMarket.addItemsToTheCart(corona, 6);

        assertThat(superMarket.totalValueOfCart(),is(BigDecimal.valueOf(90).setScale(2)));
    }

    @Test
    public void blankReceiptWhenNoItemIsAddedToTheCart() {
        assertThat(superMarket.generateReceipt(superMarket.getMyCart())
                ,is(
                        "\t\tSuperMarket Pricing Manager\n" +
                                "\t\t\t\t\t\tDate: " + superMarket.todaysDate + "\n" +
                                "\n" +
                                "Item No.\tDescription\t\tQuantity\t\tTotal Price\n" +
                                "---------------------------------------------------------------\n" +
                                "\n" +
                                "---------------------------------------------------------------\n" +
                                "\t\t\t\t\tGrand Total: $\n" +
                                "\t\t\t\t\t  You Saved: $\n" +
                                "\t\tThanks for Visiting! Have a Nice Day"));
    }

    @Test
    public void addItemsToTheCartAndCheckoutToVerifyTheReceipt() {
        initializeInventory();
        superMarket.addItemsToTheCart(milk, 1);
        superMarket.addItemsToTheCart(corona, 1);
        superMarket.addItemsToTheCart(whisky, 1);
        superMarket.addItemsToTheCart(bread, 1);
        includePromotions();
        assertThat(superMarket.generateReceipt(superMarket.getMyCart())
                ,is(
                        "\t\tSuperMarket Pricing Manager\n" +
                                "\t\t\t\t\t\tDate: " + superMarket.todaysDate + "\n" +
                                "\n" +
                                "Item No.\tDescription\t\tQuantity\t\tTotal Price\n" +
                                "---------------------------------------------------------------\n" +
                                "1           Milk            1               5.0\n" +
                                "2           Corona          1               11.25\n" +
                                "4           Whisky          1               526.0\n" +
                                "3           Bread           1               12.0\n" +
                                "---------------------------------------------------------------\n" +
                                "\t\t\t\t\tGrand Total: $  554.25\n" +
                                "\t\t\t\t\t  You Saved: $  0\n" +
                                "\t\tThanks for Visiting! Have a Nice Day"));
    }

    @Test
    public void secondCustomerCheckoutsTheCartVerifyReceiptGenerationAfterCheckout() {
        initializeInventory();
        superMarket.addItemsToTheCart(milk, 4);
        superMarket.addItemsToTheCart(bread, 2);
        includePromotions();
        assertThat(superMarket.generateReceipt(superMarket.getMyCart())
                ,is(
                        "\t\tSuperMarket Pricing Manager\n" +
                                "\t\t\t\t\t\tDate: " + superMarket.todaysDate + "\n" +
                                "\n" +
                                "Item No.\tDescription\t\tQuantity\t\tTotal Price\n" +
                                "---------------------------------------------------------------\n" +
                                "1           Milk            4               15.0\n" +
                                "3           Bread           2               24.0\n" +
                                "---------------------------------------------------------------\n" +
                                "\t\t\t\t\tGrand Total: $  39.0\n" +
                                "\t\t\t\t\t  You Saved: $  5.0\n" +
                                "\t\tThanks for Visiting! Have a Nice Day"));
    }

    @Test
    public void customersCannotAddMoreQtyToTheCartThenTheAvailableInventory() {
        initializeInventory();
        assertThat(superMarket.addItemsToTheCart(milk, 300),is(false));
        assertThat(superMarket.addItemsToTheCart(milk, 200),is(true));
        assertThat(superMarket.addItemsToTheCart(bread, 51),is(false));
        assertThat(superMarket.addItemsToTheCart(corona, 100),is(true));
    }
}