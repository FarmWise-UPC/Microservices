package com.agrotech.profile.profile.interfaces.rest.transform;
import com.agrotech.profile.profile.domain.model.commands.CreateAdvisorCommand;
import com.agrotech.profile.profile.interfaces.rest.resources.CreateAdvisorResource;

public class CreateAdvisorCommandFromResource {
    public static CreateAdvisorCommand toCommandFromResource(CreateAdvisorResource resource) {
        return new CreateAdvisorCommand(resource.userId());
    }
}