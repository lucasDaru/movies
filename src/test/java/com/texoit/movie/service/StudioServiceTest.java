package com.texoit.movie.service;

import static com.texoit.movie.fixture.Fixture.getStudio;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import com.texoit.movie.entity.Studio;
import com.texoit.movie.repository.StudioRepository;
import com.texoit.movie.service.impl.StudioServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class StudioServiceTest {

    @Mock
    private StudioRepository studioRepository;

    @InjectMocks
    private StudioServiceImpl studioService;

    @Test
    void testFindStudioByName_Exists() {
        String studioName = "Some Studio";
        Studio studio = getStudio();

        when(studioRepository.findByName(studioName)).thenReturn(Optional.of(studio));

        Optional<Studio> result = studioService.findStudioByName(studioName);

        assertTrue(result.isPresent());
        assertEquals(studio, result.get());
    }

    @Test
    void testFindStudioByName_NotFound() {
        String studioName = "NonExistentStudio";

        when(studioRepository.findByName(studioName)).thenReturn(Optional.empty());

        Optional<Studio> result = studioService.findStudioByName(studioName);

        assertFalse(result.isPresent());
    }
}

