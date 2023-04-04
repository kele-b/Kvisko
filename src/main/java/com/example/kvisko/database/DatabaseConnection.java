package com.example.kvisko.database;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.util.List;
import java.util.Scanner;

public class DatabaseConnection extends Thread {

    private String url = "jdbc:mysql://localhost:3306/kvisko";

    private String isCreated = "?createDatabaseIfNotExist=true";

    private String username = "root";

    private String password = "admin123";

    private Connection connection;

    private User user;

    private boolean addingUser = false;

    private boolean loggingInUser = false;

    private boolean gettingQuestions = false;

    private boolean savingPoints = false;

    private boolean gettingUsers = false;

    private String usernameLogin;

    private String passwordLogin;

    private int points;

    @Override
    public void run() {
        connectAndPopulateDB();
        DatabaseService databaseService = new DatabaseService(connection);

        while (true) {
            synchronized (this) {
                if (addingUser) {
                    databaseService.addUser(user);
                    addingUser = false;
                }

                if (loggingInUser) {
                    try {
                        databaseService.loginUser(usernameLogin, passwordLogin);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    } finally {
                        loggingInUser = false;
                    }
                }

                if (gettingQuestions) {
                    try {
                        databaseService.getQuestions();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    } finally {
                        gettingQuestions = false;
                    }
                }

                if (savingPoints) {
                    try {
                        databaseService.savePoints(points);
                        databaseService.getQuestions();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    } finally {
                        savingPoints = false;
                    }
                }

                if (gettingUsers) {
                    try {
                        databaseService.getAllUsers();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    } finally {
                        gettingUsers = false;
                    }
                }

                try {
                    wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }

    }

    private synchronized void connectAndPopulateDB() {
        // establishing a connection with the database

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url + isCreated, username, password);
            System.out.println("Connection is successful to the database " + url);

            // create the questions table if it doesn't exist
            Statement statement = connection.createStatement();
            String createQuestionTableQuery =
                    "CREATE TABLE IF NOT EXISTS questions (id INT PRIMARY KEY AUTO_INCREMENT, " +
                            "question_text VARCHAR(1000), " +
                            "correct_answer VARCHAR(255)," +
                            "answer_1 VARCHAR(255)," +
                            "answer_2 VARCHAR(255)," +
                            "answer_3 VARCHAR(255))";
            statement.execute(createQuestionTableQuery);

            // check if the questions table is already populated
            String selectQuestionsQuery = "SELECT * FROM questions";
            ResultSet resultSet = statement.executeQuery(selectQuestionsQuery);

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

            // create the user table if it doesn't exist
            String createUserTableQuery = "CREATE TABLE IF NOT EXISTS users(id INT PRIMARY KEY AUTO_INCREMENT, " +
                    "first_name VARCHAR(255), " +
                    "last_name VARCHAR(255), " +
                    "username VARCHAR(255) UNIQUE, " +
                    "_password VARCHAR(255), " +
                    "email VARCHAR(255) UNIQUE," +
                    "points INT )";
            statement.execute(createUserTableQuery);

            //check if the user table is already populated
            String selectUsersQuery = "SELECT * FROM users";
            ResultSet usersResultSet = statement.executeQuery(selectUsersQuery);
            if(!usersResultSet.next()){
                Path path = Paths.get("src/main/resources/com/example/kvisko/users.sql");
                File file = path.toFile();
                String query = new String(Files.readAllBytes(file.toPath()), StandardCharsets.UTF_8);
                statement.execute(query);
            }

        } catch (SQLException | ClassNotFoundException | IOException ex) {
            ex.printStackTrace();
        }
    }

    public void registerUser(User user) {
        this.user = user;
        addingUser = true;
    }

    public void loginUser(String username, String password) {
        this.usernameLogin = username;
        this.passwordLogin = password;
        loggingInUser = true;
    }

    public void getQuestions() {
        gettingQuestions = true;
    }

    public void savePoints(int points) {
        this.points = points;
        savingPoints = true;
    }

    public void getUsers() {
        gettingUsers = true;
    }
}
