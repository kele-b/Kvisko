package com.example.kvisko.database;

import com.example.kvisko.Kvisko;
import javafx.application.Platform;
import javafx.scene.control.Alert;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DatabaseService {

    private Connection connection;

    DatabaseService(Connection connection) {
        this.connection = connection;
    }

    void addUser(User user) {
        boolean isFieldEmpty = "".equals(user.getFirstName()) ||
                "".equals(user.getLastName()) ||
                "".equals(user.getUsername()) ||
                "".equals(user.getPassword()) ||
                "".equals(user.getEmail());

        if (isFieldEmpty) {
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Greška");
                alert.setHeaderText("Pogrešan unos!");
                alert.setContentText("Sva polja su obavezna!");
                alert.show();
            });
            return;
        }
        String passwordRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";

        if(!user.getPassword().matches(passwordRegex)){
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Greška");
                alert.setHeaderText("Pogrešan unos!");
                alert.setContentText("Lozinka mora imati najmanje 8 karaktera, kombinaciju malih i velikih slova, 1 broj i 1 specijalni znak");
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
            insertStatment.setInt(6, user.getPoints());
            insertStatment.executeUpdate();
            insertStatment.close();

            Kvisko.setCurrentUser(user);
            Kvisko.registerForm.getScene().setRoot(Kvisko.home);

        } catch (SQLException e) {
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Greška!");
                alert.setHeaderText("Pogrešan unos!");
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

    void loginUser(String username, String password) throws SQLException {
        PreparedStatement getStatement = connection.prepareStatement(
                "SELECT * FROM users WHERE username = ? AND _password = ?"
        );
        getStatement.setString(1, username);
        getStatement.setString(2, password);

        ResultSet user = getStatement.executeQuery();

        if (user.next()) {
            User currentUser = new User(user.getString("first_name"),
                    user.getString("last_name"),
                    user.getString("username"),
                    user.getString("_password"),
                    user.getString("email"),
                    user.getInt("points"));
            Kvisko.setCurrentUser(currentUser);
            Kvisko.loginForm.getScene().setRoot(Kvisko.home);
            getStatement.close();
        } else {
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Pogrešan unos");
                alert.setContentText("Pogrešano korisničko ime ili lozinka!\n\nDa li imate registrovan nalog???");
                alert.show();
            });
        }
    }

    void getQuestions() throws SQLException {

        PreparedStatement getStatement = connection.prepareStatement(
                "SELECT * FROM questions ORDER BY RAND() LIMIT 15"
        );
        ResultSet resultSet = getStatement.executeQuery();
        ArrayList<Question> questions = new ArrayList<>();

        while (resultSet.next()) {
            ArrayList<String> incorrectAnswers = new ArrayList<>();
            incorrectAnswers.add(resultSet.getString("answer_1"));
            incorrectAnswers.add(resultSet.getString("answer_2"));
            incorrectAnswers.add(resultSet.getString("answer_3"));
            questions.add(new Question(
                    resultSet.getString("question_text"),
                    resultSet.getString("correct_answer"),
                    incorrectAnswers
            ));
        }
        Kvisko.getQuiz().setQuestions(questions);
        getStatement.close();
    }

    void savePoints(int pointsScored) throws SQLException {
        PreparedStatement insertStatement = connection.prepareStatement(
                "UPDATE users SET points = points + ? WHERE username IN (?)"
        );
        insertStatement.setInt(1, pointsScored);
        insertStatement.setString(2, Kvisko.getCurrentUser().getUsername());
        insertStatement.executeUpdate();
        insertStatement.close();
    }

    void getAllUsers() throws SQLException {
        PreparedStatement statement = connection.prepareStatement(
                "SELECT * FROM users"
        );
        ResultSet resultSet = statement.executeQuery();
        ArrayList<User> users = new ArrayList<>();

        while (resultSet.next()) {
            users.add(new User(
                    resultSet.getString("first_name"),
                    resultSet.getString("last_name"),
                    null, null,
                    resultSet.getString("email"),
                    resultSet.getInt("points")
            ));
        }
        Kvisko.getQuiz().setUsers(users);
        statement.close();
    }
}
