import org.junit.Test;

import java.util.List;

import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class PriceManagerTest {

    SuperMarket superMarket = new SuperMarket();

    @Test
    public void checkTheStoreInventory() {
        List<Inventory> inventory = superMarket.initializeInventory();
        assertThat(inventory, is(empty()));
    }

    @Test
    public void intiateStoreInventoryAndAssertOnTheItemName() {
        Inventory milk = new Inventory("1","Milk",5.0, 1);
        List<Inventory> inventory = superMarket.initializeInventory();
        superMarket.addItem(milk);
        assertThat(inventory, is(asList(milk)));
    }

    @Test
    public void verifyThatItemsWithValidPriceCanBeAddedToTheInventory() {
        List<Inventory> inventory = superMarket.initializeInventory();
        Inventory milk = new Inventory("1","Milk",-5.0, 1);
        superMarket.addItem(milk);
        assertThat("Invalid Price of the order being added to the Inventory", inventory, is(empty()));
        Inventory bread = new Inventory("2","Bread",12d, 0);
        superMarket.addItem(bread);
        assertThat("Invalid Qty of the order being added to the Inventory", inventory, is(empty()));
    }

    @Test
    public void addItemsToInventoryAndAssertAgainstThePrice() {
        Inventory milk = new Inventory("1","Milk",5.0, 1);
        Inventory corona = new Inventory("2","Corona",11.25, 1);
        Inventory whisky = new Inventory("3","Ardbeg",526.0, 1);
        superMarket.addItem(milk);
        superMarket.addItem(corona);
        superMarket.addItem(whisky);
        assertThat(milk.itemPrice, is(5.0));
    }

    @Test
    public void calculateTotalValueOfItemsAddedToTheInventory() {
        Inventory milk = new Inventory("1","Milk",5.0, 1);
        Inventory corona = new Inventory("2","Corona",11.25, 1);
        Inventory whisky = new Inventory("3","Ardbeg",526.0, 1);
        Inventory bread = new Inventory("4","Bread",12.0, 1);
        superMarket.addItem(milk);
        superMarket.addItem(corona);
        superMarket.addItem(whisky);
        superMarket.addItem(bread);
        assertThat(superMarket.totalValueOfCart(), is(554.25));
    }

    @Test
    public void totalNumberOfItemsInTheCart() {
        Inventory milk = new Inventory("1","Milk",5.0, 1);
        Inventory corona = new Inventory("2","Corona",11.25, 1);
        Inventory whisky = new Inventory("3","Ardbeg",526.0, 1);
        Inventory bread = new Inventory("4","Bread",12.0, 1);
        superMarket.addItem(milk);
        superMarket.addItem(corona);
        superMarket.addItem(whisky);
        superMarket.addItem(bread);
        assertThat(superMarket.itemsInTheCart(), is(4));
    }

    @Test
    public void addDiscountedItemToTheCart() {
        Inventory milk = new Inventory("1","Milk",5.0, 4);
        Inventory corona = new Inventory("2","Corona",11.25, 3);
        superMarket.addItem(milk);
        superMarket.addItem(corona);
        assertThat(superMarket.totalValueOfCart(), is(45d));
    }
}
