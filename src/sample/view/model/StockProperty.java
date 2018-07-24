package sample.view.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ObservableValue;
import sample.entity.Stock;

public class StockProperty {
    private final ObservableValue<Integer> id;
    private final ObservableValue<Integer> productId;
    private final ObservableValue<Integer> quantity;
    private final ObservableValue<Integer> shopId;


    public StockProperty(Stock stock) {
        this.id = new SimpleIntegerProperty(stock.getId()).asObject();
        this.productId = new SimpleIntegerProperty(stock.getProductId()).asObject();
        this.quantity = new SimpleIntegerProperty(stock.getQuantity()).asObject();
        this.shopId = new SimpleIntegerProperty(stock.getShopId()).asObject();
    }

    public StockProperty() {
        this.id = new SimpleIntegerProperty().asObject();
        this.productId = new SimpleIntegerProperty().asObject();
        this.quantity = new SimpleIntegerProperty().asObject();
        this.shopId = new SimpleIntegerProperty().asObject();
    }


    public ObservableValue<Integer> idProperty() {
        return id;
    }


    public ObservableValue<Integer> productIdProperty() {
        return productId;
    }


    public ObservableValue<Integer> quantityProperty() {
        return quantity;
    }

    public ObservableValue<Integer> shopIdProperty() {
        return shopId;
    }
}
