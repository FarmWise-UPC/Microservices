package com.agrotech.post.post.interfaces.rest.transform;


import com.agrotech.post.post.domain.model.commands.UpdatePostCommand;
import com.agrotech.post.post.interfaces.rest.resources.UpdatePostResource;

public class UpdatePostCommandFromResourceAssembler {
    public static UpdatePostCommand toCommandFromResource(Long id, UpdatePostResource resource) {
        return new UpdatePostCommand(
                id,
                resource.title(),
                resource.description(),
                resource.image(),resource.advisorId());
    }
}
