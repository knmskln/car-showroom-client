package controller.car;

import cooperation.ClientRequest;
import cooperation.ServerResponse;
import entity.Car;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import main.Runner;
import util.MapParser;
import util.validator.CarInformationValidator;
import util.validator.SceneChanger;
import java.util.HashMap;

import java.util.Map;

import static javafx.scene.control.Alert.AlertType.ERROR;
import static javafx.scene.control.Alert.AlertType.INFORMATION;

public class EditCarController {

    @FXML
    private Button addCar;

    @FXML
    private Button cancel;

    @FXML
    private TextField mark;

    @FXML
    private TextField model;

    @FXML
    private TextField year;

    @FXML
    private TextField color;
    @FXML
    private TextField price;

    private MapParser parser = MapParser.getInstance();
    private CarInformationValidator validator = CarInformationValidator.getInstance();
    private Car car;

    @FXML
    private void initialize() {
        addCar.setOnAction(event -> updateCar());
        cancel.setOnAction(event -> cancel.getScene().getWindow().hide());
        this.initForm(SceneChanger.getInstance().getDataId());
    }

    private void initForm(int carId) {
        getCar(carId);
        this.initForm();

        if (!"admin".equals(Runner.getStatus().getRoleName())&&
                !"dealer".equals(Runner.getStatus().getRoleName())) {
            addCar.setVisible(false);
            mark.setEditable(false);
            model.setEditable(false);
            year.setEditable(false);
            color.setEditable(false);
            price.setEditable(false);
        }
    }

    private void initForm() {
        this.mark.setText(this.car.getMark());
        this.model.setText(this.car.getModel());
        this.year.setText(String.valueOf(this.car.getYear()));
        this.color.setText(this.car.getColor());
        this.price.setText(String.valueOf(this.car.getPrice()));
    }

    private void getCar(int carId) {
        Map<String, Object> data = new HashMap<>();
        data.put("carId", carId);
        Runner.sendData(new ClientRequest("getCarById", data));
        ServerResponse response = Runner.getData();
        if (!response.isError()) {
            Map<String, Object> workerMap = response.getData();
            car = parser.car((Map<String, Object>) workerMap.get("car"));
        }
    }

    private void updateCar() {
        String markText = this.mark.getText();
        String modelText = this.model.getText();
        String yearText = this.year.getText();
        String colorText = this.color.getText();
        String priceText = this.price.getText();

        if (validator.validate(markText, modelText, colorText)){
            Map<String, Object> data = new HashMap<>();
            data.put("carId", this.car.getCarId());
            data.put("mark", markText);
            data.put("model", modelText);
            data.put("year", Integer.parseInt(yearText));
            data.put("color", colorText);
            data.put("price", Integer.parseInt(priceText));
            Runner.sendData(new ClientRequest("editCar", data));
            ServerResponse response = Runner.getData();
            if (!response.isError()) {
                addCar.getScene().getWindow().hide();
                        Alert alert = new Alert(INFORMATION, "Автомобиль успешно обновлен!");
                        alert.show();
            } else {
                Alert alert = new Alert(ERROR, "Информация некорректна! " + response.getErrorMessage());
                alert.show();
            }
        }
    }
}

