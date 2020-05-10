import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class WalmartTest {

    Walmart walmart = new Walmart();

    @Test
    public void emptyStore() {
        assertThat(render(walmart.allItems()), is(" Item                 Name Remaining Qty\n"));
    }

    @Test
    public void addItemToWalmartInventory() {
        Item pitabread = pitaBreadWIthName("pitaBread");
        walmart.addItem(pitabread);
        assertThat(render(walmart.allItems()), is(" Item                 Name Remaining Qty\n" +
                "  100            pitaBread          0"));
    }

    @Test
    public void invalidEntryRetrunsErrorAndIsNotAdded() {
        Item pitabread = pitaBreadWIthName("");
        String error = walmart.addItem(pitabread);
        assertThat(error, is("Item Name is Empty"));
        assertThat(render(walmart.allItems()), is(" Item                 Name Remaining Qty\n"));

    }

    private Item pitaBreadWIthName(String pitaBread) {
        return new Item("100", pitaBread, BigDecimal.valueOf(15), 0, false);
    }

    private String render(List<Item> allItems) {
        String result = format("Item", "Name", "Remaining Qty\n");
        result += allItems.stream()
                .map(i -> format(i.getSerialNumber(), i.getItemName(), Integer.toString(i.getStoreQty())))
                .collect(Collectors.joining("\n"));
        return result;
    }

    private String format(String item, String name, String remaining_qty) {
        return String.format("%5s %20s %10s", item, name, remaining_qty);
    }

    private class Walmart {

        private final ArrayList<Item> items = new ArrayList<>();

        public List<Item> allItems() {
            return items;
        }

        public String addItem(Item item) {
            if (!item.getItemName().isEmpty()) {
                items.add(item);
                return "Some meaningful text";
            } else
                return "Item Name is Empty";
        }
    }
}
