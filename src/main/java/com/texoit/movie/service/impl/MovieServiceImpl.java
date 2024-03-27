package com.texoit.movie.service.impl;


import com.texoit.movie.dto.ProducerIntervalDTO;
import com.texoit.movie.entity.Movie;
import com.texoit.movie.entity.Producer;
import com.texoit.movie.entity.Studio;
import com.texoit.movie.repository.MovieRepository;
import com.texoit.movie.repository.ProducerRepository;
import com.texoit.movie.repository.StudioRepository;
import com.texoit.movie.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MovieServiceImpl implements MovieService {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private ProducerRepository producerRepository;

    @Autowired
    private StudioRepository studioRepository;

    @Transactional
    public void processCSVFile(MultipartFile file) throws IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            String line;
            boolean firstLine = true;
            while ((line = br.readLine()) != null) {
                if (firstLine) {
                    firstLine = false;
                    continue;
                }
                String[] data = line.split(";");
                if (data.length >= 4) {

                    int year = Integer.parseInt(data[0]);
                    String title = data[1];
                    String[] studioNames = data[2].replaceAll("(?<!\\S)and(?!\\S)", ",").split(",");
                    String[] producerNames = data[3].replaceAll("(?<!\\S)and(?!\\S)", ",").split(",");

                    Movie movie = new Movie();
                    movie.setYear(year);
                    movie.setTitle(title);
                    movie.setWinner(false);

                    if (data.length == 5) {
                        String winner = data[4];
                        if (!winner.trim().isEmpty()) {
                            movie.setWinner(winner.toLowerCase().equals("yes"));
                        }
                    }

                    for (String studioName : studioNames) {
                        if (studioName.trim().isEmpty())
                            continue;

                        Studio studio = studioRepository.findByName(studioName.trim()).orElse(null);
                        if (studio == null) {
                            studio = new Studio();
                            studio.setName(studioName.trim());
                            studioRepository.save(studio);
                        }
                        movie.getStudios().add(studio);
                    }

                    for (String producerName : producerNames) {
                        if (producerName.trim().isEmpty())
                            continue;

                        Producer producer = producerRepository.findByName(producerName.trim()).orElse(null);
                        if (producer == null) {
                            producer = new Producer();
                            producer.setName(producerName.trim());
                            producerRepository.save(producer);
                        }
                        movie.getProducers().add(producer);
                    }

                    movieRepository.save(movie);
                }
            }
        }
    }

    public List<ProducerIntervalDTO> findProducersWithMaxInterval() {
       return movieRepository.findProducerWithMaxInterval();
    }

}
