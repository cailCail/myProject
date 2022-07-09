package model;

/**
 * This class creates the Product objects.
 */

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Product {

    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();

    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;
    public static int nextid = 1;
    public Product(int id, String name, double
            price, int stock, int min, int max) {

        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /**
     * @param name the name is set.
     * */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @param price the price is set.
     * */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * @param stock the stock is set.
     * */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * @param min the min is set.
     * */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     * @param max the max is set.
     * */
    public void setMax(int max) {
        this.max = max;
    }

    /**
     * @param id  the id is set.
     * */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the name.
     * */
    public String getName() {
        return name;
    }

    /**
     * @ return the price.
     * */
    public double getPrice() {
        return price;
    }

    /**
     * @return the stock.
     * */
    public int getStock() {
        return stock;
    }

    /**
     * @return the min.
     * */
    public int getMin() {
        return min;
    }

    /**
     * @ return the max.
     * */
    public int getMax() {
        return max;
    }

    /**
     * @ return the id.
     * */
    public int getId() {
        return id;
    }

    /**
     * @param part the part to be added.
     * */
    public void addAssociatedPart(Part part) {
        associatedParts.add(part);
    }

    /**
     * @return the associated parts.
     * */
    public ObservableList<Part> getAllAssociatedParts() {
        return associatedParts;
    }

    /**
     * @param selectedAssociatedPart delete the selected associated part.
     * */
    public boolean deleteAssociatedPart(Part selectedAssociatedPart) {
        return associatedParts.remove(selectedAssociatedPart);
    }

}