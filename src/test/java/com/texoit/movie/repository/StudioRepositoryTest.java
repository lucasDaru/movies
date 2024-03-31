package com.texoit.movie.repository;

import static com.texoit.movie.fixture.Fixture.getStudio;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import com.texoit.movie.entity.Studio;
import com.texoit.movie.service.impl.StudioServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class StudioRepositoryTest {

    @Mock
    private StudioRepository studioRepository;

    @InjectMocks
    private StudioServiceImpl studioService;

    @Test
    public void testFindByName() {
        String studioName = "Some Studio";
        Studio studio = getStudio();

        when(studioRepository.findByName(studioName)).thenReturn(Optional.of(studio));

        Optional<Studio> result = studioService.findStudioByName(studioName);
        assertTrue(result.isPresent());
        assertEquals(studioName, result.get().getName());
    }
}

