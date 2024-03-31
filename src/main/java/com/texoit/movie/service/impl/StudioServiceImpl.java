package com.texoit.movie.service.impl;

import java.util.Optional;

import com.texoit.movie.entity.Studio;
import com.texoit.movie.repository.StudioRepository;
import com.texoit.movie.service.StudioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudioServiceImpl implements StudioService {

    @Autowired
    private StudioRepository studioRepository;

    @Override
    public Optional<Studio> findStudioByName(String name) {
        return studioRepository.findByName(name);
    }
}
