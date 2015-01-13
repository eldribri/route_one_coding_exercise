package com.routeone.interview;

import com.routeone.interview.inventory.dataModel.InventoryItem;
import java.util.ArrayList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Brian
 */
public class StoreRegisterTest {

    private StoreRegister storeRegister = null;
    private static final Logger LOG = LogManager.getLogger(StoreRegisterTest.class);

    @Before
    public void initialize() {
        storeRegister = new StoreRegister();
    }

    @Test
    public void testNonNullOrEmptyInventoryAfterLoadInventory() {
        Assert.assertNotNull("Invetory should not be null after loading a non null inventory CSV file", storeRegister.getInventory());
        Assert.assertFalse("Inventory should not be empty after loading a non null inventory CSV file", storeRegister.getInventory().getInventoryItems().isEmpty());
    }

    @Test
    public void testNullReceiptDuringCheckoutOfUnknownItem() {
        ArrayList<String> checkedOutItems = new ArrayList<String>();
        checkedOutItems.add("INVALID ITEM");
        Receipt receipt = storeRegister.checkoutOrder(checkedOutItems);
        Assert.assertNull("Receipt should be null when checking out an invalid item", receipt);
    }

    @Test
    public void testNonNullReceiptForValidCheckout() {
        ArrayList<String> checkedOutItems = new ArrayList<String>();
        for (InventoryItem item : storeRegister.getInventory().getInventoryItems().values()) {
            checkedOutItems.add(item.getComponentName());
            break;
        }
        Receipt receipt = storeRegister.checkoutOrder(checkedOutItems);
        Assert.assertNotNull("Receipt should not be null for a valid checkout", receipt);
    }
}
