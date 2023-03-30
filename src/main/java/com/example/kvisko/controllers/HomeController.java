package com.example.kvisko.controllers;

import com.example.kvisko.Kvisko;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;

public class HomeController {

    public Button listUsers;

    public void listAllUsers(ActionEvent actionEvent) {
        listUsers.getScene().setRoot(Kvisko.loginForm);
    }

}
