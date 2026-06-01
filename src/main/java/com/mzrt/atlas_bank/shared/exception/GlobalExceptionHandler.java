package com.mzrt.atlas_bank.shared.exception;

import com.mzrt.atlas_bank.account.exception.AccountNotFoundException;
import com.mzrt.atlas_bank.transaction.exception.AccountNotActiveException;
import com.mzrt.atlas_bank.transaction.exception.InsufficientFundsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AccountNotFoundException.class)
    public ProblemDetail handleAccountNotFoundException(AccountNotFoundException e) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.NOT_FOUND, e.getMessage()
        );
        problemDetail.setTitle("Cuenta no encontrada");

        return problemDetail;
    }

    @ExceptionHandler(InsufficientFundsException.class)
    public ProblemDetail handleInsufficientFundsException(InsufficientFundsException e) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatusCode.valueOf(422), e.getMessage()
        );
        problemDetail.setTitle("Fondos insuficientes para realizar la operacion");
        return problemDetail;
    }

    @ExceptionHandler(AccountNotActiveException.class)
    public ProblemDetail handleAccountNotActiveException(AccountNotActiveException e) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatusCode.valueOf(422) , e.getMessage()
        );
        problemDetail.setTitle("Cuenta no active para realizar la operacion");
        return problemDetail;
    }

    @ExceptionHandler(Exception.class)
    public ProblemDetail handleException(Exception e) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.INTERNAL_SERVER_ERROR, "Error interno del servidor"
        );
        problemDetail.setTitle("Error interno");
        return problemDetail;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);

        List<String> errors = new ArrayList<>();

        e.getFieldErrors().
                forEach(error -> errors.add(error.getField() + ": " + error.getDefaultMessage()));

        e.getGlobalErrors().forEach(error -> errors.add(error.getDefaultMessage()));

        problemDetail.setProperty("errors", errors);
        problemDetail.setTitle("Error de validacion");
        return problemDetail;
    }
}
