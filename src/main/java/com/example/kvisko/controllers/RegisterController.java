package com.example.kvisko.controllers;

import com.example.kvisko.Kvisko;
import com.example.kvisko.database.User;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;


public class RegisterController {

    public TextField firstName;

    public TextField lastName;

    public TextField username;

    public PasswordField password;

    public TextField email;

    public Button backButton;

    public void registerUser(ActionEvent actionEvent) {
        User user = new User(firstName.getText(),
                lastName.getText(),
                username.getText(),
                password.getText(),
                email.getText());
        Kvisko.databaseConnection.registerUser(user);
    }

    public void backToLoginForm(ActionEvent actionEvent) {
        backButton.getScene().setRoot(Kvisko.loginForm);
    }
}
