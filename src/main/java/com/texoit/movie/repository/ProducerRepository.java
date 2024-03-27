package com.texoit.movie.repository;

import com.texoit.movie.dto.ProducerIntervalDTO;
import com.texoit.movie.entity.Producer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProducerRepository extends JpaRepository<Producer, Long> {

    Optional<Producer> findByName(String name);
    @Query(value = "WITH movie_producer_titles AS ( " +
            "    SELECT " +
            "        m.release_year AS release_year, " +
            "        p.name AS producer, " +
            "        mv.title AS title " +
            "    FROM " +
            "        movie_producer mp " +
            "        JOIN producer p ON mp.id_producer = p.id_producer " +
            "        JOIN movie m ON mp.id_movie = m.id_movie " +
            "        JOIN movie mv ON mv.id_movie = mp.id_movie " +
            "    WHERE " +
            "        m.flg_winner = true " +
            "), " +
            "movie_win_intervals AS ( " +
            "    SELECT " +
            "        producer, " +
            "        release_year AS previous_win_year, " +
            "        LEAD(release_year) OVER (PARTITION BY producer ORDER BY release_year) AS following_win_year, " +
            "        LAG(title) OVER (ORDER BY release_year) AS previous_movie, " +
            "        title AS following_movie " +
            "    FROM " +
            "        movie_producer_titles " +
            ") " +
            "SELECT " +
            "    producer, " +
            "    MAX(following_win_year - previous_win_year) AS yearInterval, " +
            "    previous_win_year, " +
            "    following_win_year " +
            "FROM " +
            "    movie_win_intervals " +
            "WHERE " +
            "    following_win_year IS NOT NULL " +
            "GROUP BY " +
            "    producer, " +
            "    previous_win_year, " +
            "    following_win_year " +
            "HAVING " +
            "    yearInterval = ( " +
            "        SELECT MAX(following_win_year - previous_win_year) " +
            "        FROM movie_win_intervals " +
            "        WHERE following_win_year IS NOT NULL " +
            "    ) " +
            "ORDER BY " +
            "    yearInterval DESC;", nativeQuery = true)
    List<Object[]> findMaxIntervals();

    @Query(value = "WITH movie_producer_titles AS ( " +
            "    SELECT " +
            "        m.release_year AS release_year, " +
            "        p.name AS producer, " +
            "        mv.title AS title " +
            "    FROM " +
            "        movie_producer mp " +
            "        JOIN producer p ON mp.id_producer = p.id_producer " +
            "        JOIN movie m ON mp.id_movie = m.id_movie " +
            "        JOIN movie mv ON mv.id_movie = mp.id_movie " +
            "    WHERE " +
            "        m.flg_winner = true " +
            "), " +
            "movie_win_intervals AS ( " +
            "    SELECT " +
            "        producer, " +
            "        release_year AS previous_win_year, " +
            "        LEAD(release_year) OVER (PARTITION BY producer ORDER BY release_year) AS following_win_year, " +
            "        LAG(title) OVER (ORDER BY release_year) AS previous_movie, " +
            "        title AS following_movie " +
            "    FROM " +
            "        movie_producer_titles " +
            ") " +
            "SELECT " +
            "    producer, " +
            "    MIN(following_win_year - previous_win_year) AS yearInterval, " +
            "    previous_win_year, " +
            "    following_win_year " +
            "FROM " +
            "    movie_win_intervals " +
            "WHERE " +
            "    following_win_year IS NOT NULL " +
            "GROUP BY " +
            "    producer, " +
            "    previous_win_year, " +
            "    following_win_year " +
            "HAVING " +
            "    yearInterval = ( " +
            "        SELECT MIN(following_win_year - previous_win_year) " +
            "        FROM movie_win_intervals " +
            "        WHERE following_win_year IS NOT NULL " +
            "    ) " +
            "ORDER BY " +
            "    yearInterval ASC;", nativeQuery = true)
    List<Object[]> findMinIntervals();


    @Query("SELECT pd FROM Producer pd JOIN pd.movies mv WHERE mv.winner IS TRUE")
    List<Producer> findAllByMovieWinner();
}
