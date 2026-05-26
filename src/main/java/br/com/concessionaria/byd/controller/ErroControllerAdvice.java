package br.com.concessionaria.byd.controller;

import br.com.concessionaria.byd.exception.RecursoNaoEncontradoException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ErroControllerAdvice {

    @ExceptionHandler(RecursoNaoEncontradoException.class)
    public String tratarRecursoNaoEncontrado(RecursoNaoEncontradoException exception, Model model) {
        model.addAttribute("mensagemErro", exception.getMessage());
        return "erro";
    }
}
