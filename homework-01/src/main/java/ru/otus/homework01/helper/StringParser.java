package ru.otus.homework01.helper;

import ru.otus.homework01.domain.Question;

public final class StringParser {
    public Question parseStringToQuestion(String inputString) {
        String[] strings = inputString.split(";");
        if (strings.length == 0) {
            throw new StringParserException("The inputString is empty");
        }
        Question question = new Question(strings[0]);

        if (strings.length == 2) {
            //Если в массиве всего два вхождения - то это вопрос с текстовым ответом, т.е. первое вхождение это вопрос
            //а второе это ответ на него
            //сохраним текстовый ответ
            question.setRightResponseString(strings[1]);
        } else {
            //Иначе это вопрос с авриантами ответов
            for (int i = 1; i < strings.length; i++) {
                String currentProcessingString = strings[i];
                if (currentProcessingString.startsWith("*")) {
                    question.setRightResponseIndex(i); //Зададим индекс правильного ответа
                    question.addResponse(currentProcessingString.substring(1)); //Обрежем префикс правильного ответа '*'
                } else {
                    question.addResponse(strings[i]);
                }
            }
        }
        return question;
    }
}
