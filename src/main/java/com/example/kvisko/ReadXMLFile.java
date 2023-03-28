package com.example.kvisko;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ReadXMLFile {

    private static List<Question> questions = new ArrayList<>();

    public static List<Question> getQuestions() {
        if(questions.isEmpty())
            readXMLFile();
        return questions;
    }

    public static void readXMLFile() {

        try {
            File xmlFile = new File("C:\\FX\\kvisko\\src\\main\\resources\\com\\example\\kvisko\\questions.xml");
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(xmlFile);
            document.getDocumentElement().normalize();
            NodeList questionsNodes = document.getElementsByTagName("question");

            for (int i = 0; i < questionsNodes.getLength(); i++) {
                Question question = new Question();
                Node questionNode = questionsNodes.item(i);
                if (questionNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element questionElement = (Element) questionNode;

                    String questionText = questionElement.getElementsByTagName("text").item(0).getTextContent();
                    question.setQuestionText(questionText);

                    ArrayList<String> answers = new ArrayList<>();
                    NodeList answerElement = questionElement.getElementsByTagName("answer");
                    for (int j = 0; j < answerElement.getLength(); j++) {
                        String answer = answerElement.item(j).getTextContent();
                        if (answer.contains("*")) {
                            question.setCorrectAnswer(answer.replace("*",""));
                        } else {
                            answers.add(answer);
                        }
                    }
                    question.setAnswers(answers);
                    questions.add(question);
                }
            }

        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
    }
}
