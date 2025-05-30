package com.agrotech.profile.profile.infrastructure.persistence.jpa.repositories;

import com.agrotech.profile.profile.domain.model.aggregates.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
    Optional<Profile> findByUserId(Long userId);
    @Query("SELECT p FROM Profile p WHERE p.occupation IS NOT NULL AND p.occupation <> ''")
    List<Profile> findAllAdvisorProfiles();
}
