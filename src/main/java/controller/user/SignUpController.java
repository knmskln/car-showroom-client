package controller.user;

import cooperation.ClientRequest;
import cooperation.ServerResponse;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import main.Runner;
import util.validator.SceneChanger;
import util.hasher.PasswordHashKeeper;
import util.validator.UserInformationValidator;

import java.util.HashMap;
import java.util.Map;

import static javafx.scene.control.Alert.AlertType.ERROR;
import static javafx.scene.control.Alert.AlertType.INFORMATION;

public class SignUpController {

    private UserInformationValidator validator = UserInformationValidator.getInstance();
    private PasswordHashKeeper keeper = PasswordHashKeeper.getInstance();

    @FXML
    private TextField name;

    @FXML
    private TextField login;

    @FXML
    private Button signUp;

    @FXML
    private TextField surname;

    @FXML
    private TextField email;

    @FXML
    private PasswordField password;

    @FXML
    private PasswordField confirmedPassword;

    @FXML
    private Button index;

    @FXML
    private Button back;

    @FXML
    private Button exit;


    @FXML
    private void initialize() {
        signUp.setOnAction(event -> processSignUp());
        index.setOnAction(event -> {
            index.getScene().getWindow().hide();
            SceneChanger.getInstance().changeScene("/fxml/index.fxml");
        });
        back.setOnAction(event -> {
            back.getScene().getWindow().hide();
            SceneChanger.getInstance().changeScene("/fxml/index.fxml");
        });
        exit.setOnAction(event -> exit.getScene().getWindow().hide());
    }

    private void processSignUp() {
        String loginText = login.getText();
        String nameText = name.getText();
        String surnameText = surname.getText();
        String emailText = email.getText();
        String passwordText = password.getText();
        String confirmedPasswordText = confirmedPassword.getText();
        if (validator.validate(loginText, nameText, surnameText, emailText, passwordText, confirmedPasswordText)) {
            String encoded = keeper.generateHash(loginText, passwordText);
            Map<String, Object> map = new HashMap<>();
            map.put("login", loginText);
            map.put("name", nameText);
            map.put("surname", surnameText);
            map.put("email", emailText);
            map.put("password", encoded);
            Runner.sendData(new ClientRequest("signUp", map));
            ServerResponse response = Runner.getData();
            if (!response.isError()) {
                signUp.getScene().getWindow().hide();
                SceneChanger.getInstance().changeScene("/fxml/index.fxml");
                Alert alert = new Alert(INFORMATION, "Регистрация прошла успешно! Теперь вы можете войти в систему.");
                alert.show();
            } else {
                Alert alert = new Alert(ERROR, response.getErrorMessage());
                alert.show();
            }
        } else {
            Alert alert = new Alert(ERROR, "Информация некорректна:\n" +
                    "1) логин должен состоять из 4-13 символов: латинских букв и нижних подчеркиваний (_)\n" +
                    "2) имя и фамилия должны состоять из 2-30 символов\n" +
                    "3) пароль должен состоять из 4-28 символов: цифр\n" +
                    "4) пароли должны совпадать");
            alert.show();
        }
    }
}