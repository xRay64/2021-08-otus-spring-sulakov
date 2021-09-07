package ru.otus.homework01.service;

import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Service
public class UserInteractionServiceImpl implements UserInteractionService{
    private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    @Override
    public void promptUser(String message) {
        System.out.println(message);
    }

    @Override
    public String askUserForString(String message) {
        String answer = null;
        System.out.println(message);
        try {
            answer = reader.readLine();
        } catch (IOException e) {
            System.out.println("Error while reading user answer");
            e.printStackTrace();
        }
        return answer;
    }

    @Override
    public int askUserForInt(String message) {
        int answerInt = -1;
        System.out.println(message);
        try {
            answerInt = Integer.parseInt(reader.readLine());
        } catch (NumberFormatException e) {
            System.out.println("Wrong number. Please enter a correct number of answer.");
            this.askUserForInt(message);
        } catch (IOException e) {
            System.out.println("Error while reading answer number.");
            e.printStackTrace();
        }
        return answerInt;
    }
}
