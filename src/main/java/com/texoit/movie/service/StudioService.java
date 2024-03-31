package com.texoit.movie.service;

import com.texoit.movie.entity.Studio;

import java.util.Optional;

public interface StudioService {
    Optional<Studio> findStudioByName(String name);
}
