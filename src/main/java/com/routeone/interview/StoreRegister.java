package com.routeone.interview;

import com.routeone.interview.inventory.dataModel.Inventory;
import com.routeone.interview.inventory.dataModel.InventoryItem;
import com.routeone.interview.inventory.exception.UnknownInventoryItemException;
import com.routeone.interview.receipt.RegisterReceipt;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class StoreRegister {

    private static final int FIELD_INDEX_COMPONENT_NAME = 0;
    private static final int FIELD_INDEX_PRICE = 1;
    private static final int FIELD_INDEX_CATEGORY = 2;
    private static final String RESOURCE_NAME_INVENTORY_CSV = "com/routeone/interview/sample-inventory.csv";
    private Inventory inventory = null;
    private static final Logger LOG = LogManager.getLogger(StoreRegister.class);

    public StoreRegister() {
        URL inventoryUrl = this.getClass().getClassLoader().getResource(RESOURCE_NAME_INVENTORY_CSV);
        loadInventory(new File(inventoryUrl.getPath()));
    }

    /**
     * Parses a CSV file and uses its contents to instantiate an Inventory full
     * of InventoryItems
     *
     * @param inventoryFile
     */
    public void loadInventory(File inventoryFile) {
        if (inventoryFile != null) {
            try (BufferedReader br = new BufferedReader(new FileReader(inventoryFile))) {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] inventoryFields = line.split(",");
                    String componentName = inventoryFields[FIELD_INDEX_COMPONENT_NAME].trim();
                    String price = inventoryFields[FIELD_INDEX_PRICE].trim();
                    String category = inventoryFields[FIELD_INDEX_CATEGORY].trim();
                    this.getInventory().addInventoryItem(new InventoryItem(componentName, Double.parseDouble(price), category));
                }
            } catch (Exception ex) {
                LOG.error("Error reading inventory file", ex);
            }
        }
    }

    /**
     * Fetches all inventory items by name and uses that list of inventory items
     * to instantiate a RegisterReceipt
     *
     * @param items
     * @return
     */
    public Receipt checkoutOrder(List<String> items) {
        RegisterReceipt receipt = null;
        if (items != null && !items.isEmpty()) {
            try {
                ArrayList<InventoryItem> purchasedInventoryItems = new ArrayList<InventoryItem>();
                for (String itemName : items) {
                    purchasedInventoryItems.add(this.getInventory().getInventoryItem(itemName));
                }
                receipt = new RegisterReceipt(purchasedInventoryItems);
            } catch (UnknownInventoryItemException ex) {
                LOG.error("Unable to find matching inventory item", ex);
            }
        }
        return receipt;
    }

    /**
     * Gets the inventory, if no inventory exists, a new one is instantiated
     *
     * @return the inventory
     */
    public Inventory getInventory() {
        if (inventory == null) {
            this.setInventory(new Inventory());
        }
        return inventory;
    }

    /**
     * @param inventory the inventory to set
     */
    private void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }
}