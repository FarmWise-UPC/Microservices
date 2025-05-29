package com.agrotech.appointment.appointment.domain.services;

import com.agrotech.appointment.appointment.domain.model.commands.CreateAvailableDateCommand;
import com.agrotech.appointment.appointment.domain.model.commands.DeleteAvailableDateCommand;
import com.agrotech.appointment.appointment.domain.model.commands.UpdateAvailableDateCommand;
import com.agrotech.appointment.appointment.domain.model.commands.UpdateAvailableDateStatusCommand;
import com.agrotech.appointment.appointment.domain.model.entities.AvailableDate;

import java.util.Optional;

public interface AvailableDateCommandService {
    Long handle(CreateAvailableDateCommand command);
    Optional<AvailableDate> handle(UpdateAvailableDateCommand command);
    void handle(DeleteAvailableDateCommand command);
    void handle(UpdateAvailableDateStatusCommand command);
}
