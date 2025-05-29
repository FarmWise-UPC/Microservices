package com.agrotech.appointment.appointment.interfaces.exceptions;

import com.agrotech.appointment.appointment.domain.exceptions.*;
import com.agrotech.appointment.shared.infrastructure.interfaces.responses.ErrorResponseDTO;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.stream.Collectors;

@ControllerAdvice
public class AppointmentExceptionsHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> handleGlobalException(Exception e) {
        ErrorResponseDTO errorResponse = new ErrorResponseDTO("Internal Server Error", "Ocurrió un error inesperado.");
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponseDTO> handleConstraintViolationException(ConstraintViolationException e) {
        String errorMessage = e.getConstraintViolations().stream()
                .map(violation -> violation.getPropertyPath() + ": " + violation.getMessage())
                .collect(Collectors.joining(", "));
        ErrorResponseDTO errorResponse = new ErrorResponseDTO("Constraint Violation", errorMessage);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

   @ExceptionHandler(AdvisorNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleAdvisorNotFoundException(AdvisorNotFoundException e) {
        ErrorResponseDTO errorResponse = new ErrorResponseDTO("Advisor Not Found", e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(FarmerNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleFarmerNotFoundException(FarmerNotFoundException e) {
        ErrorResponseDTO errorResponse = new ErrorResponseDTO("Farmer Not Found", e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AppointmentNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleAppointmentNotFoundException(AppointmentNotFoundException e) {
        ErrorResponseDTO errorResponse = new ErrorResponseDTO("Appointment Not Found", e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AvailableDateNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleAvailableDateNotFoundException(AvailableDateNotFoundException e) {
        ErrorResponseDTO errorResponse = new ErrorResponseDTO("Available Date Not Found", e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(SameAvailableDateException.class)
    public ResponseEntity<ErrorResponseDTO> handleSameAvailableDateException(SameAvailableDateException e) {
        ErrorResponseDTO errorResponse = new ErrorResponseDTO("Same Available Date", e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ReviewNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleReviewNotFoundException(ReviewNotFoundException e) {
        ErrorResponseDTO errorResponse = new ErrorResponseDTO("Review Not Found", e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidRatingException.class)
    public ResponseEntity<ErrorResponseDTO> handleInvalidRatingException(InvalidRatingException e) {
        ErrorResponseDTO errorResponse = new ErrorResponseDTO("Invalid Rating", e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidStatusException.class)
    public ResponseEntity<ErrorResponseDTO> handleInvalidStatusException(InvalidStatusException e) {
        ErrorResponseDTO errorResponse = new ErrorResponseDTO("Invalid Status", e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidDateException.class)
    public ResponseEntity<ErrorResponseDTO> handleInvalidDateException(InvalidDateException e) {
        ErrorResponseDTO errorResponse = new ErrorResponseDTO("Invalid Date", e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidTimeRangeException.class)
    public ResponseEntity<ErrorResponseDTO> handleInvalidTimeRangeException(InvalidTimeRangeException e) {
        ErrorResponseDTO errorResponse = new ErrorResponseDTO("Invalid Time Range", e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IncorrectTimeFormatException.class)
    public ResponseEntity<ErrorResponseDTO> handleIncorrectTimeFormatException(IncorrectTimeFormatException e) {
        ErrorResponseDTO errorResponse = new ErrorResponseDTO("Incorrect Time Format", e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ReviewAlreadyExistsException.class)
    public ResponseEntity<ErrorResponseDTO> handleReviewAlreadyExistsException(ReviewAlreadyExistsException e) {
        ErrorResponseDTO errorResponse = new ErrorResponseDTO("Review Already Exists", e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidAvailableDateException.class)
    public ResponseEntity<ErrorResponseDTO> handleInvalidAvailableDateException(InvalidAvailableDateException e) {
        ErrorResponseDTO errorResponse = new ErrorResponseDTO("Invalid Available Date", e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
