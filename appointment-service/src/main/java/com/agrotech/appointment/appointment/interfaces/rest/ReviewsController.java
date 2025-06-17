package com.agrotech.appointment.appointment.interfaces.rest;

import com.agrotech.appointment.appointment.domain.model.commands.DeleteReviewCommand;
import com.agrotech.appointment.appointment.domain.model.entities.Review;
import com.agrotech.appointment.appointment.domain.model.queries.*;
import com.agrotech.appointment.appointment.domain.services.ReviewCommandService;
import com.agrotech.appointment.appointment.domain.services.ReviewQueryService;
import com.agrotech.appointment.appointment.interfaces.rest.resources.CreateReviewResource;
import com.agrotech.appointment.appointment.interfaces.rest.resources.ReviewResource;
import com.agrotech.appointment.appointment.interfaces.rest.resources.UpdateReviewResource;
import com.agrotech.appointment.appointment.interfaces.rest.transform.CreateReviewCommandFromResourceAssembler;
import com.agrotech.appointment.appointment.interfaces.rest.transform.ReviewResourceFromEntityAssembler;
import com.agrotech.appointment.appointment.interfaces.rest.transform.UpdateReviewCommandFromResourceAssembler;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

//@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
@RestController
@RequestMapping(value="api/v1/reviews", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Reviews", description = "Review Management Endpoints")
public class ReviewsController {
    private final ReviewCommandService reviewCommandService;
    private final ReviewQueryService reviewQueryService;

    public ReviewsController(ReviewCommandService reviewCommandService, ReviewQueryService reviewQueryService) {
        this.reviewCommandService = reviewCommandService;
        this.reviewQueryService = reviewQueryService;
    }

    @GetMapping
    public ResponseEntity<List<ReviewResource>> getReviews(
            @RequestParam(value = "advisorId", required = false) Long advisorId,
            @RequestParam(value = "farmerId", required = false) Long farmerId) {

        List<Review> reviews;

        if (advisorId != null && farmerId != null) {
            var getReviewByAdvisorIdAndFarmerIdQuery = new GetReviewByAdvisorIdAndFarmerIdQuery(advisorId, farmerId);
            var review = reviewQueryService.handle(getReviewByAdvisorIdAndFarmerIdQuery);
            if (review.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            reviews = List.of(review.get());
        } else if (advisorId != null) {
            var query = new GetReviewByAdvisorIdQuery(advisorId);
            reviews = reviewQueryService.handle(query);
        } else if (farmerId != null) {
            var query = new GetReviewByFarmerIdQuery(farmerId);
            reviews = reviewQueryService.handle(query);
        } else {
            var getAllReviewsQuery = new GetAllReviewsQuery();
            reviews = reviewQueryService.handle(getAllReviewsQuery);
        }

        var reviewResources = reviews.stream()
                .map(ReviewResourceFromEntityAssembler::toResourceFromEntity)
                .toList();

        return ResponseEntity.ok(reviewResources);
    }


    @GetMapping("/{id}")
    public ResponseEntity<ReviewResource> getReviewById(@PathVariable Long id) {
        var getReviewByIdQuery = new GetReviewByIdQuery(id);
        var review = reviewQueryService.handle(getReviewByIdQuery);
        if (review.isEmpty()) return ResponseEntity.notFound().build();
        var reviewResource = ReviewResourceFromEntityAssembler.toResourceFromEntity(review.get());
        return ResponseEntity.ok(reviewResource);
    }

    @PostMapping
    public ResponseEntity<ReviewResource> createReview(@RequestBody CreateReviewResource createReviewResource,
                                                       @Parameter(hidden = true) @RequestHeader("Authorization") String token) {
        var createReviewCommand = CreateReviewCommandFromResourceAssembler.toCommandFromResource(createReviewResource);
        Long reviewId = reviewCommandService.handle(createReviewCommand, token);
        var review = reviewQueryService.handle(new GetReviewByIdQuery(reviewId));
        if (review.isEmpty()) return ResponseEntity.badRequest().build();
        var reviewResource = ReviewResourceFromEntityAssembler.toResourceFromEntity(review.get());
        return new ResponseEntity<>(reviewResource, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReviewResource> updateReview(@PathVariable Long id,
                                                       @RequestBody UpdateReviewResource updateReviewResource,
                                                       @Parameter(hidden = true) @RequestHeader("Authorization") String token) {
        var updateReviewCommand = UpdateReviewCommandFromResourceAssembler.toCommandFromResource(id, updateReviewResource);
        Optional<Review> review = reviewCommandService.handle(updateReviewCommand, token);
        if (review.isEmpty()) return ResponseEntity.notFound().build();
        var reviewResource = ReviewResourceFromEntityAssembler.toResourceFromEntity(review.get());
        return ResponseEntity.ok(reviewResource);
    }

     @DeleteMapping("/{id}")
     public ResponseEntity<?> deleteReview(@PathVariable Long id) {
         var deleteReviewCommand = new DeleteReviewCommand(id);
         reviewCommandService.handle(deleteReviewCommand);
         return ResponseEntity.ok().body("Review with id " + id + " deleted successfully");
     }
}
