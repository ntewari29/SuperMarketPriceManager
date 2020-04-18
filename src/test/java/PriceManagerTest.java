import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class PriceManagerTest {

    private SuperMarket superMarket;

    private Inventory milk = new Inventory("1", "Milk", BigDecimal.valueOf(5d), 1, true);
    private Inventory corona = new Inventory("2", "Corona", BigDecimal.valueOf(11.25), 3, true);
    private Inventory bread = new Inventory("3", "Bread", BigDecimal.valueOf(12d), 1, false);
    private Inventory whisky = new Inventory("4", "Whisky", BigDecimal.valueOf(526d), 1, false);

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
        List<Inventory> inventory = superMarket.initializeInventory();
        assertThat(inventory).isEmpty();
    }

    @Test
    public void checkThatItemsWithInvalidAttributesCannotBeAddedToTheCart() {
        Inventory garlic = new Inventory("", "Garlic", BigDecimal.valueOf(5.4), 1, false);
        Inventory moisturiser = new Inventory("2a", "Moisturiser", BigDecimal.valueOf(-50), 10, false);
        Inventory pitabread = new Inventory("100", "", BigDecimal.valueOf(15), 0, false);

        superMarket.addItemToInventory(garlic);
        superMarket.addItemToInventory(moisturiser);
        superMarket.addItemToInventory(pitabread);

        assertThat(superMarket.getInventoryList()).isEmpty();
    }

    @Test
    public void initiateStoreInventoryAndAssertOnTheItemName() {
        Inventory milk = new Inventory("1", "Milk", BigDecimal.valueOf(5), 1, false);
        List<Inventory> inventory = superMarket.initializeInventory();
        superMarket.addItemToInventory(milk);

        assertThat(inventory).isEqualTo(Collections.singletonList(milk));
    }

    @Test
    public void verifyThatItemsWithValidPriceCanBeAddedToTheInventory() {
        List<Inventory> inventory = superMarket.initializeInventory();

        assertThat(inventory).as("Invalid Price of the order being added to the Inventory").isEmpty();
        assertThat(inventory).as("Invalid Qty of the order being added to the Inventory").isEmpty();
    }

    @Test
    public void addItemsToInventoryAndAssertAgainstThePrice() {
        Inventory milk = new Inventory("1", "Milk", BigDecimal.valueOf(5), 1, false);
        Inventory corona = new Inventory("2", "Corona", BigDecimal.valueOf(11.25), 1, false);
        Inventory whisky = new Inventory("3", "Ardbeg", BigDecimal.valueOf(526), 1, false);
        superMarket.addItemToInventory(milk);
        superMarket.addItemToInventory(corona);
        superMarket.addItemToInventory(whisky);

        assertThat(milk.getItemPrice()).isEqualTo(BigDecimal.valueOf(5));
    }

    @Test
    public void calculateTotalValueOfItemsAddedInTheCart() {
        initializeInventory();
        includePromotions();
        superMarket.addItemsToTheCart(milk, 1);
        superMarket.addItemsToTheCart(corona, 1);
        superMarket.addItemsToTheCart(whisky, 1);
        superMarket.addItemsToTheCart(bread, 1);
        assertThat(superMarket.totalValueOfCart()).isEqualTo(BigDecimal.valueOf(554.25));
    }

    @Test
    public void totalNumberOfItemsInTheCart() {
        initializeInventory();
        superMarket.addItemsToTheCart(milk, 1);
        superMarket.addItemsToTheCart(corona, 1);
        superMarket.addItemsToTheCart(whisky, 1);
        superMarket.addItemsToTheCart(bread, 1);

        assertThat(superMarket.itemsInTheCart()).isEqualTo(4);
    }

    @Test
    public void shouldPromotionValue() {
        initializeInventory();
        includePromotions();
        superMarket.addItemsToTheCart(milk, 4);
        superMarket.addItemsToTheCart(corona, 3);

        assertThat(superMarket.totalValueOfCart()).isEqualByComparingTo(BigDecimal.valueOf(45));
    }

    @Test
    public void shouldPromotionValueWhenQtyIsDoubled() {
        initializeInventory();
        includePromotions();
        superMarket.addItemsToTheCart(milk, 8);
        superMarket.addItemsToTheCart(corona, 6);

        assertThat(superMarket.totalValueOfCart()).isEqualByComparingTo(BigDecimal.valueOf(90));
    }

    @Test
    public void shouldPromotionValueWhenQtyIsLessThanNeeded() {
        initializeInventory();
        includePromotions();
        superMarket.addItemsToTheCart(milk, 7);
        superMarket.addItemsToTheCart(corona, 6);

        assertThat(superMarket.totalValueOfCart()).isEqualByComparingTo(BigDecimal.valueOf(90));
    }

    @Test
    public void blankReceiptWhenNoItemIsAddedToTheCart() {
        assertThat(superMarket.generateReceipt(superMarket.getMyCart()))
                .isEqualTo(
                        "\t\tSuperMarket Pricing Manager\n" +
                                "\t\t\t\t\t\tDate: " + superMarket.todaysDate + "\n" +
                                "\n" +
                                "Item No.\tDescription\t\tQuantity\t\tTotal Price\n" +
                                "---------------------------------------------------------------\n" +
                                "\n" +
                                "---------------------------------------------------------------\n" +
                                "\t\t\t\t\tGrand Total: $\n" +
                                "\t\t\t\t\t  You Saved: $\n" +
                                "\t\tThanks for Visting! Have a Nice Day");
    }

    @Test
    public void addItemsToTheCartAndCheckoutToVerifyTheReceipt() {
        initializeInventory();
        superMarket.addItemsToTheCart(milk, 1);
        superMarket.addItemsToTheCart(corona, 1);
        superMarket.addItemsToTheCart(whisky, 1);
        superMarket.addItemsToTheCart(bread, 1);
        includePromotions();
        assertThat(superMarket.generateReceipt(superMarket.getMyCart()))
                .isEqualTo(
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
                                "\t\tThanks for Visting! Have a Nice Day");
    }

    @Test
    public void secondCustomerCheckoutsTheCartVerifyReceiptGenerationAfterCheckout() {
        initializeInventory();
        superMarket.addItemsToTheCart(milk, 4);
        superMarket.addItemsToTheCart(bread, 2);
        includePromotions();
        assertThat(superMarket.generateReceipt(superMarket.getMyCart()))
                .isEqualTo(
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
                                "\t\tThanks for Visting! Have a Nice Day");
    }
}