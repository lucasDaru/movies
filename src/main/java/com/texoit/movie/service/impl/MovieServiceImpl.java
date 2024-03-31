package com.texoit.movie.service.impl;


import com.texoit.movie.entity.Movie;
import com.texoit.movie.entity.Producer;
import com.texoit.movie.entity.Studio;
import com.texoit.movie.exception.MovieException;
import com.texoit.movie.repository.MovieRepository;
import com.texoit.movie.repository.ProducerRepository;
import com.texoit.movie.repository.StudioRepository;
import com.texoit.movie.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

@Service
public class MovieServiceImpl implements MovieService {

    public static final String SPLIT = "(?<!\\S)and(?!\\S)";

    private static final long MAX_FILE_SIZE_BYTES = 10 * 1024 * 1024;

    @Autowired
    @Lazy
    private MovieRepository movieRepository;

    @Autowired
    @Lazy
    private ProducerRepository producerRepository;

    @Autowired
    @Lazy
    private StudioRepository studioRepository;

    @Transactional
    public void processCSVFile(MultipartFile multipartFile) throws MovieException {
        validateFile(multipartFile);
        try {
            readFileCSVFile(multipartFile.getInputStream());
        } catch (IOException e) {
            throw new MovieException(MovieException.Message.ERROR_READ_FILE);
        }
    }

    @Transactional
    public void readFileCSVFile(InputStream inputStream) throws MovieException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
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
                    String[] studioNames = data[2].replaceAll(SPLIT, ",").split(",");
                    String[] producerNames = data[3].replaceAll(SPLIT, ",").split(",");

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

                    processStudios(studioNames, movie);

                    processProducers(producerNames, movie);

                    movieRepository.save(movie);
                } else {
                    throw new MovieException(MovieException.Message.ERROR_READ_FILE);
                }
            }
        } catch (IOException e) {
            throw new MovieException(MovieException.Message.ERROR_READ_FILE);
        }
    }

    private void processProducers(String[] producerNames, Movie movie) {
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
    }

    private void processStudios(String[] studioNames, Movie movie) {
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
    }

    private void validateFile(MultipartFile multipartFile) throws MovieException {
        if (multipartFile.isEmpty()) {
            throw new MovieException(MovieException.Message.EMPTY_FILE);
        }
        if (multipartFile.getSize() > MAX_FILE_SIZE_BYTES) {
            throw new MovieException(MovieException.Message.FILE_TOO_LARGE);
        }

        if (!multipartFile.getOriginalFilename().toLowerCase().endsWith(".csv")) {
            throw new MovieException(MovieException.Message.TYPE_OF_FILE);
        }
    }

    public List<Movie> findMoviesByYear(Integer year) {
        return movieRepository.findAllByYear(year);
    }

    public List<Movie> findMoviesByYearAndWinner(Integer year, boolean winner) {
        return movieRepository.findAllByYearAndWinner(year, winner);
    }

    public List<Movie> findWinningMoviesOrderedByYear() {
        return movieRepository.findByWinnerTrueOrderByYearAsc();
    }

}
