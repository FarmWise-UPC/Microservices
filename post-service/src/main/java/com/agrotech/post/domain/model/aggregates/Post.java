package com.agrotech.post.domain.model.aggregates;


import com.agrotech.post.domain.model.commands.CreatePostCommand;
import com.agrotech.post.domain.model.commands.UpdatePostCommand;
import com.agrotech.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;


@Entity
@Getter
public class Post extends AuditableAbstractAggregateRoot<Post> {
    @NotNull(message = "Title is required")
    @NotBlank(message = "Title cannot be blank")
    private String title;
    @NotNull(message = "Description is required")
    @NotBlank(message = "Description cannot be blank")
    @Column(columnDefinition = "TEXT")
    private String description;
    @NotNull(message = "Image is required")
    private String image;
    @Getter
    @NotNull(message = "Advisor is required")
    private Long advisorId;


    public Post() {
    }

    public Post(CreatePostCommand command){
        this.title = command.title();
        this.description = command.description();
        this.image = command.image();
        this.advisorId = command.advisorId();
    }

    public Post update(UpdatePostCommand command){
        this.title = command.title();
        this.description = command.description();
        this.image = command.image();
        this.advisorId = command.advisorId();
        return this;
    }

}
