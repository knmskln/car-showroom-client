package util;

import com.fasterxml.jackson.databind.ObjectMapper;
//import com.sun.org.apache.xpath.internal.operations.Or;
import entity.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MapParser {

    private static final MapParser INSTANCE = new MapParser();

    public static MapParser getInstance() {
        return INSTANCE;
    }

    private MapParser() {
    }

    private ObjectMapper mapper = new ObjectMapper();

    public List<User> users(List userData) {
        List<User> users = new ArrayList<>();
        for (Map<String, Object> user : (List<Map<String, Object>>) userData) {
            users.add(user(user));
        }
        return users;
    }

    public User user(Map<String, Object> map) {
        return new User((int) map.get("userId"),
                (String) map.get("login"),
                (String) map.get("name"),
                (String) map.get("surname"),
                roleId((Map<String, Object>) map.get("userRole")),
                (String) map.get("email"),
                (boolean) map.get("banned"));
    }
    public User userId(Map<String, Object> map) {
        return new User((int) map.get("userId"),
                (String) map.get("login"),
                (String) map.get("name"),
                (String) map.get("surname"),
                roleId((Map<String, Object>) map.get("userRole")),
                (String) map.get("email"),
                (boolean) map.get("banned"));
    }

    public Role roleId(Map<String, Object> map) {
        return new Role(
                (int) map.get("roleId"),
                (String) map.get("roleName"));
    }

    public Status orderStatus(Map<String, Object> map) {
        return new Status((int) map.get("statusId"), (String) map.get("statusName"));
    }

    public List<Car> cars(List carsData) {
        List<Car> cars = new ArrayList<>();
        for (Map<String, Object> car : (List<Map<String, Object>>) carsData) {
            cars.add(car(car));
        }
        return cars;
    }

    public Car car(Map<String, Object> map) {
        Car car = new Car();
        car.setCarId((int) map.get("carId"));
        car.setMark((String) map.get("mark"));
        car.setModel((String) map.get("model"));
        car.setYear((Integer) map.get("year"));
        car.setColor((String) map.get("color"));
        car.setPrice((Integer) map.get("price"));
        return car;
    }

    public Car carId(Map<String, Object> map) {
        return new Car(
                (int) map.get("carId"),
                (String) map.get("mark"),
                (String) map.get("model"),
                (Integer) map.get("year"),
                (String) map.get("color"),
                (Integer) map.get("price")
        );
    }

    public List<Order> orders(List ordersData) {
        List<Order> orders = new ArrayList<>();
        for (Map<String, Object> order : (List<Map<String, Object>>) ordersData) {
            orders.add(order(order));
        }
        return orders;
    }

    public Order order(Map<String, Object> map) {
        return new Order((int) map.get("orderId"),
                userId((Map<String, Object>) map.get("userId")),
                carId((Map<String, Object>) map.get("carId")),
                orderStatus((Map<String, Object>) map.get("orderStatus")));
    }
}