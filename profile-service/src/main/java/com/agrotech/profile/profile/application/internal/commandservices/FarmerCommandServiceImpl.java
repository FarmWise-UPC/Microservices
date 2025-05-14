package com.agrotech.profile.profile.application.internal.commandservices;

import com.agrotech.iamservice.iam.domain.model.aggregates.User;
import com.agrotech.profile.profile.domain.model.commands.CreateFarmerCommand;
import com.agrotech.profile.profile.domain.model.commands.DeleteFarmerCommand;
import com.agrotech.profile.profile.domain.model.entities.Farmer;
import com.agrotech.profile.profile.domain.services.FarmerCommandService;
import com.agrotech.profile.profile.infrastructure.persistence.jpa.repositories.FarmerRepository;
import com.agrotech.profile.shared.domain.exceptions.FarmerNotFoundException;
import com.agrotech.profile.shared.domain.exceptions.UserNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class FarmerCommandServiceImpl implements FarmerCommandService {
    private final FarmerRepository farmerRepository;

    public FarmerCommandServiceImpl(FarmerRepository farmerRepository) {
        this.farmerRepository = farmerRepository;
    }

    @Override
    public Long handle(CreateFarmerCommand command, User user) {
        var sameUser = farmerRepository.findByUser_Id(command.userId());
        if (sameUser.isPresent()) {
            throw new UserNotFoundException(command.userId());
        }
        var farmer = new Farmer(command, user);
        farmerRepository.save(farmer);
        return farmer.getId();
    }

    @Override
    public void handle(DeleteFarmerCommand command) {
        var farmer = farmerRepository.findById(command.id());
        if (farmer.isEmpty()) {
            throw new FarmerNotFoundException(command.id());
        }
        farmerRepository.delete(farmer.get());
    }
}
