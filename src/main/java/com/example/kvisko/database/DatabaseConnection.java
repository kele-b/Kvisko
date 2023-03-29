package com.example.kvisko.database;

import java.sql.*;
import java.util.List;

public class DatabaseConnection extends Thread {

    @Override
    public void run() {
        connectAndPopulateDB();
    }

    private synchronized void connectAndPopulateDB() {
        // establishing a connection with the database
        String url = "jdbc:mysql://localhost:3306/kvisko";
        String isCreated = "?createDatabaseIfNotExist=true";
        String username = "root";
        String password = "admin123";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url + isCreated, username, password);
            System.out.println("Connection is successful to the database " + url);

            // create the questions and user table if it doesn't exist
            Statement statement = connection.createStatement();
            String createQuestionTableQuery =
                    "CREATE TABLE IF NOT EXISTS questions (id INT PRIMARY KEY AUTO_INCREMENT, " +
                            "question_text VARCHAR(1000), " +
                            "correct_answer VARCHAR(255)," +
                            "answer_1 VARCHAR(255)," +
                            "answer_2 VARCHAR(255)," +
                            "answer_3 VARCHAR(255))";
            statement.execute(createQuestionTableQuery);

            String createUserTableQuery = "CREATE TABLE IF NOT EXISTS users(id INT PRIMARY KEY AUTO_INCREMENT, "+
                    "first_name VARCHAR(255), "+
                    "last_name VARCHAR(255), "+
                    "username VARCHAR(255) UNIQUE, "+
                    "_password VARCHAR(255), "+
                    "email VARCHAR(255) UNIQUE )";
            statement.execute(createUserTableQuery);

            // check if the questions table is already populated
            String selectQuery = "SELECT * FROM questions";
            ResultSet resultSet = statement.executeQuery(selectQuery);

            if (!resultSet.next()) {
                List<Question> questions = ReadXMLFile.getQuestions();

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

        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }
}
