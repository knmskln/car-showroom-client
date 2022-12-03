package controller.statistics;

import cooperation.ClientRequest;
import cooperation.ServerResponse;
import entity.property.OrderProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.text.Text;
import main.Runner;
import util.MapParser;
import util.validator.SceneChanger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import entity.Order;

public class StatisticsController {
    @FXML
    private TableView<OrderProperty> statisticsTable;
    @FXML
    private Text kpi;
    @FXML
    private Text count;
    @FXML
    private TableColumn<OrderProperty, String> nameColumn;
    @FXML
    private TableColumn<OrderProperty, String> surnameColumn;
    @FXML
    private TableColumn<OrderProperty, String> clientSurnameColumn;
    @FXML
    private TableColumn<OrderProperty, String> statusColumn;
    @FXML
    private Button back;
    @FXML
    private Button main;
    @FXML
    private Button createStatistics;

    private SceneChanger sceneChanger = SceneChanger.getInstance();
    private MapParser parser = MapParser.getInstance();

    private List<Order> orders;

    int productData = 0;

    @FXML
    private void initialize() {
        fillStatisticsTable();

        back.setOnAction(event -> {
            back.getScene().getWindow().hide();
            SceneChanger.getInstance().changeScene("/fxml/main.fxml");
        });

        main.setOnAction(event -> {
            main.getScene().getWindow().hide();
            SceneChanger.getInstance().changeScene("/fxml/main.fxml");
        });

        this.createStatistics.setOnAction(event -> {
            fillKpiTable();
        });
    }
    private void getOrders() {
        Runner.sendData(new ClientRequest("getOrdersForStatistics", new HashMap<>()));
        ServerResponse response = Runner.getData();
        if (!response.isError()) {
            Map<String, Object> productMap = response.getData();
            List productData = (List) productMap.get("orders");
            orders = parser.orders(productData);
        }
    }
    private void createStatistics() {
        int sellerId = statisticsTable.getSelectionModel()
                .selectedItemProperty()
                .getValue()
                .getSellerId();
        Map<String, Object> map = new HashMap<>();
        map.put("sellerId", sellerId);
        Runner.sendData(new ClientRequest("getCountApproved", map));
        ServerResponse response = Runner.getData();
        if (!response.isError()) {
            Map<String, Object> productMap = response.getData();
            productData = (int) productMap.get("count");
        }
    }
    private void fillStatisticsTable() {
        getOrders();
        List<OrderProperty> orderProperties = new ArrayList<>();
        orders.forEach(order -> orderProperties.add(new OrderProperty(order)));
        statisticsTable.setItems(FXCollections.observableArrayList(orderProperties));
        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSellerName()));
        surnameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSellerSurname()));
        clientSurnameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSurname()));
        statusColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getStatusName()));
    }
    private void fillKpiTable() {
        createStatistics();
        count.setText(Integer.toString(productData));
        Double kpi2 = Double.parseDouble(Integer.toString(productData));
        kpi.setText(Double.toString(kpi2 / 5));
    }
}

