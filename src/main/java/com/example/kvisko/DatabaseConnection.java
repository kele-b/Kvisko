package com.example.kvisko;

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

            // create the questions table if it doesn't exist
            Statement statement = connection.createStatement();
            String createTableQuery =
                    "CREATE TABLE IF NOT EXISTS questions (id INT PRIMARY KEY AUTO_INCREMENT, " +
                            "question_text VARCHAR(1000), " +
                            "correct_answer VARCHAR(255)," +
                            "answer_1 VARCHAR(255)," +
                            "answer_2 VARCHAR(255)," +
                            "answer_3 VARCHAR(255))";
            statement.execute(createTableQuery);

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
