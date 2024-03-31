package com.texoit.movie.repository;

import com.texoit.movie.entity.Producer;
import com.texoit.movie.service.impl.ProducerServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.texoit.movie.fixture.Fixture.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProducerRepositoryTest {

    @Mock
    private ProducerRepository producerRepository;

    @InjectMocks
    private ProducerServiceImpl producerService;

    @Test
    public void testFindByName() {
        String producerName = "Some Producer";
        Producer producer = getProducer();

        when(producerRepository.findByName(producerName)).thenReturn(Optional.of(producer));

        Optional<Producer> foundProducer = producerService.findProducerByName(producerName);

        assertEquals(Optional.of(producer), foundProducer);
    }

    @Test
    public void testFindAllByMovieWinner() {
        Producer producer1 = getProducerMin();
        Producer producer2 = getProducerMax();
        List<Producer> producers = Arrays.asList(producer1, producer2);

        when(producerRepository.findAllByMovieWinner()).thenReturn(producers);

        List<Producer> foundProducers = producerService.findAllProducersWithWinningMovies();

        assertEquals(producers.size(), foundProducers.size());
        assertEquals(producers, foundProducers);
    }
}

