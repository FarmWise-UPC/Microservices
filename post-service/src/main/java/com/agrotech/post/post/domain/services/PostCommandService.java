package com.agrotech.post.post.domain.services;



import com.agrotech.post.post.domain.model.aggregates.Post;
import com.agrotech.post.post.domain.model.commands.CreatePostCommand;
import com.agrotech.post.post.domain.model.commands.DeletePostCommand;
import com.agrotech.post.post.domain.model.commands.UpdatePostCommand;

import java.util.Optional;

public interface PostCommandService {
    Long handle(CreatePostCommand command);
    Optional<Post> handle(UpdatePostCommand command);
    void handle(DeletePostCommand command);
}
