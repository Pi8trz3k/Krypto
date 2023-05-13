package com.example.des;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    private static Stage currentStage;

    public static Stage getCurrentStage() {
        return currentStage;
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("des.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Szyfrowanie DES");
        stage.setScene(scene);
        stage.show();
        currentStage = stage;
    }

    public static void main(String[] args) {
        launch();
    }
}