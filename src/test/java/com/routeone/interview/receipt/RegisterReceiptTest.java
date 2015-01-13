package com.routeone.interview.receipt;

import com.routeone.interview.Receipt;
import com.routeone.interview.StoreRegister;
import com.routeone.interview.inventory.dataModel.InventoryItem;
import com.routeone.interview.inventory.exception.UnknownInventoryItemException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Brian
 */
public class RegisterReceiptTest {

    private StoreRegister storeRegister = null;
    private static final Logger LOG = LogManager.getLogger(RegisterReceiptTest.class);

    @Before
    public void initialize() {
        storeRegister = new StoreRegister();
    }

    @Test
    public void testFormattedTotal() {
        ArrayList<String> orderedItems = new ArrayList<String>();
        double total = 0.0;
        while (total < 1000000.0) {
            for (InventoryItem item : storeRegister.getInventory().getInventoryItems().values()) {
                orderedItems.add(item.getComponentName());
                total += item.getPrice();
                if (total > 1000000.0) {
                    break;
                }
            }
        }
        Receipt receipt = storeRegister.checkoutOrder(orderedItems);
        DecimalFormat df = new DecimalFormat("#,###.##");
        Assert.assertEquals("Expected a different formatted total than was evaluated", "$" + df.format(total), receipt.getFormattedTotal());
    }

    @Test
    public void testCanCheckoutDuplicateItems() {
        ArrayList<String> orderedItems = new ArrayList<String>();
        //add every item twice
        orderedItems.addAll(storeRegister.getInventory().getInventoryItems().keySet());
        orderedItems.addAll(storeRegister.getInventory().getInventoryItems().keySet());
        Receipt receipt = storeRegister.checkoutOrder(orderedItems);
        //ensure that the correct number of items exist in the list
        Assert.assertTrue("Failed to checkout duplicates of the same item", receipt.getOrderedItems().size() == (storeRegister.getInventory().getInventoryItems().keySet().size() * 2));
    }

    @Test
    public void testProperSortingOfOrderedItems() {
        ArrayList<String> orderedItems = new ArrayList<String>();
        //add every item twice
        orderedItems.addAll(storeRegister.getInventory().getInventoryItems().keySet());
        orderedItems.addAll(storeRegister.getInventory().getInventoryItems().keySet());
        Receipt receipt = storeRegister.checkoutOrder(orderedItems);
        boolean isProperOrder = true;
        List<String> sortedOrderedItems = receipt.getOrderedItems();
        InventoryItem previousItem = null;
        for (int i = 0; i < sortedOrderedItems.size(); i++) {
            try {
                InventoryItem currentItem = storeRegister.getInventory().getInventoryItem(sortedOrderedItems.get(i));
                if (previousItem == null) {
                    previousItem = currentItem;
                    continue;
                } else {
                    if (previousItem.getPrice() < currentItem.getPrice()) {
                        isProperOrder = false;
                        break;
                    } else if (previousItem.getPrice() == currentItem.getPrice()) {
                        if (previousItem.getComponentName().compareTo(currentItem.getComponentName()) == 1) {
                            isProperOrder = false;
                            break;
                        }
                    }
                    previousItem = currentItem;
                }
            } catch (UnknownInventoryItemException e) {
                Assert.fail("Unable to find an inventory item referenced in receipt");
            }
        }
        Assert.assertTrue("Method getOrderedItems does not appear to be in the proper order", isProperOrder);
    }
}