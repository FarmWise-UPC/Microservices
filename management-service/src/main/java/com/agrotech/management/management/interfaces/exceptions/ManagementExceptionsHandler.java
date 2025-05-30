package com.agrotech.management.management.interfaces.exceptions;

import com.agrotech.management.management.domain.exceptions.AnimalNotFoundException;
import com.agrotech.management.management.domain.exceptions.EnclosureNotFoundException;
import com.agrotech.management.management.domain.exceptions.IncorrectHealthStatusException;
import com.agrotech.management.shared.infrastructure.interfaces.responses.ErrorResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ManagementExceptionsHandler {
    @ExceptionHandler(AnimalNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleAnimalNotFoundException(AnimalNotFoundException e) {
        ErrorResponseDTO errorResponse = new ErrorResponseDTO("Animal Not Found", e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EnclosureNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleEnclosureNotFoundException(EnclosureNotFoundException e) {
        ErrorResponseDTO errorResponse = new ErrorResponseDTO("Enclosure Not Found", e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IncorrectHealthStatusException.class)
    public ResponseEntity<ErrorResponseDTO> handleIncorrectHealthStatusException(IncorrectHealthStatusException e) {
        ErrorResponseDTO errorResponse = new ErrorResponseDTO("Incorrect Health Status", e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
