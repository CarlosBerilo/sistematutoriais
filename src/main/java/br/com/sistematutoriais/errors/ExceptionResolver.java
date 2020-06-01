package br.com.sistematutoriais.errors;

import java.util.HashMap;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;

@RestControllerAdvice
public class ExceptionResolver {

    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    public HashMap<String, String> handleNoHandlerFound(NoHandlerFoundException e, WebRequest request) {
        HashMap<String, String> response = new HashMap<>();
        response.put("code", "404"); 
        response.put("message", "Pagina nao encontrata.");
        return response;
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    public HashMap<String, String> handleNoHandler(Exception e, WebRequest request) {
    	HashMap<String, String> response = new HashMap<>();
    	response.put("code", "500"); 
    	response.put("message", "Dados incorretos, verifique e tente novamente.");
    	return response;
    }
    
}
