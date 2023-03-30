package com.example.kvisko.quiz;

import com.example.kvisko.database.Question;

import java.util.ArrayList;

public class Quiz {

    private ArrayList<Question> questions = new ArrayList<>();

    public Quiz(ArrayList<Question> questions) {
        this.questions = questions;
    }

    public ArrayList<Question> getQuestions() {
        return questions;
    }
}
