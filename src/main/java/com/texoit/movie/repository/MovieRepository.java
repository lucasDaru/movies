package com.texoit.movie.repository;

import com.texoit.movie.dto.ProducerIntervalDTO;
import com.texoit.movie.entity.Movie;
import com.texoit.movie.entity.Producer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import java.util.List;


public interface MovieRepository extends JpaRepository<Movie, Long> {

    List<Movie> findAllByYear(Integer year);
    List<Movie> findAllByYearAndWinner(Integer year, Boolean winner);

    List<Movie> findByWinnerTrueOrderByYearAsc();

    default List<ProducerIntervalDTO> findProducerWithMaxInterval() {
        List<Movie> movies = findByWinnerTrueOrderByYearAsc();
        List<ProducerIntervalDTO> result = new ArrayList<>();

        String currentProducer = null;
        Integer previousYear = null;

        int maxInterval = 0;
        int currentInterval = 0;

        for (Movie movie : movies) {
            List<Producer> producers = movie.getProducers();
            for (Producer producer : producers) {
                if (currentProducer == null || !currentProducer.equals(producer.getName())) {
                    currentProducer = producer.getName();
                    previousYear = movie.getYear();
                } else {
                    currentInterval = movie.getYear() - previousYear;
                    if (currentInterval > maxInterval) {
                        maxInterval = currentInterval;
                        result.clear();
                        result.add(new ProducerIntervalDTO(currentProducer, maxInterval, previousYear, movie.getYear()));
                    } else if (currentInterval == maxInterval) {
                        result.add(new ProducerIntervalDTO(currentProducer, maxInterval, previousYear, movie.getYear()));
                    }
                    previousYear = movie.getYear();
                }
            }
        }
        return result;
    }

}
