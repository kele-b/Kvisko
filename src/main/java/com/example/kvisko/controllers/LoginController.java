package com.example.kvisko.controllers;

import com.example.kvisko.Kvisko;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class LoginController {

    public Button newUserBtn;

    public Button login;

    public TextField username;

    public TextField password;

    public void changeToRegistationForm(ActionEvent actionEvent) {
        newUserBtn.getScene().setRoot(Kvisko.registerForm);
    }

    public void loginUser(ActionEvent actionEvent) {
        String username = this.username.getText();
        String password = this.password.getText();
        Kvisko.databaseConnection.loginUser(username, password);
    }
}
