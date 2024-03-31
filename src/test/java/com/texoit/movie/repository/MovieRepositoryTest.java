package com.texoit.movie.repository;

import com.texoit.movie.entity.Movie;
import com.texoit.movie.service.impl.MovieServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static com.texoit.movie.fixture.Fixture.getMovie;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MovieRepositoryTest {

    @Mock
    private MovieRepository movieRepository;

    @InjectMocks
    private MovieServiceImpl movieService;

    @Test
    void testFindAllByYear() {
        Integer year = 2018;
        List<Movie> expectedMovies = Arrays.asList(getMovie());

        when(movieRepository.findAllByYear(year)).thenReturn(expectedMovies);

        List<Movie> actualMovies = movieService.findMoviesByYear(year);

        assertEquals(expectedMovies.size(), actualMovies.size());
        assertEquals(expectedMovies, actualMovies);
    }

    @Test
    void testFindAllByYearAndWinner() {
        Integer year = 2018;
        boolean winner = true;
        List<Movie> expectedMovies = Arrays.asList(getMovie());

        when(movieRepository.findAllByYearAndWinner(year, winner)).thenReturn(expectedMovies);

        List<Movie> actualMovies = movieService.findMoviesByYearAndWinner(year, winner);

        assertEquals(expectedMovies.size(), actualMovies.size());
        assertEquals(expectedMovies, actualMovies);
    }

    @Test
    void testFindByWinnerTrueOrderByYearAsc() {
        List<Movie> expectedMovies = Arrays.asList(getMovie());

        when(movieRepository.findByWinnerTrueOrderByYearAsc()).thenReturn(expectedMovies);

        List<Movie> actualMovies = movieService.findWinningMoviesOrderedByYear();

        assertEquals(expectedMovies.size(), actualMovies.size());
        assertEquals(expectedMovies, actualMovies);
    }
}

