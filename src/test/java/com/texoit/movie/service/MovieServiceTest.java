package com.texoit.movie.service;

import com.texoit.movie.dto.ProducerIntervalDTO;
import com.texoit.movie.entity.Producer;
import com.texoit.movie.entity.Studio;
import com.texoit.movie.exception.MovieException;
import com.texoit.movie.fixture.Fixture;
import com.texoit.movie.repository.MovieRepository;
import com.texoit.movie.repository.ProducerRepository;
import com.texoit.movie.repository.StudioRepository;
import com.texoit.movie.service.impl.MovieServiceImpl;
import com.texoit.movie.service.impl.ProducerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MovieServiceTest {

    @Mock
    private MovieRepository movieRepository;

    @InjectMocks
    private MovieServiceImpl movieService;

    @InjectMocks
    private ProducerServiceImpl producerService;

    @Mock
    private StudioRepository studioRepository;

    @Mock
    private ProducerRepository producerRepository;

    @Test
    void testProcessCSVFile_EmptyFile() throws MovieException, IOException {
        InputStream csvFileStream = getClass().getClassLoader().getResourceAsStream("test.csv");
        MultipartFile multipartFile = new MockMultipartFile("test.csv", "test.csv", "text/csv", csvFileStream);

        // Test and Verification
        try {
            movieService.processCSVFile(multipartFile);
            fail("Expected MovieException to be thrown");
        } catch (MovieException e) {
            assertEquals(MovieException.Message.EMPTY_FILE.getMessage(), e.getMessage());
        }
    }

    @Test
    void testProcessCSVFile_InvalidTypeFile() throws IOException {
        // Mocking multipart file
        byte[] content = "Invalid type\ntest".getBytes();
        InputStream inputStream = new ByteArrayInputStream(content);
        MockMultipartFile file = new MockMultipartFile("test.txt", "test.txt", "text/csv", inputStream);

        // Test and Verification
        try {
            movieService.processCSVFile(file);
            fail("Expected MovieException to be thrown");
        } catch (MovieException e) {
            assertEquals(MovieException.Message.TYPE_OF_FILE.getMessage(), e.getMessage());
        }
    }

    @Test
    void testProcessCSVFile_InvalidFile() throws IOException {
        byte[] content = "Invalid file\ntest".getBytes();
        InputStream inputStream = new ByteArrayInputStream(content);
        MockMultipartFile file = new MockMultipartFile("test.csv", "test.csv", "text/csv", inputStream);

        try {
            movieService.processCSVFile(file);
            fail("Expected MovieException to be thrown");
        } catch (MovieException e) {
            assertEquals(MovieException.Message.ERROR_READ_FILE.getMessage(), e.getMessage());
        }
    }

    @Test
    void testReadFileCSVFile_ValidData() throws IOException, MovieException {
        Studio studio = new Studio();
        when(studioRepository.findByName(any())).thenReturn(Optional.of(studio));

        byte[] content = "year;title;studios;producers;winner\n2000;Movie Title;Studio1,Studio2;Producer1,Producer2;Yes".getBytes();
        InputStream inputStream = new ByteArrayInputStream(content);
        MockMultipartFile file = new MockMultipartFile("test.csv", "test.csv", "text/csv", inputStream);

        movieService.readFileCSVFile(file.getInputStream());
        verify(movieRepository, times(1)).save(any());
    }

    @Test
    void testReadFileCSVFile_InvalidData() throws IOException {
        byte[] content = "Invalid CSV Data\ntest".getBytes();
        InputStream inputStream = new ByteArrayInputStream(content);
        MockMultipartFile file = new MockMultipartFile("test.csv", "test.csv", "text/csv", inputStream);

        try {
            movieService.readFileCSVFile(file.getInputStream());
            fail("Expected MovieException to be thrown");
        } catch (MovieException e) {
            assertEquals(MovieException.Message.ERROR_READ_FILE.getMessage(), e.getMessage());
        }
    }

    @Test
    public void testFindProducerIntervalsMinMax() {

        Producer producer1 = Fixture.getProducerMin();
        Producer producer2 = Fixture.getProducerBetweenMedium();
        Producer producer3 = Fixture.getProducerMax();
        Producer producer4 = Fixture.getProducerBetweenLong();

        List<Producer> producers = Arrays.asList(producer1, producer2, producer3, producer4);

        when(producerRepository.findAllByMovieWinner()).thenReturn(producers);

        Map<String, List<ProducerIntervalDTO>> result = producerService.findProducerIntervalsMinMax();

        assertEquals(2, result.size());

        List<ProducerIntervalDTO> minIntervals = result.get("min");
        List<ProducerIntervalDTO> maxIntervals = result.get("max");

        assertEquals(false, minIntervals.isEmpty());
        assertEquals(false, maxIntervals.isEmpty());
    }

    @Test
    public void testFindProducerIntervalsMinMaxDetails() {
        ProducerIntervalDTO expectedMinInterval = new ProducerIntervalDTO("Some Producer Min", 1, 2017, 2018);
        ProducerIntervalDTO expectedMaxInterval = new ProducerIntervalDTO("Some Producer Max", 26, 1991, 2017);

        Producer producer1 = Fixture.getProducerMin();
        Producer producer2 = Fixture.getProducerBetweenMedium();
        Producer producer3 = Fixture.getProducerMax();
        Producer producer4 = Fixture.getProducerBetweenLong();

        List<Producer> producers = Arrays.asList(producer1, producer2, producer3, producer4);

        when(producerRepository.findAllByMovieWinner()).thenReturn(producers);

        Map<String, List<ProducerIntervalDTO>> result = producerService.findProducerIntervalsMinMax();

        assertEquals(2, result.size());

        List<ProducerIntervalDTO> minIntervals = result.get("min");
        assertEquals(1, minIntervals.size());
        assertEquals(expectedMinInterval, minIntervals.get(0));

        List<ProducerIntervalDTO> maxIntervals = result.get("max");
        assertEquals(1, maxIntervals.size());
        assertEquals(expectedMaxInterval, maxIntervals.get(0));
    }

}

