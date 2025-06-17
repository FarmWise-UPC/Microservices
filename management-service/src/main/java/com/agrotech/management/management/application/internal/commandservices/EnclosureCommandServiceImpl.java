package com.agrotech.management.management.application.internal.commandservices;

import com.agrotech.management.management.domain.exceptions.EnclosureNotFoundException;
import com.agrotech.management.management.domain.exceptions.FarmerNotFoundException;
import com.agrotech.management.management.domain.model.aggregates.Enclosure;
import com.agrotech.management.management.domain.model.commands.CreateEnclosureCommand;
import com.agrotech.management.management.domain.model.commands.DeleteEnclosureCommand;
import com.agrotech.management.management.domain.model.commands.UpdateEnclosureCommand;
import com.agrotech.management.management.domain.services.EnclosureCommandService;
import com.agrotech.management.management.infrastructure.outbound.profile.rest.ProfileRestClient;
import com.agrotech.management.management.infrastructure.persitence.jpa.repositories.EnclosureRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EnclosureCommandServiceImpl implements EnclosureCommandService {

    private final EnclosureRepository enclosureRepository;
    private final ProfileRestClient profileRestClient;

    public EnclosureCommandServiceImpl(EnclosureRepository enclosureRepository, ProfileRestClient profileRestClient) {
        this.enclosureRepository = enclosureRepository;
        this.profileRestClient = profileRestClient;
    }

    @Override
    public Long handle(CreateEnclosureCommand command, String token) {
        var farmerOpt = profileRestClient.getFarmerById(command.farmerId(), token);
        if (farmerOpt.isEmpty()) {
            throw new FarmerNotFoundException(command.farmerId());
        }

        Enclosure enclosure = new Enclosure(command, command.farmerId());
        Enclosure savedEnclosure = enclosureRepository.save(enclosure);
        return savedEnclosure.getId();
    }

    @Override
    public Optional<Enclosure> handle(UpdateEnclosureCommand command) {
        var enclosureOpt = enclosureRepository.findById(command.enclosureId());
        if (enclosureOpt.isEmpty()) {
            throw new EnclosureNotFoundException(command.enclosureId());
        }

        var enclosureToUpdate = enclosureOpt.get();
        Enclosure updatedEnclosure = enclosureRepository.save(enclosureToUpdate.update(command));
        return Optional.of(updatedEnclosure);
    }

    @Override
    public void handle(DeleteEnclosureCommand command) {
        var enclosureOpt = enclosureRepository.findById(command.enclosureId());
        if (enclosureOpt.isEmpty()) {
            throw new EnclosureNotFoundException(command.enclosureId());
        }
        enclosureRepository.delete(enclosureOpt.get());
    }
}
