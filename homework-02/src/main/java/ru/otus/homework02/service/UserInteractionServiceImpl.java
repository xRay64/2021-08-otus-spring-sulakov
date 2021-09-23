package ru.otus.homework02.service;

import org.springframework.stereotype.Service;
import ru.otus.homework02.helper.IOService;

import java.io.BufferedReader;
import java.io.InputStreamReader;

@Service
public class UserInteractionServiceImpl implements UserInteractionService {
    private final IOService ioService;

    public UserInteractionServiceImpl(IOService ioService) {
        this.ioService = ioService;
    }

    @Override
    public void promptUser(String message) {
        ioService.println(message);
    }

    @Override
    public String askUserForString(String message) {
        ioService.println(message);
        String answer = ioService.readLine();
        return answer;
    }

    @Override
    public int askUserForInt(String message) {
        int answerInt = -1;
        ioService.println(message);
        try {
            answerInt = Integer.parseInt(ioService.readLine());
        } catch (NumberFormatException e) {
            ioService.println("Wrong number. Please enter a correct number of answer.");
            this.askUserForInt(message);
        }
        return answerInt;
    }
}
