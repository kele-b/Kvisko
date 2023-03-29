package com.example.kvisko.controllers;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class RegisterController {

    public TextField firstName;

    public TextField lastName;

    public TextField username;

    public PasswordField password;

    public TextField email;

    public void registerUser(ActionEvent actionEvent) {
        System.out.println(firstName.getText() +"\n"+
                lastName.getText()+"\n"+
                username.getText() +"\n"+
                password.getText() +"\n"+
                email.getText());
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText("All fields are required");
        alert.show();

    }
}
