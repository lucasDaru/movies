package com.texoit.movie.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface BaseService<EN> {

    EN save(EN entity);
    Optional<EN> findById(Long id);
    List<EN> findAll();
    Page<EN> findAll(Pageable pageable);
    void deleteById(Long id);
}
