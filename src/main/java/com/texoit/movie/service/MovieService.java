package com.texoit.movie.service;

import com.texoit.movie.dto.ProducerIntervalDTO;
import com.texoit.movie.entity.Movie;
import com.texoit.movie.entity.Producer;
import com.texoit.movie.exception.MovieException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public interface MovieService {
    void processCSVFile(MultipartFile multipartFile) throws MovieException;
    void readFileCSVFile(InputStream inputStream) throws MovieException;
    List<Movie> findMoviesByYear(Integer year);
    List<Movie> findMoviesByYearAndWinner(Integer year, boolean winner);
    List<Movie> findWinningMoviesOrderedByYear();
}
