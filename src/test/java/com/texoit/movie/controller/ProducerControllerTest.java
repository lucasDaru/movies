package com.texoit.movie.controller;

import com.texoit.movie.dto.ProducerIntervalDTO;
import com.texoit.movie.service.ProducerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.Map;

import static com.texoit.movie.fixture.Fixture.getProducerMinMax;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProducerController.class)
public class ProducerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private ProducerService producerService;

    @InjectMocks
    private ProducerController producerController;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(producerController).build();
    }

    @Test
    public void testFindProducerIntervalsMinMax() throws Exception {
        Map<String, List<ProducerIntervalDTO>> intervals = getProducerMinMax();

        when(producerService.findProducerIntervalsMinMax()).thenReturn(intervals);

        mockMvc.perform(get("/api/producer/min-max"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.min").exists())
                .andExpect(jsonPath("$.max").exists())
                .andExpect(jsonPath("$.min").isArray())
                .andExpect(jsonPath("$.max").isArray())
                .andReturn();
    }
}

