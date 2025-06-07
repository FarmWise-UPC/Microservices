package com.agrotech.shared.infrastructure.interfaces.exceptions;


import com.agrotech.shared.domain.exceptions.AdvisorNotFoundException;
import com.agrotech.shared.domain.exceptions.FarmerNotFoundException;
import com.agrotech.shared.domain.exceptions.UserNotFoundException;
import com.agrotech.shared.infrastructure.interfaces.responses.ErrorResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class GlobalExceptionsHandler {


    @ExceptionHandler(AdvisorNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleAdvisorNotFoundException(AdvisorNotFoundException e) {
        ErrorResponseDTO errorResponse = new ErrorResponseDTO("Advisor Not Found", e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> handleGlobalException(Exception e) {
        ErrorResponseDTO errorResponse = new ErrorResponseDTO("Internal Server Error", "Ocurrió un error inesperado.");
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleUserNotFoundException(UserNotFoundException e) {
        ErrorResponseDTO errorResponse = new ErrorResponseDTO("User Not Found", e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(FarmerNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleFarmerNotFoundException(FarmerNotFoundException e) {
        ErrorResponseDTO errorResponse = new ErrorResponseDTO("Farmer Not Found", e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
}
