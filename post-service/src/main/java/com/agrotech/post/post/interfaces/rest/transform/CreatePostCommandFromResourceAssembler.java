package com.agrotech.post.post.interfaces.rest.transform;


import com.agrotech.post.post.domain.model.commands.CreatePostCommand;
import com.agrotech.post.post.interfaces.rest.resources.CreatePostResource;

public class CreatePostCommandFromResourceAssembler {
    public static CreatePostCommand toCommandFromResource(CreatePostResource resource) {
        return new CreatePostCommand(
                resource.advisorId(),
                resource.title(),
                resource.description(),
                resource.image());
    }
}
