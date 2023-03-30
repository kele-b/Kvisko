package com.example.kvisko.database;

import com.example.kvisko.Kvisko;
import javafx.application.Platform;
import javafx.scene.control.Alert;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseService {

    private Connection connection;

    public DatabaseService(Connection connection) {
        this.connection = connection;
    }

    public synchronized void addUser(User user) {
        /*

        TO DO:
        Set text fields to empty

         */

        boolean isFieldEmpty = "".equals(user.getFirstName()) ||
                "".equals(user.getLastName()) ||
                "".equals(user.getUsername()) ||
                "".equals(user.getPassword()) ||
                "".equals(user.getEmail());

        if (isFieldEmpty) {
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Pogrešan unos!");
                alert.setContentText("Sva polja su obavezna!");
                alert.show();
            });
            return;
        }
        try {
            PreparedStatement insertStatment = connection.prepareStatement(
                    "INSERT INTO users (first_name, last_name, username, _password, email)" +
                            "VALUES (?, ?, ?, ?, ?)"
            );
            insertStatment.setString(1, user.getFirstName());
            insertStatment.setString(2, user.getLastName());
            insertStatment.setString(3, user.getUsername());
            insertStatment.setString(4, user.getPassword());
            insertStatment.setString(5, user.getEmail());
            insertStatment.executeUpdate();

            Kvisko.setCurrentUser(user);
            Kvisko.registerForm.getScene().setRoot(Kvisko.home);

        } catch (SQLException e) {
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Pogrešan unos!");
                alert.setContentText(e.getLocalizedMessage());
                if (e.getMessage().contains("users.username")) {
                    alert.setContentText("Username: " + user.getUsername() + " already exist!");
                }
                if (e.getMessage().contains("users.email")) {
                    alert.setContentText("Email: " + user.getEmail() + " already exist!");
                }
                alert.show();
            });
        }
    }

}
