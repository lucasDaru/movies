package com.texoit.movie.repository;

import com.texoit.movie.entity.Studio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudioRepository extends JpaRepository<Studio, Long> {
    Optional<Studio> findByName(String name);

}
