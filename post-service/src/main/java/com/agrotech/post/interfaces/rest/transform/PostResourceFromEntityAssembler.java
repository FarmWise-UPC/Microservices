package com.agrotech.post.interfaces.rest.transform;


import com.agrotech.post.domain.model.aggregates.Post;
import com.agrotech.post.interfaces.rest.resources.PostResource;

public class PostResourceFromEntityAssembler {
    public static PostResource toResourceFromEntity(Post entity) {
        return new PostResource(
                entity.getId(),
                entity.getAdvisorId(),
                entity.getTitle(),
                entity.getDescription(),
                entity.getImage());
    }
}
