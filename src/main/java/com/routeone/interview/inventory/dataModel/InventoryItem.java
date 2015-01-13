package com.routeone.interview.inventory.dataModel;

import com.routeone.interview.inventory.exception.InvalidInventoryItemException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Brian
 */
public class InventoryItem {

    private String componentName = "";
    private double price = 0.0;
    private String category = "";
    private static final Logger LOG = LogManager.getLogger(InventoryItem.class);

    private InventoryItem() {
    }

    public InventoryItem(String componentName, double price, String category) throws InvalidInventoryItemException {
        if (componentName == null || componentName.isEmpty()) {
            throw new InvalidInventoryItemException("Component name must be supplied");
        }
        if (price < 0) {
            throw new InvalidInventoryItemException("Price cannot be less than zero");
        }
        if (category == null || category.isEmpty()) {
            throw new InvalidInventoryItemException("Category must be supplied");
        }
        this.componentName = componentName;
        this.price = price;
        this.category = category;
    }

    /**
     * @return the componentName
     */
    public String getComponentName() {
        return componentName;
    }

    /**
     * @param componentName the componentName to set
     */
    private void setComponentName(String componentName) {
        this.componentName = componentName;
    }

    /**
     * @return the category
     */
    public String getCategory() {
        return category;
    }

    /**
     * @param category the category to set
     */
    private void setCategory(String category) {
        this.category = category;
    }

    /**
     * @return the price
     */
    public double getPrice() {
        return price;
    }

    /**
     * @param price the price to set
     */
    private void setPrice(double price) {
        this.price = price;
    }
}
