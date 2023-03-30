package com.example.kvisko.controllers;

import com.example.kvisko.Kvisko;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;

public class LoginController {

    public Button newUserBtn;
    public void changeToRegistationForm(ActionEvent actionEvent) {
        newUserBtn.getScene().setRoot(Kvisko.registerForm);
    }
}
