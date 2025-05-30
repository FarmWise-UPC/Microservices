package com.agrotech.profile.profile.application.internal.commandservices;

import com.agrotech.iamservice.iam.domain.model.aggregates.User;
import com.agrotech.profile.profile.domain.exceptions.UserAlreadyUsedException;
import com.agrotech.profile.profile.domain.model.commands.CreateAdvisorCommand;
import com.agrotech.profile.profile.domain.model.commands.DeleteAdvisorCommand;
import com.agrotech.profile.profile.domain.model.commands.UpdateAdvisorCommand;
import com.agrotech.profile.profile.domain.model.entities.Advisor;
import com.agrotech.profile.profile.domain.services.AdvisorCommandService;
import com.agrotech.profile.profile.infrastructure.persistence.jpa.repositories.AdvisorRepository;
import com.agrotech.profile.shared.domain.exceptions.AdvisorNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AdvisorCommandServiceImpl implements AdvisorCommandService {
    private final AdvisorRepository advisorRepository;

    public AdvisorCommandServiceImpl(AdvisorRepository advisorRepository) {
        this.advisorRepository = advisorRepository;
    }

    @Override
    public Long handle(CreateAdvisorCommand command, User user) {
        var sameUser = advisorRepository.findByUserId(command.userId());
        if (sameUser.isPresent()) {
            throw new UserAlreadyUsedException(command.userId());
        }
        Advisor advisor = new Advisor(command.userId());
        advisorRepository.save(advisor);
        return advisor.getId();
    }

    @Override
    public Optional<Advisor> handle(UpdateAdvisorCommand command) {
        var advisor = advisorRepository.findById(command.id());
        if (advisor.isEmpty()) {
            return Optional.empty();
        }
        var advisorToUpdate = advisor.get();
        Advisor updatedAdvisor = advisorRepository.save(advisorToUpdate.update(command));
        return Optional.of(updatedAdvisor);
    }

    @Override
    public void handle(DeleteAdvisorCommand command) {
        var advisor = advisorRepository.findById(command.id());
        if (advisor.isEmpty()) {
            throw new AdvisorNotFoundException(command.id());
        }
        advisorRepository.delete(advisor.get());
    }
}
