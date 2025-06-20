package com.agrotech.post.application.internal.commandservices;

import com.agrotech.post.post.application.internal.commandservices.PostCommandServiceImpl;
import com.agrotech.post.post.domain.model.aggregates.Post;
import com.agrotech.post.post.domain.model.commands.CreatePostCommand;
import com.agrotech.post.post.infrastructure.persistence.jpa.repositories.PostRepository;
import com.agrotech.post.post.application.internal.outboundservices.acl.AdvisorExternalService;
import com.agrotech.post.post.infrastructure.outbound.user.dtos.AdvisorView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class PostCommandServiceImplTest {

    @Mock
    private PostRepository postRepository;

    @Mock
    private AdvisorExternalService advisorExternalService;

    @InjectMocks
    private PostCommandServiceImpl postCommandService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void test_handle_create_post_command_with_valid_advisor_returns_post_id() throws Exception {
        // Arrange
        Long advisorId = 1L;
        Long userId = 6L;
        BigDecimal rating = BigDecimal.valueOf(4.5);
        Long expectedPostId = 10L;
        String title = "Test Title";
        String description = "Test Description";
        String image = "test-image.jpg";
        String token = "valid-token";

        CreatePostCommand command = new CreatePostCommand(advisorId, title, description, image);
        AdvisorView advisorView = new AdvisorView(advisorId, userId, rating);


        when(advisorExternalService.fetchAdvisorById(advisorId, token)).thenReturn(Optional.of(advisorView));

        doAnswer(invocation -> {
            Post saved = invocation.getArgument(0);
            Field idField = null;
            Class<?> clazz = saved.getClass();
            while (clazz != null) {
                try {
                    idField = clazz.getDeclaredField("id");
                    break;
                } catch (NoSuchFieldException e) {
                    clazz = clazz.getSuperclass();
                }
            }
            if (idField == null) throw new RuntimeException("Campo 'id' no encontrado");
            idField.setAccessible(true);
            idField.set(saved, expectedPostId);
            return saved;
        }).when(postRepository).save(any(Post.class));

        // Act
        Long actualPostId = postCommandService.handle(command, token);

        // Assert
        assertEquals(expectedPostId, actualPostId);
        verify(advisorExternalService).fetchAdvisorById(advisorId, token);
        verify(postRepository).save(any(Post.class));
    }

}