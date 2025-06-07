package com.agrotech.post.interfaces.rest.transform;


import com.agrotech.post.domain.model.commands.CreatePostCommand;
import com.agrotech.post.interfaces.rest.resources.CreatePostResource;

public class CreatePostCommandFromResourceAssembler {
    public static CreatePostCommand toCommandFromResource(CreatePostResource resource) {
        return new CreatePostCommand(
                resource.advisorId(),
                resource.title(),
                resource.description(),
                resource.image());
    }
}
