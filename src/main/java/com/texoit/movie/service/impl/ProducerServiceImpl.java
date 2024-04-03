package com.texoit.movie.service.impl;

import com.texoit.movie.dto.ProducerIntervalDTO;
import com.texoit.movie.entity.Movie;
import com.texoit.movie.entity.Producer;
import com.texoit.movie.repository.MovieRepository;
import com.texoit.movie.repository.ProducerRepository;
import com.texoit.movie.service.ProducerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional
public class ProducerServiceImpl extends BaseServiceImpl<ProducerRepository, Producer> implements ProducerService {

    @Autowired
    private MovieRepository movieRepository;

    public ProducerServiceImpl(ProducerRepository repository) {
        super(repository);
    }

    public Map<String, List<ProducerIntervalDTO>> findProducerIntervalsMinMax() {
        List<Producer> allByMovieWinner = repository.findAllByMovieWinner();
        MultiValueMap<Long, ProducerIntervalDTO> mapByInterval = new LinkedMultiValueMap<>();

        for (Producer producer : allByMovieWinner) {
            List<Movie> movies = producer.getMovies().stream()
                    .filter(Movie::getWinner)
                    .sorted(Comparator.comparingInt(Movie::getYear))
                    .collect(Collectors.toList());

            int numMovies = movies.size();
            if (numMovies < 2) {
                continue;
            }

            if (numMovies > 2) {
                for (int i = 0; i < numMovies - 1; i++) {
                    Integer startYear = movies.get(i).getYear();
                    Integer endYear = movies.get(i + 1).getYear();
                    int movieInterval = endYear - startYear;

                    if (startYear.equals(endYear)) {
                        continue;
                    }

                    ProducerIntervalDTO dto = new ProducerIntervalDTO();
                    dto.setProducer(producer.getName());
                    dto.setYearInterval(movieInterval);
                    dto.setPreviousWin(startYear);
                    dto.setFollowingWin(endYear);

                    mapByInterval.add((long) movieInterval, dto);
                }
            } else {
                Integer startYear = movies.get(0).getYear();
                Integer endYear = movies.get(1).getYear();
                int movieInterval = endYear - startYear;

                if (startYear.equals(endYear)) {
                    continue;
                }

                ProducerIntervalDTO dto = new ProducerIntervalDTO();
                dto.setProducer(producer.getName());
                dto.setYearInterval(movieInterval);
                dto.setPreviousWin(startYear);
                dto.setFollowingWin(endYear);

                mapByInterval.add((long) movieInterval, dto);
            }
        }

        Map<String, List<ProducerIntervalDTO>> result = new HashMap<>();

        result.put("min", mapByInterval.get(Collections.min(mapByInterval.keySet())));
        result.put("max", mapByInterval.get(Collections.max(mapByInterval.keySet())));

        log.info("Result: {}", result);
        return result;
    }

    private List<ProducerIntervalDTO> mapToDTO(List<Object[]> results) {
        return results.stream().map(this::mapToObject).toList();
    }

    private ProducerIntervalDTO mapToObject(Object[] result) {
        ProducerIntervalDTO dto = new ProducerIntervalDTO();
        dto.setProducer((String) result[0]);
        dto.setYearInterval((Integer) result[1]);
        dto.setPreviousWin((Integer) result[2]);
        dto.setFollowingWin((Integer) result[3]);
        return dto;
    }

    public Optional<Producer> findProducerByName(String name) {
        return repository.findByName(name);
    }

    public List<Producer> findAllProducersWithWinningMovies() {
        return repository.findAllByMovieWinner();
    }
}
