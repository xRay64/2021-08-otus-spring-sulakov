package ru.otus.library.controllers.advices;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class NPEAdvice {
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NullPointerException.class)
    public ModelAndView handleNPE(NullPointerException e) {
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("errMessage", e.getMessage());
        return modelAndView;
    }
}