package com.agrotech.management.management.infrastructure.persitence.jpa.repositories;

import com.agrotech.management.management.domain.model.aggregates.Enclosure;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EnclosureRepository extends JpaRepository<Enclosure, Long> {

    List<Enclosure> findAllByFarmerId(Long farmerId);
}
