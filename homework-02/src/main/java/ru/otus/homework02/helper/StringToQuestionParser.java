package ru.otus.homework02.helper;

import org.springframework.stereotype.Component;
import ru.otus.homework02.domain.Question;

@Component
public final class StringToQuestionParser {
    public Question parseStringToQuestion(String inputString) {
        String[] strings = inputString.split(";");
        if (strings[0].isEmpty()) {
            throw new StringParserException("The inputString is empty");
        }

        Question question = new Question(strings[0]);
        try {
            //Сохрним переведенный текст вопроса
            if (strings[4] != null & !"".equals(strings[4])) {
                question.setQuestionTextTranslated(strings[4]);
            }

            if (strings[8] != null & !"textAnswer".equals(strings[8])) {
                //Если указан номер паривльного варианта ответа - то это вопрос с несколькими вариантами ответов
                question.setRightResponseIndex(Integer.parseInt(strings[8]));
                //добавим варианты ответов без перевода
                for (int i = 1; i < 4; i++) {
                    question.addResponse(strings[i]);
                }
                //добавим переведенные варианты ответов
                for (int j = 5; j < 8; j++) {
                    question.addTranslatedResponse(strings[j]);
                }
            } else if (strings[8] != null & "textAnswer".equals(strings[8])) {
                //если номер ответа не укзан - то это вопрос с ответом, требующим ввода строки от пользователя
                question.setRightResponseString(strings[1]);
                question.setRightResponseStringTranslated(strings[5]);
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new StringParserException("Strings in CSV file dose not match correct format");
        }

        return question;
    }
}
