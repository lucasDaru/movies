package com.texoit.movie.repository;

import com.texoit.movie.dto.ProducerIntervalDTO;
import com.texoit.movie.entity.Movie;
import com.texoit.movie.entity.Producer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;


public interface MovieRepository extends JpaRepository<Movie, Long> {

    List<Movie> findAllByYear(Integer year);
    List<Movie> findAllByYearAndWinner(Integer year, Boolean winner);
    List<Movie> findByWinnerTrueOrderByYearAsc();
}
