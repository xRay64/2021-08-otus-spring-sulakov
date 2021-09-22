package ru.otus.homework02.domain;

import ru.otus.homework02.exceptions.QuestionQuizRuntimeException;

import java.util.HashMap;
import java.util.Map;

public class Question {
    private final String questionText;
    private final Map<Integer, String> responses = new HashMap<>();
    private String questionTextTranslated;
    private final Map<Integer, String> responsesTranslated = new HashMap<>();
    private int rightResponseIndex;
    private String rightResponseString;
    private String rightResponseStringTranslated;

    public Question(String questionText) {
        this.questionText = questionText;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionTextTranslated(String questionTextTranslated) {
        this.questionTextTranslated = questionTextTranslated;
    }

    public String getQuestionTextTranslated() {
        return this.questionTextTranslated;
    }


    public void addResponse(String response) {
        if (response != null & !"".equals(response)) {
            responses.put(responses.size() + 1, response);
        } else {
            throw new QuestionQuizRuntimeException("Response can't be null");
        }
    }

    public void addTranslatedResponse(String translatedResponse) {
        if (translatedResponse != null & !"".equals(translatedResponse)) {
            responsesTranslated.put(responsesTranslated.size() + 1, translatedResponse);
        } else {
            throw new QuestionQuizRuntimeException("Response can't be null");
        }
    }

    public Map<Integer, String> getResponses() {
        return this.responses;
    }

    public Map<Integer, String> getResponsesTranslated() {
        return this.responsesTranslated;
    }

    public void setRightResponseIndex(int rightResponseIndex) {
        this.rightResponseIndex = rightResponseIndex;
    }

    public void setRightResponseString(String rightResponseString) {
        this.rightResponseString = rightResponseString;
    }

    public void setRightResponseStringTranslated(String rightResponseStringTranslated) {
        this.rightResponseStringTranslated = rightResponseStringTranslated;
    }

    public boolean needTextAnswer() {
        return rightResponseString != null;
    }

    public boolean checkResponseIndex(int responseIndex) {
        return rightResponseIndex == responseIndex;
    }

    public boolean checkResponseString(String responseString) {
        return rightResponseString.toLowerCase().contains(responseString.toLowerCase()) ||
                rightResponseStringTranslated.toLowerCase().contains(responseString.toLowerCase());
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("The question is:\n");
        sb.append(questionText);
        sb.append("\n");
        if (responses.size() > 0) {
            sb.append("And here are responses:\n");
            for (Map.Entry<Integer, String> responsePair : responses.entrySet()) {
                sb.append(String.format("%d. %s", responsePair.getKey(), responsePair.getValue()));
                sb.append("\n");
            }
        }
        return sb.toString();
    }
}
