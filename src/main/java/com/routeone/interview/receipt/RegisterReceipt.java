package com.routeone.interview.receipt;

import com.routeone.interview.Receipt;
import com.routeone.interview.inventory.dataModel.InventoryItem;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Brian
 */
public class RegisterReceipt implements Receipt {

    private ArrayList<InventoryItem> purchasedInventoryItems = null;
    private static final Logger LOG = LogManager.getLogger(RegisterReceipt.class);

    private RegisterReceipt() {
    }

    public RegisterReceipt(ArrayList<InventoryItem> orderedInventoryItems) {
        this.purchasedInventoryItems = orderedInventoryItems;
    }

    /**
     * Evaluates the sum of all purchased items and returns the total in the
     * format of $#,###.##.
     *
     * @return
     */
    @Override
    public String getFormattedTotal() {
        double total = 0.0;
        for (InventoryItem item : this.getPurchasedInventoryItems()) {
            total += item.getPrice();
        }
        DecimalFormat formatter = new DecimalFormat("#,###.##");
        return "$" + formatter.format(total);
    }

    /**
     * Sorts all purchased items by price and name and returns the sorted list
     * of component names.
     *
     * @return
     */
    @Override
    public List<String> getOrderedItems() {
        ArrayList<String> orderedItemNames = new ArrayList<String>();
        Collections.sort(this.getPurchasedInventoryItems(), new Comparator<InventoryItem>() {
            @Override
            public int compare(InventoryItem item1, InventoryItem item2) {
                int compare = Double.compare(item2.getPrice(), item1.getPrice());
                if (compare == 0) {
                    compare = item1.getComponentName().compareTo(item2.getComponentName());
                }
                return compare;
            }
        });
        for (InventoryItem item : this.getPurchasedInventoryItems()) {
            orderedItemNames.add(item.getComponentName());
        }
        return orderedItemNames;
    }

    /**
     * @return the purchasedInventoryItems
     */
    private ArrayList<InventoryItem> getPurchasedInventoryItems() {
        if (purchasedInventoryItems == null) {
            this.setPurchasedInventoryItems(new ArrayList<InventoryItem>());
        }
        return purchasedInventoryItems;
    }

    /**
     * @param purchasedInventoryItems the purchasedInventoryItems to set
     */
    private void setPurchasedInventoryItems(ArrayList<InventoryItem> purchasedInventoryItems) {
        this.purchasedInventoryItems = purchasedInventoryItems;
    }
}
