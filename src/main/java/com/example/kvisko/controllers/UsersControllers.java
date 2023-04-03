package com.example.kvisko.controllers;

import com.example.kvisko.Kvisko;
import com.example.kvisko.database.User;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

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

    @FXML
    private TableColumn<User, Integer> rank;

    private ObservableList<User> users = FXCollections.observableArrayList();

    private ArrayList<User> listOfUsers = Kvisko.getQuiz().getUsers();

    public void getTable() throws IOException {
        initialize();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/kvisko/users.fxml"));
        Parent usersTable = fxmlLoader.load();
        Kvisko.window = new Stage();
        Kvisko.window.setTitle("Lista igrača");
        Kvisko.window.setWidth(620);
        Kvisko.window.setHeight(400);
        Kvisko.window.setX(120);
        Kvisko.window.setY(120);
        Kvisko.window.initModality(Modality.APPLICATION_MODAL);
        Kvisko.window.setResizable(false);
        Kvisko.window.setScene(new Scene(usersTable));
        Kvisko.window.show();

    }

    public void initialize() {
        rank.setCellValueFactory(column -> {
            int index = users.indexOf(column.getValue()) + 1;
            return new SimpleIntegerProperty(index).asObject();
        });
        firstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        email.setCellValueFactory(new PropertyValueFactory<>("email"));
        points.setCellValueFactory(new PropertyValueFactory<>("points"));
        users.setAll(sortUserList());
        users.add(sortUserList().get(sortUserList().size() - 1));
        usersTableView.setItems(users);

        usersTableView.setRowFactory(tv -> new TableRow<User>() {
            @Override
            protected void updateItem(User user, boolean empty) {
                super.updateItem(user, empty);

                if (empty || user == null) {
                    setStyle("");
                } else {
                    int index = getIndex();
                    if (index >= 0 && index < 10) {
                        setStyle("-fx-background-color: #8ce58c;");
                    } else {
                        setStyle("");
                    }
                }
            }
        });
    }

    private List<User> sortUserList() {
        return listOfUsers.stream()
                .sorted(Comparator.comparingInt(User::getPoints).reversed()
                        .thenComparing(User::getFirstName)
                        .thenComparing(User::getLastName))
                .toList();

    }

}