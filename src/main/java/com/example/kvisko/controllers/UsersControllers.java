package com.example.kvisko.controllers;

import com.example.kvisko.Kvisko;
import com.example.kvisko.database.User;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class UsersControllers {

    @FXML
    private TableView<User> usersTableView;

    @FXML
    private TableColumn<User, String> firstName;

    @FXML
    private TableColumn<User, String> lastName;

    @FXML
    private TableColumn<User, String> email;

    @FXML
    private TableColumn<User, Integer> points;

    private ObservableList<User> users = FXCollections.observableArrayList();

    private ArrayList<User> listOfUsers = Kvisko.getQuiz().getUsers();

    public void getTable(){
        initialize();
        Platform.runLater(() -> {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/kvisko/users.fxml"));
                Parent usersTable = fxmlLoader.load();
                Stage window = new Stage();
                window.setTitle("Lista igrača");
                window.setWidth(620);
                window.setHeight(400);
                window.initModality(Modality.APPLICATION_MODAL);
                window.setScene(new Scene(usersTable));
                window.show();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        });
    }

    public void initialize() {
        firstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        email.setCellValueFactory(new PropertyValueFactory<>("email"));
        points.setCellValueFactory(new PropertyValueFactory<>("points"));
        users.setAll(sortUserList());
        usersTableView.setItems(users);
    }

    private List<User> sortUserList(){
       return listOfUsers.stream()
                .sorted(Comparator.comparingInt(User :: getPoints).reversed())
                .toList();

    }

//    public void addUser() {
//        User user = new User("John", "Doe", "john.doe@example.com", 100);
//        users.add(user);
//    }
//
//    public void removeUser() {
//        User selectedUser = usersTableView.getSelectionModel().getSelectedItem();
//        if (selectedUser != null) {
//            users.remove(selectedUser);
//        }
//    }


}