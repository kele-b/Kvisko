package com.example.kvisko.database;

import com.example.kvisko.Kvisko;
import javafx.application.Platform;
import javafx.scene.control.Alert;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseService {

    private Connection connection;

    public DatabaseService(Connection connection) {
        this.connection = connection;
    }

    public synchronized void addUser(User user) {
        /*

        TO DO:
        Set text fields to empty after successful registration
        Email and password validation

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
                    "INSERT INTO users (first_name, last_name, username, _password, email, points)" +
                            "VALUES (?, ?, ?, ?, ?, ?)"
            );
            insertStatment.setString(1, user.getFirstName());
            insertStatment.setString(2, user.getLastName());
            insertStatment.setString(3, user.getUsername());
            insertStatment.setString(4, user.getPassword());
            insertStatment.setString(5, user.getEmail());
            insertStatment.setInt(6,user.getPoints());
            insertStatment.executeUpdate();
            insertStatment.close();

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

    public synchronized void loginUser(String username, String password) throws SQLException {
        PreparedStatement getStatement = connection.prepareStatement(
                "SELECT * FROM users WHERE username = ? AND _password = ?"
        );
        getStatement.setString(1, username);
        getStatement.setString(2, password);

        ResultSet user = getStatement.executeQuery();

        if (user.next()){
            User currentUser = new User(user.getString("first_name"),
                    user.getString("last_name"),
                    user.getString("username"),
                    user.getString("_password"),
                    user.getString("email"),
                    user.getInt("points"));
            Kvisko.setCurrentUser(currentUser);
            Kvisko.loginForm.getScene().setRoot(Kvisko.home);
        }
        else {
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Pogrešan unos");
                alert.setContentText("Pogrešano korisničko ime ili lozinka!\n\nDa li imate registrovan nalog???");
                alert.show();
            });
        }
    }

}
