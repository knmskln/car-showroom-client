package controller.order;
//import com.sun.org.apache.xpath.internal.operations.Or;
import cooperation.ClientRequest;
import cooperation.ServerResponse;
import entity.property.OrderProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import main.Runner;
import util.MapParser;
import util.validator.SceneChanger;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import entity.Order;


import static javafx.scene.control.Alert.AlertType.ERROR;
import static javafx.scene.control.Alert.AlertType.INFORMATION;

public class OrderByStatusController {

    @FXML
    private TableView<OrderProperty> ordersTable;

    @FXML
    private TableColumn<OrderProperty, String> markColumn;

    @FXML
    private TableColumn<OrderProperty, String> modelColumn;

    @FXML
    private TableColumn<OrderProperty, Integer> yearColumn;

    @FXML
    private TableColumn<OrderProperty, String> colorColumn;

    @FXML
    private TableColumn<OrderProperty, Integer> priceColumn;

    @FXML
    private TableColumn<OrderProperty, String> nameColumn;

    @FXML
    private TableColumn<OrderProperty, String> surnameColumn;

    @FXML
    private TableColumn<OrderProperty, String> statusColumn;

    @FXML
    private Button orderFromDealer;
    @FXML
    private Button rejected;
    @FXML
    private Button back;
    @FXML
    private Button main;
    @FXML
    private Button approved;

    @FXML
    private Button saveToFile;

    private SceneChanger sceneChanger = SceneChanger.getInstance();

    private MapParser parser = MapParser.getInstance();

    private List<Order> orders;

    @FXML
    private void initialize() {
        if (!"seller".equals(Runner.getStatus().getRoleName())) {
            orderFromDealer.setVisible(false);
        }
        if (!"seller".equals(Runner.getStatus().getRoleName())&&
                !"dealer".equals(Runner.getStatus().getRoleName())) {
            rejected.setVisible(false);
        }
        if (!"dealer".equals(Runner.getStatus().getRoleName())) {
            approved.setVisible(false);
        }
        if (!"admin".equals(Runner.getStatus().getRoleName())) {
            saveToFile.setVisible(false);
        }
        fillOrdersTable();

        back.setOnAction(event -> {
            back.getScene().getWindow().hide();
            SceneChanger.getInstance().changeScene("/fxml/main.fxml");
        });

        orderFromDealer.setOnAction(event -> {
            changeOrderStatus(2);
            fillOrdersTable();
        });

        rejected.setOnAction(event -> {
            changeOrderStatus(4);
            fillOrdersTable();
        });

        approved.setOnAction(event -> {
            changeOrderStatus(3);
            fillOrdersTable();
        });

        main.setOnAction(event -> {
            main.getScene().getWindow().hide();
            SceneChanger.getInstance().changeScene("/fxml/main.fxml");
        });

        this.saveToFile.setOnAction(event -> {

            try {
                FileWriter writer = new FileWriter("src/main/resources/orders" + ".txt");

                for (Order order : this.orders) {
                    String mark = order.getCarId().getMark();
                    String model = order.getCarId().getModel();
                    Integer year = order.getCarId().getYear();
                    String color = order.getCarId().getColor();
                    Integer price = order.getCarId().getPrice();
                    String name = order.getUserId().getFirstName();
                    String surname = order.getUserId().getLastName();
                    String status = order.getOrderStatus().getStatusName();

                    String text =
                            " Марка: " + mark
                                    + " Модель:   " + model
                                    + " Год Выпуска  " + year
                                    + " Цвет   " + color
                                    + " Цена  " + price
                                    + " Имя   " + name
                                    + " Фамилия  " + surname
                                    + " Статус заказа  " + status;

                    writer.write(text + System.getProperty("line.separator"));
                }
                writer.close();


                Alert alert = new Alert(INFORMATION, "Заказы успешно сохранены в файл!");
                alert.show();

            } catch (IOException e) {
                e.printStackTrace();
                Alert alert = new Alert(ERROR, "Произошла ошибка при записи файл!");
                alert.show();
            }
        });
    }

    private void getOrders() {
        int statusId = 1;
        if("dealer".equals(Runner.getStatus().getRoleName())){
             statusId = 2;
        }
        else if("seller".equals(Runner.getStatus().getRoleName())){
            statusId = 1;
        }
        Map<String, Object> data = new HashMap<>();
        data.put("statusId", statusId);
        Runner.sendData(new ClientRequest("getOrderByStatus", data));
        ServerResponse response = Runner.getData();
        if (!response.isError()) {
            Map<String, Object> productMap = response.getData();
            List productData = (List) productMap.get("orders");
            orders = parser.orders(productData);
        }
    }
    private void changeOrderStatus(int status) {
        OrderProperty order = ordersTable.getSelectionModel().getSelectedItem();
        int userId = order.getUserId();
        int orderId = order.getOrderId();
        Map<String, Object> data = new HashMap<>();
        data.put("orderId", orderId);
        data.put("statusId", status);
        data.put("userId", userId);
        Runner.sendData(new ClientRequest("changeOrderStatus", data));
        ServerResponse response = Runner.getData();
        if (!response.isError()) {
            getOrders();
            Alert alert = new Alert(INFORMATION, "Статус заявки изменен!");
            alert.show();
        } else {
            Alert alert = new Alert(ERROR, "Произошла ошибка!");
            alert.show();
        }
    }
    private void fillOrdersTable() {
        getOrders();
        List<OrderProperty> orderProperties = new ArrayList<>();
        orders.forEach(order -> orderProperties.add(new OrderProperty(order)));
        ordersTable.setItems(FXCollections.observableArrayList(orderProperties));

        markColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMark()));
        modelColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getModel()));
        yearColumn.setCellValueFactory(cellData -> {
            Integer year = cellData.getValue().getYear();
            return new SimpleIntegerProperty(year).asObject();
        });
        colorColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getColor()));
        priceColumn.setCellValueFactory(cellData -> {
            Integer price = cellData.getValue().getPrice();
            return new SimpleIntegerProperty(price).asObject();
        });
        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        surnameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSurname()));
        statusColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getStatusName()));
        viewActionsForOrder(null);
        ordersTable.getSelectionModel()
                .selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> viewActionsForOrder(newValue));
    }

    private void viewActionsForOrder(OrderProperty order) {
        if (order != null) {
            if("seller".equals(Runner.getStatus().getRoleName())){
                if ("orderFromSeller".equals(order.getStatusName())) {
                    orderFromDealer.setVisible(true);
                    rejected.setVisible(true);
                } else {
                    orderFromDealer.setVisible(false);
                    rejected.setVisible(false);
                }
            }
            if("dealer".equals(Runner.getStatus().getRoleName())){
                if ("orderFromDealer".equals(order.getStatusName())) {
                    approved.setVisible(true);
                    rejected.setVisible(true);
                } else {
                    approved.setVisible(false);
                    rejected.setVisible(false);
                }
            }
        } else {
            orderFromDealer.setVisible(false);
            approved.setVisible(false);
            rejected.setVisible(false);
        }
    }
}

