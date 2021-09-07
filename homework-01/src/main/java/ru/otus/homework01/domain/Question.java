package ru.otus.homework01.domain;

import java.util.HashMap;
import java.util.Map;

public class Question {
    private final String questionText;
    private Map<Integer, String> responses = new HashMap<>();
    private int rightResponseIndex;
    private String rightResponseString;

    public Question(String questionText) {
        this.questionText = questionText;
    }


    public String getQuestionText() {
        return questionText;
    }

    public void addResponse(String response) {
        if (response != null) {
            responses.put(responses.size() + 1, response);
        } else {
            throw new QuestionExcepton("Response can't be null");
        }
    }

    public void setRightResponseIndex(int rightResponseIndex) {
        this.rightResponseIndex = rightResponseIndex;
    }

    public void setRightResponseString(String rightResponseString) {
        this.rightResponseString = rightResponseString;
    }

    public boolean needTextAnswer() {
        return rightResponseString != null;
    }
    public boolean checkResponseIndex(int responseIndex) {
        return rightResponseIndex == responseIndex;
    }

    public boolean checkResponseString(String responseString) {
        return responseString.toLowerCase().contains(rightResponseString.toLowerCase());
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("The question is:\n");
        sb.append(questionText + "\n");
        if (responses.size() > 0) {
            sb.append("And here are responses:\n");
            for (Map.Entry<Integer, String> responsePair : responses.entrySet()) {
                sb.append(String.format("%d. %s", responsePair.getKey(), responsePair.getValue()) + "\n");
            }
        }
        return sb.toString();
    }
}
