package com.texoit.movie.service;

import com.texoit.movie.dto.ProducerIntervalDTO;
import com.texoit.movie.service.impl.MovieServiceImpl;
import com.texoit.movie.service.impl.ProducerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@SpringBootTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class ProducerServiceTest {

    @Autowired
    private ProducerServiceImpl producerService;

    @Autowired
    private MovieServiceImpl movieService;

    @Autowired
    private ResourceLoader resourceLoader;

    @BeforeEach
    public void setup() {
        runMethod();
    }

    @Test
    public void testFindProducerIntervalsMinMaxSize() {
        Map<String, List<ProducerIntervalDTO>> result = producerService.findProducerIntervalsMinMax();

        assertEquals(1, result.get("max").size());
        assertEquals(5, result.get("min").size());
    }


    @Test
    public void testFindProducerIntervalsMinMaxDetails(){
        Map<String, List<ProducerIntervalDTO>> result = producerService.findProducerIntervalsMinMax();

        assertNotNull(result);

        assertEquals("Joel Silver", result.get("min").get(0).getProducer());
        assertEquals(1, result.get("min").get(0).getYearInterval());
        assertEquals(1990, result.get("min").get(0).getPreviousWin());
        assertEquals(1991, result.get("min").get(0).getFollowingWin());

        assertEquals("Allan Carr", result.get("max").get(0).getProducer());
        assertEquals(42, result.get("max").get(0).getYearInterval());
        assertEquals(1980, result.get("max").get(0).getPreviousWin());
        assertEquals(2022, result.get("max").get(0).getFollowingWin());
    }

    private void runMethod() {
        try {
            Resource resource = resourceLoader.getResource("classpath:/movies.csv");
            movieService.readFileCSVFile(resource.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
