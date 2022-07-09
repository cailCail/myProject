package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * This class has the inventory for parts and products. It also has methods to add, search, update and delete.
 */
public class Inventory {

    private static final ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static final ObservableList<Product> allProducts = FXCollections.observableArrayList();

    /**
     * This is the nextid which establishes a unique id count for parts.
     */
    public static int nextid = 1;

    /**
     * This is the method to add a new part to the inventory.
     * The part is displayed in the parts table on the main menu screen.
     * @param newPart a new part to be added
     */
    public static void addPart(Part newPart) {
        allParts.add(newPart);
    }

    /**
     * This method is to add a new product to the inventory.
     * The product is displayed in the product table on the main screen.
     * @param newProduct the new product to be added
     */
    public static void addProduct(Product newProduct) {
        allProducts.add(newProduct);
    }

    /**
     * This method is to search for parts by partID.
     * This method looks through the current parts in the inventory and returns a match if present.
     * @param partID the part id to be searched
     * @return the part matching the ID if part is found
     */
    public static Part lookupPart(int partID) {

        for (Part pa : allParts) {
            if (pa.getId() == partID)
                return pa;
        }
        return null;
    }
    /**
     * This is the method to search products by exact product ID.
     * This method looks through the current products in the inventory and returns a match if present.
     * @param productID the product id to be searched
     * @return the part matching the ID if product is found
     */
    public static Product lookupProduct(int productID) {

        for (Product p : allProducts) {
            if (p.getId() == productID)
                return p;
        }

        return null;
    }
    /**
     * This is the method to search parts by exact or partial part name.
     * This method looks through the current parts in the inventory and returns an exact or partial match if present in one or more parts on inventory.
     * @param partName the part name to be selected.
     * @return the parts matching the search criteria by exact or partial match
     */
    public static ObservableList<Part> lookupPart(String partName) {
        ObservableList<Part> namedPart = FXCollections.observableArrayList();

        ObservableList<Part> allP = Inventory.getAllParts();

        for (Part p : allP) {
            if (p.getName().contains(partName)) {
                namedPart.add(p);
            }

        }

        return namedPart;
    }

    /**
     * This is the method to search products by exact or partial product name.
     * This method looks through the current products in the inventory and returns an exact or partial match if present in one or more products in inventory.
     * @param productName the product name to be selected.
     * @return the product matching the search criteria by exact or partial match.
     */
    public static ObservableList<Product> lookupProduct(String productName) {
        ObservableList<Product> namedProduct = FXCollections.observableArrayList();

        ObservableList<Product> allProd = Inventory.getAllProducts();

        for (Product pro : allProd) {
            if (pro.getName().contains(productName)) {
                namedProduct.add(pro);
            }

        }
        return namedProduct;
    }

    /**
     * This is the method to update a product.
     * This method takes the preexisting product being modified and replace it in inventory using the product ID.
     * @param selectedProduct the selected Product to be updated
     * @param index the index of the product to be updated
     * */
    public static void updateProduct(int index, Product selectedProduct) {
        allProducts.set(index, selectedProduct);
    }

    /**
     * This is the method to update a part.
     * This method takes the preexisting part being modified and replace it in inventory using the prart ID.
     * @param index the index of the part to be updated
     * @param selectedPart  the selected part to be updated
     * */
    public static void updatePart(int index, Part selectedPart) {
        allParts.set(index, selectedPart);

    }

    /**
     * This is the method to delete a part.
     * The selected part is removed from the inventory.
     * @param selectedPart the selected part to be deleted
     * */
    public static boolean deletePart(Part selectedPart) {
        allParts.remove(selectedPart);
        return true;
    }
    /**
     * This is the method to delete a product.
     * The selected product is removed from the inventory.
     * @param selectedProduct is the selected product to be deleted.
     * */
    public static boolean deleteProduct(Product selectedProduct) {
        allProducts.remove(selectedProduct);
        return true;

    }
    /**
     * This is the method to get all parts.
     * All parts in inventory are returned.
     * @return all parts currently in inventory.
     * */
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    /**
     * This is the method to get all products.
     * All products in inventory are returned.
     * @return all products currently in inventory.
     * */
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }

}
