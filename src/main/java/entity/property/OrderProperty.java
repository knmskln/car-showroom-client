package entity.property;

import entity.*;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class OrderProperty {
    private IntegerProperty userId;
    private IntegerProperty orderId;
    private StringProperty name;
    private StringProperty surname;
    private StringProperty mark;
    private StringProperty model;
    private IntegerProperty year;
    private StringProperty color;
    private IntegerProperty price;
    private StringProperty statusName;
    private IntegerProperty sellerId;
    private StringProperty sellerName;
    private StringProperty sellerSurname;

    public OrderProperty(Order order) {
        this.userId = new SimpleIntegerProperty(order.getUserId().getUserId());
        this.orderId = new SimpleIntegerProperty(order.getOrderId());
        this.name = new SimpleStringProperty(order.getUserId().getFirstName());
        this.surname = new SimpleStringProperty(order.getUserId().getLastName());
        this.mark = new SimpleStringProperty(order.getCarId().getMark());
        this.model = new SimpleStringProperty(order.getCarId().getModel());
        this.year = new SimpleIntegerProperty(order.getCarId().getYear());
        this.color = new SimpleStringProperty(order.getCarId().getColor());
        this.price = new SimpleIntegerProperty(order.getCarId().getPrice());
        this.statusName = new SimpleStringProperty(order.getOrderStatus().getStatusName());
        this.sellerId = new SimpleIntegerProperty(order.getSellerId().getUserId());
        this.sellerName = new SimpleStringProperty(order.getSellerId().getFirstName());
        this.sellerSurname = new SimpleStringProperty(order.getSellerId().getLastName());
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

    public String getSurname() {
        return surname.get();
    }

    public StringProperty surnameProperty() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname.set(surname);
    }

    public String getMark() {
        return mark.get();
    }

    public StringProperty markProperty() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark.set(mark);
    }

    public String getModel() {
        return model.get();
    }

    public StringProperty modelProperty() {
        return model;
    }

    public void setModel(String model) {
        this.model.set(model);
    }

    public int getYear() {
        return year.get();
    }

    public IntegerProperty yearProperty() {
        return year;
    }

    public void setYear(int year) {
        this.year.set(year);
    }

    public String getColor() {
        return color.get();
    }

    public StringProperty colorProperty() {
        return color;
    }

    public void setColor(String color) {
        this.color.set(color);
    }

    public int getPrice() {
        return price.get();
    }

    public IntegerProperty priceProperty() {
        return price;
    }

    public void setPrice(int price) {
        this.price.set(price);
    }

    public String getStatusName() {
        return statusName.get();
    }

    public StringProperty statusNameProperty() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName.set(statusName);
    }

    public int getOrderId() {
        return orderId.get();
    }

    public IntegerProperty orderIdProperty() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId.set(orderId);
    }

    public int getUserId() {
        return userId.get();
    }

    public IntegerProperty userIdProperty() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId.set(userId);
    }

    public int getSellerId() {
        return sellerId.get();
    }

    public IntegerProperty sellerIdProperty() {
        return sellerId;
    }

    public void setSellerId(int sellerId) {
        this.sellerId.set(sellerId);
    }

    public String getSellerName() {
        return sellerName.get();
    }

    public StringProperty sellerNameProperty() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName.set(sellerName);
    }

    public String getSellerSurname() {
        return sellerSurname.get();
    }

    public StringProperty sellerSurnameProperty() {
        return sellerSurname;
    }

    public void setSellerSurname(String sellerSurname) {
        this.sellerSurname.set(sellerSurname);
    }
}
