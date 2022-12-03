package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import main.Runner;
import util.validator.SceneChanger;


public class MainController {

    @FXML
    private Button cars;

    @FXML
    private Button orders;

    @FXML
    private Button myProfile;

    @FXML
    private Button allUsers;

    @FXML
    private Button orderByUser;

    @FXML
    private Button ordersByStatus;

    @FXML
    private Button statistics;

    @FXML
    private Button back;

    private final SceneChanger sceneChanger = SceneChanger.getInstance();

    @FXML
    private void initialize() {
        if (!"admin".equals(Runner.getStatus().getRoleName())) {
            allUsers.setVisible(false);
            orders.setVisible(false);
            statistics.setVisible(false);
        }
       if(!"seller".equals(Runner.getStatus().getRoleName())
       &&!"dealer".equals(Runner.getStatus().getRoleName())){
           ordersByStatus.setVisible(false);
        }
        if (!"user".equals(Runner.getStatus().getRoleName())) {
            orderByUser.setVisible(false);
        }
        cars.setOnAction(event -> {
            cars.getScene().getWindow().hide();
            sceneChanger.changeScene("/fxml/cars-list.fxml");
        });

        orders.setOnAction(event -> {
            orders.getScene().getWindow().hide();
            sceneChanger.changeScene("/fxml/order-list.fxml");
        });

        orderByUser.setOnAction(event -> {
            orderByUser.getScene().getWindow().hide();
            sceneChanger.changeScene("/fxml/orderByUser-list.fxml");
        });

        ordersByStatus.setOnAction(event -> {
            ordersByStatus.getScene().getWindow().hide();
            sceneChanger.changeScene("/fxml/orderByStatus-list.fxml");
        });

        myProfile.setOnAction(event -> {
            myProfile.getScene().getWindow().hide();
            sceneChanger.changeScene("/fxml/profile.fxml");
        });

        allUsers.setOnAction(event -> {
            allUsers.getScene().getWindow().hide();
            sceneChanger.changeScene("/fxml/users.fxml");
        });

        statistics.setOnAction(event -> {
            statistics.getScene().getWindow().hide();
            sceneChanger.changeScene("/fxml/statistics.fxml");
        });

        back.setOnAction(event -> {
            Runner.setStatus(null);
            Runner.setUserId(-1);
            Runner.setUsername(null);
            back.getScene().getWindow().hide();
            sceneChanger.changeScene("/fxml/index.fxml");
        });
    }
}