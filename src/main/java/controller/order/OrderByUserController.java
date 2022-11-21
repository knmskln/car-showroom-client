package controller.order;

//import com.sun.org.apache.xpath.internal.operations.Or;

import cooperation.ClientRequest;
import cooperation.ServerResponse;
import entity.property.OrderProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import main.Runner;
import util.MapParser;
import util.validator.SceneChanger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import entity.Order;

public class OrderByUserController {

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
    private Button back;
    @FXML
    private Button main;

    private SceneChanger sceneChanger = SceneChanger.getInstance();

    private MapParser parser = MapParser.getInstance();

    private List<Order> orders;

    @FXML
    private void initialize() {

        fillOrdersTable();

        back.setOnAction(event -> {
            back.getScene().getWindow().hide();
            SceneChanger.getInstance().changeScene("/fxml/main.fxml");
        });

        main.setOnAction(event -> {
            main.getScene().getWindow().hide();
            SceneChanger.getInstance().changeScene("/fxml/main.fxml");
        });
    }

    private void getOrders() {
        int userId = Runner.getUserId();
        Map<String, Object> data = new HashMap<>();
        data.put("userId", userId);
        Runner.sendData(new ClientRequest("getOrderByUserId", data));
        ServerResponse response = Runner.getData();
        if (!response.isError()) {
            Map<String, Object> productMap = response.getData();
                List productData = (List) productMap.get("orders");
                orders = parser.orders(productData);
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
    }
}

