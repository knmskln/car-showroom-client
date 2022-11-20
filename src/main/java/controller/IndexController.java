package controller;

import cooperation.ClientRequest;
import cooperation.ServerResponse;
import entity.User;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import main.Runner;
import util.MapParser;
import util.hasher.PasswordHashKeeper;
import util.validator.SceneChanger;
import util.validator.UserInformationValidator;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static javafx.scene.control.Alert.AlertType.ERROR;
import static javafx.scene.control.Alert.AlertType.INFORMATION;

public class IndexController {

    @FXML
    private Button signIn;

    @FXML
    private TextField login;

    @FXML
    private PasswordField password;

    @FXML
    private Button signUp;

    @FXML
    private Button forgotPassword;

    private UserInformationValidator validator = UserInformationValidator.getInstance();
    private PasswordHashKeeper hashKeeper = PasswordHashKeeper.getInstance();
    private MapParser parser = MapParser.getInstance();

    @FXML
    private void initialize() {
        signIn.setOnAction(event -> processSignIn());
        signUp.setOnAction(event -> {
            signIn.getScene().getWindow().hide();
            SceneChanger.getInstance().changeScene("/fxml/sign-up.fxml");
        });
        forgotPassword.setOnAction(event -> {
            forgotPassword.getScene().getWindow().hide();
            SceneChanger.getInstance().changeScene("/fxml/restore-password.fxml");
        });
    }

    private void processSignIn() {
        String usernameText = login.getText();
        String passwordText = password.getText();
        if (Objects.nonNull(usernameText) && Objects.nonNull(passwordText)) {
            String encoded = hashKeeper.generateHash(usernameText, passwordText);
            Map<String, Object> map = new HashMap<>();
            map.put("login", usernameText);
            map.put("password", encoded);
            Runner.sendData(new ClientRequest("signIn", map));
            ServerResponse response = Runner.getData();
            if (!response.isError()) {
                User user = parser.user((Map<String, Object>) response.getData().get("user"));
                Runner.setUserId(user.getUserId());
                Runner.setUsername(user.getUsername());
                Runner.setStatus(user.getUserRole());
                signIn.getScene().getWindow().hide();
                SceneChanger.getInstance().changeScene("/fxml/main.fxml");
                Alert alert = new Alert(INFORMATION, "Вход выполнен успешно!");
                alert.show();
            } else {
                Alert alert = new Alert(ERROR, response.getErrorMessage());
                alert.show();
            }
        } else {
            Alert alert = new Alert(ERROR, "Информация некорректна:\n" +
                    "1) логин должен состоять из \n" +
                    "2) пароль должен состоять из ");
            alert.show();
        }
    }
}