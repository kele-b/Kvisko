package com.example.kvisko.quiz;

import com.example.kvisko.database.Question;
import com.example.kvisko.database.User;

import java.util.ArrayList;

public class Quiz {

    private ArrayList<Question> questions = new ArrayList<>();

    private ArrayList<User> users = new ArrayList<>();

    public Quiz() {
    }

    public ArrayList<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(ArrayList<Question> questions) {
        this.questions = questions;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }
}
