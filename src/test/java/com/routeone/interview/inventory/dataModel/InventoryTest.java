package com.routeone.interview.inventory.dataModel;

import com.routeone.interview.StoreRegister;
import com.routeone.interview.inventory.exception.DuplicateInventoryItemException;
import com.routeone.interview.inventory.exception.InvalidInventoryItemException;
import com.routeone.interview.inventory.exception.UnknownInventoryItemException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Brian
 */
public class InventoryTest {

    private StoreRegister storeRegister = null;

    @Before
    public void initialize() {
        storeRegister = new StoreRegister();
    }

    @Test(expected = UnknownInventoryItemException.class)
    public void testUknownInventorItemExceptionForGetInventoryItemWithInvalidComponentName() throws UnknownInventoryItemException {
        storeRegister.getInventory().getInventoryItem("INVALID ITEM");
    }

    @Test(expected = UnknownInventoryItemException.class)
    public void testUknownInventorItemExceptionForGetInventoryItemWithNullComponentName() throws UnknownInventoryItemException {
        storeRegister.getInventory().getInventoryItem(null);
    }

    @Test(expected = UnknownInventoryItemException.class)
    public void testUknownInventorItemExceptionForGetInventoryItemWithEmptyComponentName() throws UnknownInventoryItemException {
        storeRegister.getInventory().getInventoryItem("");
    }

    @Test
    public void testNonNullInventoryItemForGetInventoryITemWithValidComponentName() throws UnknownInventoryItemException {
        for (InventoryItem item : storeRegister.getInventory().getInventoryItems().values()) {
            InventoryItem returnedItem = storeRegister.getInventory().getInventoryItem(item.getComponentName());
            Assert.assertNotNull("getInventoryItem should return non null Inventory Item with a valid component name", returnedItem);
            break;
        }
    }

    @Test(expected = InvalidInventoryItemException.class)
    public void testInvalidInventoryItemExceptionWhenAddingNullInventoryItem() throws InvalidInventoryItemException, DuplicateInventoryItemException {
        storeRegister.getInventory().addInventoryItem(null);
    }

    @Test(expected = DuplicateInventoryItemException.class)
    public void testDuplicateInventoryItemExceptionWhenAddingExistingInventoryItem() throws InvalidInventoryItemException, DuplicateInventoryItemException {
        for (InventoryItem item : storeRegister.getInventory().getInventoryItems().values()) {
            storeRegister.getInventory().addInventoryItem(item);
            break;
        }
    }
}