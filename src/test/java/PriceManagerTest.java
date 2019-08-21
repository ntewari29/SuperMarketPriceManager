import org.junit.Test;

import java.util.List;

import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class PriceManagerTest {
    @Test
    public void checkTheStoreInventory() {
        SuperMarket superMarket = new SuperMarket();
        List<Inventory> inventory = superMarket.initializeInventory();
        assertThat(inventory, is(empty()));
    }
}
