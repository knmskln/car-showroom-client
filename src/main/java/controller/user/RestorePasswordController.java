package controller.user;

import cooperation.ClientRequest;
import cooperation.ServerResponse;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import main.Runner;
import util.validator.SceneChanger;
import util.validator.UserInformationValidator;

import java.util.HashMap;
import java.util.Map;

import static javafx.scene.control.Alert.AlertType.ERROR;
import static javafx.scene.control.Alert.AlertType.INFORMATION;

public class RestorePasswordController {

    @FXML
    private Button restorePassword;

    @FXML
    private TextField email;

    @FXML
    private Button back;

    @FXML
    private Button index;

    private UserInformationValidator validator = UserInformationValidator.getInstance();

    @FXML
    private void initialize() {
        restorePassword.setOnAction(event -> processRestorePassword());
        index.setOnAction(event -> {
            index.getScene().getWindow().hide();
            SceneChanger.getInstance().changeScene("/fxml/index.fxml");
        });
        back.setOnAction(event -> {
            back.getScene().getWindow().hide();
            SceneChanger.getInstance().changeScene("/fxml/index.fxml");
        });
    }

    private void processRestorePassword() {
        String emailText = email.getText();
        if (validator.validate(emailText)) {
            Map<String, Object> map = new HashMap<>();
            map.put("email", emailText);
            Runner.sendData(new ClientRequest("restorePassword", map));
            ServerResponse response = Runner.getData();
            if (!response.isError()) {
                restorePassword.getScene().getWindow().hide();
                SceneChanger.getInstance().changeScene("/fxml/index.fxml");
                Alert alert = new Alert(INFORMATION, "�������������� ������ ��������� �������!\n" +
                        "������ � ����� ������� ������� �� ��� ����������� ����.");
                alert.show();
            } else {
                Alert alert = new Alert(ERROR, "�������� ����� ����������� �����!");
                alert.show();
            }
        } else {
            Alert alert = new Alert(ERROR, "����� ����������� ����� ������������!");
            alert.show();
        }
    }
}