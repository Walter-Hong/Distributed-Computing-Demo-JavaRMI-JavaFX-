package sample.view.model;

import javafx.beans.property.*;
import javafx.beans.value.ObservableValue;
import sample.entity.Product;

import java.math.BigDecimal;

public class ProductProperty {
    private final ObservableValue<Integer> id;
    private final ObservableValue<Integer> categoryId;
    private final StringProperty categoryName;
    private final StringProperty name;
    private final StringProperty image;
    private final StringProperty description;
    private final ObjectProperty<BigDecimal> price;

    public Product toProduct() {
        return new Product(getId(), getCategoryId(), getCategoryName(), getName(), getImage(), getPrice(), getDescription());
    }

    public ProductProperty(Product product) {
        this.id = new SimpleIntegerProperty(product.getId()).asObject();
        this.categoryId = new SimpleIntegerProperty(product.getCategoryId()).asObject();
        this.categoryName = new SimpleStringProperty(product.getCategoryName());
        this.name = new SimpleStringProperty(product.getName());
        this.image = new SimpleStringProperty(product.getImage());
        this.description = new SimpleStringProperty(product.getDescription());
        this.price = new SimpleObjectProperty<>(product.getPrice());
    }

    public ProductProperty() {
        this.categoryId = new SimpleIntegerProperty().asObject();
        this.id = new SimpleIntegerProperty().asObject();
        this.categoryName = new SimpleStringProperty();
        this.name = new SimpleStringProperty();
        this.image = new SimpleStringProperty();
        this.description = new SimpleStringProperty();
        this.price = new SimpleObjectProperty<>();
    }

    public Integer getCategoryId() {
        return categoryId.getValue();
    }

    public Integer getId() {
        return id.getValue();
    }

    public ObservableValue<Integer> idProperty() {
        return id;
    }

    public ObservableValue<Integer> categoryIdProperty() {
        return categoryId;
    }

    public String getCategoryName() {
        return categoryName.get();
    }

    public StringProperty categoryNameProperty() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName.set(categoryName);
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getImage() {
        return image.get();
    }

    public StringProperty imageProperty() {
        return image;
    }

    public void setImage(String image) {
        this.image.set(image);
    }

    public String getDescription() {
        return description.get();
    }

    public StringProperty descriptionProperty() {
        return description;
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    public BigDecimal getPrice() {
        return price.get();
    }

    public ObjectProperty<BigDecimal> priceProperty() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price.set(price);
    }

    @Override
    public String toString() {
        return name.get() + "\t" + description.get() + "\t" + price.get() + "$";
    }
}
