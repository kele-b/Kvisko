package com.example.kvisko.controllers;

import com.example.kvisko.Kvisko;
import com.example.kvisko.database.User;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;

public class RegisterController {

    public TextField firstName;

    public TextField lastName;

    public TextField username;

    public PasswordField password;

    public TextField email;

    public void registerUser(ActionEvent actionEvent) {
        User user = new User(firstName.getText(),
                lastName.getText(),
                username.getText(),
                password.getText(),
                email.getText());
        Kvisko.databaseConnection.registerUser(user);

        if(Kvisko.getCurrentUser()!=null){
            email.getScene().setRoot(Kvisko.home);
        }
    }
}
