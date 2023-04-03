package com.example.kvisko.controllers;

import com.example.kvisko.Kvisko;
import com.example.kvisko.database.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;


public class RegisterController {

    @FXML
    private TextField firstName;

    @FXML
    private TextField lastName;

    @FXML
    private TextField username;

    @FXML
    private PasswordField password;

    @FXML
    private TextField email;

    @FXML
    private Button backButton;

    public void registerUser(ActionEvent actionEvent) {
        User user = new User(firstName.getText(),
                lastName.getText(),
                username.getText(),
                password.getText(),
                email.getText());
        synchronized (Kvisko.databaseConnection) {
            Kvisko.databaseConnection.registerUser(user);
            Kvisko.databaseConnection.getQuestions();
            Kvisko.databaseConnection.notify();
        }
    }

    public void backToLoginForm(ActionEvent actionEvent) {
        backButton.getScene().setRoot(Kvisko.loginForm);
    }
}
