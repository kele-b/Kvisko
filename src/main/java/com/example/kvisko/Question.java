package com.example.kvisko;

import java.util.ArrayList;

public class Question {

    private String questionText;

    private String correctAnswer;

    private ArrayList<String> answers;

    public Question() {
    }

    public Question(String questionMessage, String correctAnswer, ArrayList<String> answers) {
        this.questionText = questionMessage;
        this.correctAnswer = correctAnswer;
        this.answers = answers;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public ArrayList<String> getAnswers() {
        return answers;
    }

    public void setAnswers(ArrayList<String> answers) {
        this.answers = answers;
    }

    @Override
    public String toString() {
        return "Question text: "+questionText+
                "\nCorrect answer: " + correctAnswer+
                "\nOther answers: "+ answers;
    }
}
