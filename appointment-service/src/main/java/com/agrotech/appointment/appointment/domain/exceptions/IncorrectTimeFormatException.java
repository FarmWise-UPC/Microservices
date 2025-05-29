package com.agrotech.appointment.appointment.domain.exceptions;

public class IncorrectTimeFormatException extends RuntimeException {
  public IncorrectTimeFormatException(String startTime, String endTime) {
    super("Incorrect time format for: " + startTime + " or " + endTime + ". Please use HH:mm format.");
  }
}
