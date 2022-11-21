package entity;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;

public class Order {
    private int orderId;
    private User userId;
    private Car carId;
    private Status orderStatus;


    public Order(int orderId,
                 User userId, Car carId,
                 Status orderStatus) {
        this.orderId = orderId;
        this.userId = userId;
        this.carId = carId;
        this.orderStatus = orderStatus;
    }

    public Order() {

    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    public Car getCarId() {
        return carId;
    }

    public void setCarId(Car carId) {
        this.carId = carId;
    }

    public Status getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Status orderStatus) {
        this.orderStatus = orderStatus;
    }
}
