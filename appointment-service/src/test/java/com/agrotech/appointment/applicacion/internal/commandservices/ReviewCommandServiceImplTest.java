package com.agrotech.appointment.applicacion.internal.commandservices;

import com.agrotech.appointment.appointment.application.internal.commandservices.ReviewCommandServiceImpl;
import com.agrotech.appointment.appointment.application.internal.outboundservices.profile.ExternalProfileService;
import com.agrotech.appointment.appointment.domain.model.commands.CreateReviewCommand;
import com.agrotech.appointment.appointment.domain.model.entities.Review;
import com.agrotech.appointment.appointment.infrastructure.outbound.profile.dtos.AdvisorView;
import com.agrotech.appointment.appointment.infrastructure.outbound.profile.dtos.FarmerView;
import com.agrotech.appointment.appointment.infrastructure.persistence.jpa.repositories.ReviewRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ReviewCommandServiceImplTest {

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private ExternalProfileService externalProfilesService;

    @InjectMocks
    private ReviewCommandServiceImpl reviewCommandService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void test_handle_create_review_command_success() {
        // Arrange
        Long advisorId = 1L;
        Long farmerId = 2L;
        Long advisorUserId = 10L;
        Long farmerUserId = 20L;
        String comment = "Great advice";
        Integer rating = 5;
        Long reviewId = 1L;
        String token = "valid-token";

        CreateReviewCommand command = new CreateReviewCommand(advisorId, farmerId, comment, rating);

        AdvisorView advisorView = new AdvisorView(advisorId, advisorUserId, BigDecimal.valueOf(4.5));
        FarmerView farmerView = new FarmerView(farmerId, farmerUserId);

        Review review = mock(Review.class);
        when(review.getId()).thenReturn(reviewId);
        when(review.getAdvisorId()).thenReturn(advisorId);

        when(externalProfilesService.fetchAdvisorById(advisorId, token)).thenReturn(Optional.of(advisorView));
        when(externalProfilesService.fetchFarmerById(farmerId, token)).thenReturn(Optional.of(farmerView));
        when(reviewRepository.findByAdvisorIdAndFarmerId(advisorId, farmerId)).thenReturn(Optional.empty());
        when(reviewRepository.save(any(Review.class))).thenReturn(review);
        when(reviewRepository.findByAdvisorId(advisorId)).thenReturn(java.util.List.of(review));
        doNothing().when(externalProfilesService).updateRating(eq(advisorId), any(BigDecimal.class), eq(token));

        // Act
        Long result = reviewCommandService.handle(command, token);

        // Assert
        assertEquals(reviewId, result);

        verify(reviewRepository).findByAdvisorIdAndFarmerId(advisorId, farmerId);
        verify(externalProfilesService).fetchAdvisorById(advisorId, token);
        verify(externalProfilesService).fetchFarmerById(farmerId, token);
        verify(reviewRepository).save(any(Review.class));
        verify(reviewRepository).findByAdvisorId(advisorId);
        verify(externalProfilesService).updateRating(eq(advisorId), any(BigDecimal.class), eq(token));
    }
}