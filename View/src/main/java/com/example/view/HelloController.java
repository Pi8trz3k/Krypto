package com.example.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;


public class HelloController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    private String keyInHex;
    private String keyInBin;

    public void generateKey(ActionEvent event) {
        keyInHex = "C5475BAFE74A3C1B";

    }
}