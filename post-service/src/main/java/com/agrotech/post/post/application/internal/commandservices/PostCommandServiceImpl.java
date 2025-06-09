package com.agrotech.post.post.application.internal.commandservices;

import com.agrotech.post.post.application.internal.outboundservices.acl.AdvisorExternalService;
import com.agrotech.post.post.domain.exceptions.PostNotFoundException;
import com.agrotech.post.post.domain.model.aggregates.Post;
import com.agrotech.post.post.domain.model.commands.CreatePostCommand;
import com.agrotech.post.post.domain.model.commands.DeletePostCommand;
import com.agrotech.post.post.domain.model.commands.UpdatePostCommand;
import com.agrotech.post.post.domain.services.PostCommandService;
import com.agrotech.post.post.infrastructure.persistence.jpa.repositories.PostRepository;
import com.agrotech.post.shared.domain.exceptions.AdvisorNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PostCommandServiceImpl implements PostCommandService {
    private final PostRepository postRepository;
    private final AdvisorExternalService advisorExternalService;

    public PostCommandServiceImpl(PostRepository postRepository, AdvisorExternalService advisorExternalService) {
        this.postRepository = postRepository;
        this.advisorExternalService = advisorExternalService;
    }

    @Override
    public Long handle(CreatePostCommand command) {
        System.out.println("Iniciando creación de Post con advisorId=" + command.advisorId());
        System.out.println("Consultando existencia del advisor en el servicio externo...");

        var advisor = advisorExternalService.fetchAdvisorById(command.advisorId());

        System.out.println("Resultado de la consulta de advisor: " + advisor);

        if (advisor.isEmpty()) {
            System.out.println("Advisor con id=" + command.advisorId() + " no encontrado");
            throw new AdvisorNotFoundException(command.advisorId());
        }
        Post post = new Post(command);
        postRepository.save(post);
        System.out.println("Post creado exitosamente con id=" + post.getId());
        return post.getId();
    }

    @Override
    public Optional<Post> handle(UpdatePostCommand command) {
        var advisor = advisorExternalService.fetchAdvisorById(command.advisorId());
        if (advisor.isEmpty()) {
            throw new AdvisorNotFoundException(command.advisorId());
        }
        var post = postRepository.findById(command.id())
                .orElseThrow(() -> new PostNotFoundException(command.id()));
        Post updatedPost = postRepository.save(post.update(command));
        return Optional.of(updatedPost);
    }

    @Override
    public void handle(DeletePostCommand command) {
        var post = postRepository.findById(command.id())
                .orElseThrow(() -> new PostNotFoundException(command.id()));
        postRepository.delete(post);
    }
}