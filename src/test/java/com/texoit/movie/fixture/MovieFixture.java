package com.texoit.movie.fixture;

import com.texoit.movie.entity.Movie;
import com.texoit.movie.entity.Producer;
import com.texoit.movie.entity.Studio;

import java.util.List;

public class MovieFixture {

    public static Movie createMovie(String title, int year, List<Studio> studios, List<Producer> producers, boolean winner) {
        Movie movie = new Movie();
        movie.setTitle(title);
        movie.setYear(year);
        movie.setStudios(studios);
        movie.setProducers(producers);
        movie.setWinner(winner);
        return movie;
    }

}
