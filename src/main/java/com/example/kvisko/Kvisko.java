package com.example.kvisko;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.*;
import java.util.List;

public class Kvisko extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void init() {
        List<Question> questions = ReadXMLFile.getQuestions();

        // establishing a connection with the database
        String url = "jdbc:mysql://localhost:3306/mydatabase?createDatabaseIfNotExist=true";
        String username = "root";
        String password = "admin123";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url, username, password);
            System.out.println("Connection is successful to the database " + url);

            // create the questions table if it doesn't exist
            Statement createTableStatement = connection.createStatement();
            String createTableQuery =
                    "CREATE TABLE IF NOT EXISTS questions (id INT PRIMARY KEY AUTO_INCREMENT, " +
                            "question_text VARCHAR(1000), " +
                            "correct_answer VARCHAR(255)," +
                            "answer_1 VARCHAR(255)," +
                            "answer_2 VARCHAR(255)," +
                            "answer_3 VARCHAR(255))";
            createTableStatement.execute(createTableQuery);

            // check if the questions table is already populated
            Statement selectStatement = connection.createStatement();
            String selectQuery = "SELECT * FROM questions";
            ResultSet resultSet = selectStatement.executeQuery(selectQuery);

            if (!resultSet.next()) {

                // insert data into the question table if is not populated
                PreparedStatement insertStatement = connection.prepareStatement
                        ("INSERT INTO questions (question_text, correct_answer, answer_1, answer_2, answer_3) " +
                                "VALUES (?, ?, ?, ?, ?)");
                for (Question q : questions) {
                    insertStatement.setString(1, q.getQuestionText());
                    insertStatement.setString(2, q.getCorrectAnswer());
                    for (int i = 0; i < q.getAnswers().size(); i++) {
                        insertStatement.setString(i + 3, q.getAnswers().get(i));
                    }
                    insertStatement.executeUpdate();
                }
            }


        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        } catch (ClassNotFoundException ex) {
            throw new RuntimeException(ex);
        }

}

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Kvisko");
        stage.setHeight(600);
        stage.setWidth(800);
        stage.setResizable(false);


        VBox parent = new VBox(10);

        Label label = new Label("Welcome to KVISKO!");

        Button button = new Button("Click me");

        Timer timer = new Timer(label);
        List<Question> questions = ReadXMLFile.getQuestions();

        button.setOnAction(f -> {
            for (int i = 0; i < 5; i++) {
                System.out.println(questions.get(i));
            }
            Timer timer1 = timer;
            if (!Timer.isStarted) {
                timer1.start();
                Timer.isStarted = true;
            } else {
                timer1.restart();
            }

        });

        parent.getChildren().addAll(label, button);

        parent.setAlignment(Pos.CENTER);


        Scene scene = new Scene(parent);
        stage.setScene(scene);

        stage.show();


    }
}
