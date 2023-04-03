package com.example.kvisko.controllers;

import com.example.kvisko.Kvisko;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class LoginController {

    @FXML
    private Button newUserBtn;

    @FXML
    private Button login;

    @FXML
    private TextField username;

    @FXML
    private TextField password;

    public void changeToRegistationForm(ActionEvent actionEvent) {
        newUserBtn.getScene().setRoot(Kvisko.registerForm);
    }

    public void loginUser(ActionEvent actionEvent) {
        String username = this.username.getText();
        String password = this.password.getText();
        synchronized (Kvisko.databaseConnection) {
            Kvisko.databaseConnection.loginUser(username, password);
            Kvisko.databaseConnection.getQuestions();
           Kvisko.databaseConnection.notify();
        }
    }
}
