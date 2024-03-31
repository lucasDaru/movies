package com.texoit.movie.controller;

import com.texoit.movie.entity.Studio;
import com.texoit.movie.service.StudioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

import static com.texoit.movie.fixture.Fixture.getStudio;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(MovieController.class)
public class StudioControllerTest {

    private MockMvc mockMvc;

    @Mock
    private StudioService studioService;

    @InjectMocks
    private StudioController studioController;

    private Studio studio;

    @BeforeEach
    public void setUp() {
        studio = getStudio();

        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(studioController).build();
    }

    @Test
    public void testGetStudioByName() throws Exception {
        String studioName = "Some Studio";

        when(studioService.findStudioByName(studioName)).thenReturn(Optional.of(studio));

        mockMvc.perform(get("/api/studios/{name}", studioName))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value(studioName));

        verify(studioService, times(1)).findStudioByName(studioName);
        verifyNoMoreInteractions(studioService);
    }

    @Test
    public void testGetStudioByName_NotFound() throws Exception {
        String studioName = "Some Studio";

        when(studioService.findStudioByName(studioName)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/studios/{name}", studioName))
                .andExpect(status().isNotFound());

        verify(studioService, times(1)).findStudioByName(studioName);
        verifyNoMoreInteractions(studioService);
    }
}

