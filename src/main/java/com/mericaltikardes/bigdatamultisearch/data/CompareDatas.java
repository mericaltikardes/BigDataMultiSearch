package com.mericaltikardes.bigdatamultisearch.data;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class CompareDatas {
    private SimpleStringProperty Product1;
    private SimpleStringProperty Product2;

    private SimpleIntegerProperty benzerlik;

    public String getProduct1() {
        return Product1.get();
    }

    public void setProduct1(String product1) {
        Product1 = new SimpleStringProperty(product1);
    }

    public String getProduct2() {
        return Product2.get();
    }

    public void setProduct2(String product2) {
        Product2 = new SimpleStringProperty(product2);
    }

    public int getBenzerlik() {
        return benzerlik.get();
    }

    public void setBenzerlik(int benzerlik) {
        this.benzerlik = new SimpleIntegerProperty(benzerlik);
    }

    public CompareDatas(String product1, String product2, int benzerlik) {
        Product1 = new SimpleStringProperty(product1);
        Product2 = new SimpleStringProperty(product2);
        this.benzerlik = new SimpleIntegerProperty(benzerlik);
    }

    @Override
    public String toString() {
        return "Kiyas{" +
                "Product1=" + Product1 +
                ", Product2=" + Product2 +
                ", benzerlik=" + benzerlik +
                '}';
    }
}
