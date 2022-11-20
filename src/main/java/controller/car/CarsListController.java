package controller.car;

import cooperation.ClientRequest;
import cooperation.ServerResponse;
import entity.Car;
import entity.Status;
import entity.User;
import entity.property.CarProperty;
import entity.property.UserProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import main.Runner;
import util.MapParser;
import util.validator.SceneChanger;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import static javafx.scene.control.Alert.AlertType.ERROR;
import static javafx.scene.control.Alert.AlertType.INFORMATION;

public class CarsListController {

    @FXML
    private TableView<CarProperty> carsTable;

    @FXML
    private TableColumn<CarProperty, String> markColumn;

    @FXML
    private TableColumn<CarProperty, String> modelColumn;

    @FXML
    private TableColumn<CarProperty, Integer> yearColumn;

    @FXML
    private TableColumn<CarProperty, String> colorColumn;

    @FXML
    private TableColumn<CarProperty, Integer> priceColumn;


    @FXML
    private Button addCar;

    @FXML
    private Button deleteCar;

    @FXML
    private Button orderCar;

    @FXML
    private Text mark;

    @FXML
    private Text model;


    @FXML
    private Button back;

    @FXML
    private Button main;

    @FXML
    private Button saveToFile;


    private SceneChanger sceneChanger = SceneChanger.getInstance();
    private List<Car> cars;
    private MapParser parser = MapParser.getInstance();

    private UserProperty user;

    @FXML
    private void initialize() {
        if (!"dealer".equals(Runner.getStatus().getRoleName())) {
            addCar.setVisible(false);
            deleteCar.setVisible(false);
        }

        if (!"user".equals(Runner.getStatus().getRoleName())) {
            orderCar.setVisible(false);
        }

        fillWorkersTable();

        addCar.setOnAction(event -> {
            sceneChanger.changeSceneAndWait("/fxml/add-car.fxml");
            fillWorkersTable();
        });

        deleteCar.setOnAction(event -> {
            deleteCar();
            fillWorkersTable();
        });

        orderCar.setOnAction(event -> {
            orderCar();
            fillWorkersTable();
        });

        carsTable.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                CarProperty carProperty = carsTable.getSelectionModel().getSelectedItem();
                sceneChanger.setDataId(carProperty.getCarId().getValue());
                sceneChanger.changeSceneAndWait("/fxml/edit-car.fxml");
                fillWorkersTable();
            }
        });

        back.setOnAction(event -> {
            back.getScene().getWindow().hide();
            SceneChanger.getInstance().changeScene("/fxml/main.fxml");
        });
        main.setOnAction(event -> {
            main.getScene().getWindow().hide();
            SceneChanger.getInstance().changeScene("/fxml/main.fxml");
        });


        this.saveToFile.setOnAction(event -> {

            try {
                FileWriter writer = new FileWriter("src/main/resources/cars" + ".txt");

                for (Car car : this.cars) {
                    String mark = car.getMark();
                    String model = car.getModel();

                    Integer year = car.getYear();
                    String color = car.getColor();
                    Integer price = car.getPrice();

                    String text =
                            " Марка: " + mark
                                    + " Модель:   " + model
                                    + " Год Выпуска  " + year
                                    + " Цвет   " + color
                                    + " Цена  " + price;

                    writer.write(text + System.getProperty("line.separator"));
                }
                writer.close();


                Alert alert = new Alert(INFORMATION, "Отчет успешно сохранен в файл!");
                alert.show();

            } catch (IOException e) {
                e.printStackTrace();
                Alert alert = new Alert(ERROR, "Произошла ошибка при записи файл!");
                alert.show();
            }
        });
    }

    private void getCars() {
        Runner.sendData(new ClientRequest("getAllCars", new HashMap<>()));
        ServerResponse response = Runner.getData();
        if (!response.isError()) {
            Map<String, Object> productMap = response.getData();
            List productData = (List) productMap.get("cars");
            cars = parser.cars(productData);
        }
    }

    private void fillWorkersTable() {
        getCars();
        List<CarProperty> carProperties = new ArrayList<>();
        cars.forEach(car -> carProperties.add(new CarProperty(car)));
        carsTable.setItems(FXCollections.observableArrayList(carProperties));
        markColumn.setCellValueFactory(cellData -> cellData.getValue().getMark());
        modelColumn.setCellValueFactory(cellData -> cellData.getValue().getModel());
        yearColumn.setCellValueFactory(cellData -> {
            Integer year = cellData.getValue().getYear().intValue();
            return new SimpleIntegerProperty(year).asObject();
        });
        colorColumn.setCellValueFactory(cellData -> cellData.getValue().getColor());
        priceColumn.setCellValueFactory(cellData -> {
            Integer price = cellData.getValue().getPrice().intValue();
            return new SimpleIntegerProperty(price).asObject();
        });

        showCarDetails(null);
        carsTable.getSelectionModel()
                .selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> showCarDetails(newValue));

    }

    private void showCarDetails(CarProperty carProperty) {
        if (carProperty != null) {
            mark.setText(carProperty.getMark().getValue());
            model.setText(carProperty.getModel().getValue());
            if ("dealer".equals(Runner.getStatus().getRoleName())) {
                deleteCar.setVisible(true);
            }
        } else {
            mark.setText("");
            model.setText("");
            deleteCar.setVisible(false);
        }
    }

    private void deleteCar() {
        int carId = carsTable.getSelectionModel()
                .selectedItemProperty()
                .getValue()
                .getCarId()
                .getValue();
        Map<String, Object> map = new HashMap<>();
        map.put("carId", carId);
        Runner.sendData(new ClientRequest("deleteCar", map));
        ServerResponse response = Runner.getData();
        if (response.isError()) {
            Alert alert = new Alert(ERROR, "Произошла ошибка при удалении автомобиля!");
            alert.show();
        }
    }

    private void orderCar() {
        int carId = carsTable.getSelectionModel()
                .selectedItemProperty()
                .getValue()
                .getCarId()
                .getValue();
        Status status = new Status(1,"orderFromSeller");
        int statusId = status.getStatusId();
        int userId = Runner.getUserId();
        Map<String, Object> map = new HashMap<>();
        map.put("carId", carId);
        map.put("userId", userId);
        map.put("statusId", statusId);
        Runner.sendData(new ClientRequest("orderCar", map));
        ServerResponse response = Runner.getData();
        if (response.isError()) {
            Alert alert = new Alert(ERROR, "Произошла ошибка при заказе автомобиля!");
            alert.show();
        }
        else {
            Alert alert = new Alert(INFORMATION, "Вы отправили заявку! Заявка на обработке у продавца");
            alert.show();
        }
    }

}