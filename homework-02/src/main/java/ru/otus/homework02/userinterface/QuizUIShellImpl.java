package ru.otus.homework02.userinterface;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.homework02.service.QuizService;

@ShellComponent
public class QuizUIShellImpl implements QuizUIShell {
    private final QuizService quizService;

    public QuizUIShellImpl(QuizService quizService) {
        this.quizService = quizService;
    }

    @Override
    @ShellMethod(key = "register-user", value = "User registration")
    public void registerUser() {
        quizService.registerUser();
    }

    @Override
    @ShellMethod(key = "start-quiz", value = "Start the game")
    public void startQuiz() {
        quizService.startQuiz();
    }
}
