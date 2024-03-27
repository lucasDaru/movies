package com.texoit.movie.service.impl;

import com.texoit.movie.dto.ProducerIntervalDTO;
import com.texoit.movie.entity.Movie;
import com.texoit.movie.entity.Producer;
import com.texoit.movie.repository.MovieRepository;
import com.texoit.movie.repository.ProducerRepository;
import com.texoit.movie.service.ProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProducerServiceImpl extends BaseServiceImpl<ProducerRepository, Producer>  implements ProducerService {

    @Autowired
    private MovieRepository movieRepository;

    public ProducerServiceImpl(ProducerRepository repository) {
        super(repository);
    }

    public Map<String, List<ProducerIntervalDTO>> findProducerIntervalsMinMax() {
        List<Producer> allByMovieWinner = repository.findAllByMovieWinner();
        Map<Long, ProducerIntervalDTO> minMapByInterval = new HashMap<>();
        Map<Long, ProducerIntervalDTO> maxMapByInterval = new HashMap<>();

        for (Producer producer : allByMovieWinner) {
            List<Movie> movies = producer.getMovies();
            if (movies.size() < 2) {
                continue;
            }

            movies.sort(Comparator.comparingInt(Movie::getYear));

            Integer previousWin = null;
            Integer followingWin = null;
            Long interval = null;

            for (Movie movie : movies) {
                Integer year = movie.getYear();
                if (followingWin == null || followingWin > year) {
                    previousWin = year;
                }

                if (previousWin != null && (followingWin == null || followingWin < year)) {
                    followingWin = year;
                }

                if (previousWin != null && followingWin != null) {
                    LocalDate previousWinLocalDate = LocalDate.of(previousWin, 1, 1);
                    LocalDate followingWinLocalDate = LocalDate.of(followingWin, 1, 1);
                    interval = ChronoUnit.YEARS.between(previousWinLocalDate, followingWinLocalDate);
                }
            }

            if (previousWin != null && followingWin != null && previousWin < followingWin) {
                ProducerIntervalDTO dto = new ProducerIntervalDTO();
                dto.setProducer(producer.getName());
                dto.setYearInterval(interval.intValue());
                dto.setPreviousWin(previousWin);
                dto.setFollowingWin(followingWin);

                if (!minMapByInterval.containsKey(interval) || previousWin < minMapByInterval.get(interval).getPreviousWin()) {
                    minMapByInterval.put(interval, dto);
                }

                if (!maxMapByInterval.containsKey(interval) || previousWin > maxMapByInterval.get(interval).getPreviousWin()) {
                    maxMapByInterval.put(interval, dto);
                }
            }
        }

        Map<String, List<ProducerIntervalDTO>> result = new HashMap<>();
        result.put("min", new ArrayList<>(minMapByInterval.values()));
        result.put("max", new ArrayList<>(maxMapByInterval.values()));
        return result;
    }


    public Map<String, List<ProducerIntervalDTO>> findProducerIntervals() {
        Map<String, List<ProducerIntervalDTO>> result = new HashMap<>();

        List<ProducerIntervalDTO> maxIntervals = mapToDTO(repository.findMaxIntervals());
        result.put("max", maxIntervals);

        List<ProducerIntervalDTO> minIntervals = mapToDTO(repository.findMinIntervals());
        result.put("min", minIntervals);

        return result;
    }

    private List<ProducerIntervalDTO> mapToDTO(List<Object[]> results) {
        return results.stream()
                .map(this::mapToObject)
                .toList();
    }

    private ProducerIntervalDTO mapToObject(Object[] result) {
        ProducerIntervalDTO dto = new ProducerIntervalDTO();
        dto.setProducer((String) result[0]);
        dto.setYearInterval((Integer) result[1]);
        dto.setPreviousWin((Integer) result[2]);
        dto.setFollowingWin((Integer) result[3]);
        return dto;
    }


}
