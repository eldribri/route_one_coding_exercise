package com.routeone.interview.inventory.dataModel;

import com.routeone.interview.inventory.exception.DuplicateInventoryItemException;
import com.routeone.interview.inventory.exception.InvalidInventoryItemException;
import com.routeone.interview.inventory.exception.UnknownInventoryItemException;
import java.util.HashMap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Brian
 */
public class Inventory {

    private HashMap<String, InventoryItem> inventoryItems = null;
    private static final Logger LOG = LogManager.getLogger(Inventory.class);

    /**
     * Fetches an inventory item from the inventory with the specified name. If
     * the item cannot be found an UnknownInventoryItemException is thrown.
     *
     * @param componentName
     * @return
     * @throws UnknownInventoryItemException
     */
    public InventoryItem getInventoryItem(String componentName) throws UnknownInventoryItemException {
        InventoryItem item = null;
        if (componentName == null || componentName.isEmpty()) {
            throw new UnknownInventoryItemException("Component name must be specified");
        } else {
            if (this.getInventoryItems().containsKey(componentName)) {
                item = this.getInventoryItems().get(componentName);
            } else {
                throw new UnknownInventoryItemException("No item exists in inventory with name '" + componentName + "'");
            }
        }
        return item;
    }

    /**
     * Adds an inventoryItem to the inventory. If an item with the same name
     * already exists a DuplicateInventoryItemException is thrown. If the item
     * to add is null or the item's name is null or empty, an
     * InvalidInventoryItemException is thrown.
     *
     * @param item
     * @throws InvalidInventoryItemException
     * @throws DuplicateInventoryItemException
     */
    public void addInventoryItem(InventoryItem item) throws InvalidInventoryItemException, DuplicateInventoryItemException {
        if (item != null) {
            if (item.getComponentName() != null && !item.getComponentName().isEmpty()) {
                if (!this.getInventoryItems().containsKey(item.getComponentName())) {
                    this.getInventoryItems().put(item.getComponentName(), item);
                } else {
                    throw new DuplicateInventoryItemException("An inventory item with name '" + item.getComponentName() + "' already exists");
                }
            } else {
                throw new InvalidInventoryItemException("An inventory item with a null or empty component name cannot be added to the inventory");
            }
        } else {
            throw new InvalidInventoryItemException("A null inventory item cannot be added to the inventory");
        }
    }

    /**
     * @return the inventoryItems
     */
    public HashMap<String, InventoryItem> getInventoryItems() {
        if (inventoryItems == null) {
            this.setInventoryItems(new HashMap<String, InventoryItem>());
        }
        return inventoryItems;
    }

    /**
     * @param inventoryItems the inventoryItems to set
     */
    private void setInventoryItems(HashMap<String, InventoryItem> inventoryItems) {
        this.inventoryItems = inventoryItems;
    }
}
