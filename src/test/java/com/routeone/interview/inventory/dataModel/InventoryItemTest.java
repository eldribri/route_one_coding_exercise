package com.routeone.interview.inventory.dataModel;

import com.routeone.interview.inventory.exception.InvalidInventoryItemException;
import org.junit.Test;

/**
 *
 * @author Brian
 */
public class InventoryItemTest {

    @Test(expected = InvalidInventoryItemException.class)
    public void testInvalidInventoryItemExceptionWhenInstantiatingWithNegativePrice() throws InvalidInventoryItemException {
        InventoryItem item = new InventoryItem("test", -1.0, "test");
    }

    @Test(expected = InvalidInventoryItemException.class)
    public void testInvalidInventoryItemExceptionWhenInstantiatingWithNullComponentName() throws InvalidInventoryItemException {
        InventoryItem item = new InventoryItem(null, 1.0, "test");
    }

    @Test(expected = InvalidInventoryItemException.class)
    public void testInvalidInventoryItemExceptionWhenInstantiatingWithEmptyComponentName() throws InvalidInventoryItemException {
        InventoryItem item = new InventoryItem("", 1.0, "test");
    }

    @Test(expected = InvalidInventoryItemException.class)
    public void testInvalidInventoryItemExceptionWhenInstantiatingWithNullCategory() throws InvalidInventoryItemException {
        InventoryItem item = new InventoryItem("test", 1.0, null);
    }

    @Test(expected = InvalidInventoryItemException.class)
    public void testInvalidInventoryItemExceptionWhenInstantiatingWithEmptyCategory() throws InvalidInventoryItemException {
        InventoryItem item = new InventoryItem("test", 1.0, "");
    }
}