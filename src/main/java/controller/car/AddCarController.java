package controller.car;

import cooperation.ClientRequest;
import cooperation.ServerResponse;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import main.Runner;
import util.MapParser;
import util.validator.CarInformationValidator;

import java.util.HashMap;
import java.util.Map;

import static javafx.scene.control.Alert.AlertType.ERROR;
import static javafx.scene.control.Alert.AlertType.INFORMATION;

public class AddCarController {

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

    @FXML
    private void initialize() {
        if (!"dealer".equals(Runner.getStatus().getRoleName())) {
            addCar.setVisible(false);
        }
        addCar.setOnAction(event -> addCar());
        cancel.setOnAction(event -> cancel.getScene().getWindow().hide());
    }

    private void addCar() {
        String markText = this.mark.getText();
        String modelText = this.model.getText();
        String yearText = this.year.getText();
        String colorText = this.color.getText();
        String priceText = this.price.getText();

        if (validator.validate(markText, modelText, colorText)){
            Map<String, Object> data = new HashMap<>();
            data.put("mark", markText);
            data.put("model", modelText);
            data.put("year", Integer.parseInt(yearText));
            data.put("color", colorText);
            data.put("price", Integer.parseInt(priceText));

            Runner.sendData(new ClientRequest("addCar", data));
            ServerResponse response = Runner.getData();
            if (!response.isError()) {
                addCar.getScene().getWindow().hide();
                Alert alert = new Alert(INFORMATION, "Автомобиль успешно добавлен!");
                alert.show();
            } else {
                Alert alert = new Alert(ERROR, "Информация некорректна!");
                alert.show();
            }
        }
        else {
            Alert alert = new Alert(ERROR, "Информация некорректна:\n" +
                    "1) Марка, модель и цвет должны состоять из 2-30 символов: латинских букв\n" +
                    "2)  \n");
            alert.show();
        }
    }
}