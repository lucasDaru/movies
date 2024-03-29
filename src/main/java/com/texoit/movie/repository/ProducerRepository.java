package com.texoit.movie.repository;

import com.texoit.movie.dto.ProducerIntervalDTO;
import com.texoit.movie.entity.Producer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProducerRepository extends JpaRepository<Producer, Long> {

    Optional<Producer> findByName(String name);

    @Query("SELECT pd FROM Producer pd JOIN pd.movies mv WHERE mv.winner IS TRUE")
    List<Producer> findAllByMovieWinner();
}
