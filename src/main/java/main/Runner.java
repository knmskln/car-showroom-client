package main;

import com.fasterxml.jackson.databind.ObjectMapper;
import cooperation.ClientRequest;
import cooperation.ServerResponse;
import entity.Role;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Runner extends Application implements Runnable {

    private static Socket socket;
    private static int userId;
    private static String login;
    private static Role userRole;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/index.fxml"));
        primaryStage.setTitle("Client Application");
        primaryStage.setScene(new Scene(root, 700, 300));
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        new Thread(new Runner()).start();
        launch(args);
    }

    @Override
    public void run() {
        try {
            socket = new Socket("127.0.0.1", 5000);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void sendData(ClientRequest data) {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(data);
            outputStream.writeObject(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ServerResponse getData() {
        try {
            ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
            ObjectMapper mapper = new ObjectMapper();
            String json = (String) inputStream.readObject();
            System.out.println(json);
            return mapper.readValue(json, ServerResponse.class);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        throw new RuntimeException();
    }

    public static int getUserId() {
        return userId;
    }

    public static void setUserId(int userId) {
        Runner.userId = userId;
    }

    public static String getUsername() {
        return login;
    }

    public static void setUsername(String login) {
        Runner.login = login;
    }

    public static Role getStatus() {
        return userRole;
    }

    public static void setStatus(Role userRole) {
        Runner.userRole = userRole;
    }
}