package com.agrotech.appointment.appointment.infrastructure.persistence.jpa.repositories;

import com.agrotech.appointment.appointment.domain.model.entities.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByAdvisorId(Long advisorId);
    List<Review> findByFarmerId(Long farmerId);
    Optional<Review> findByAdvisorIdAndFarmerId(Long advisorId, Long farmerId);
}
