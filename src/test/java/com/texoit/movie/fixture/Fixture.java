package com.texoit.movie.fixture;

import com.texoit.movie.dto.ProducerIntervalDTO;
import com.texoit.movie.dto.StudioDTO;
import com.texoit.movie.entity.Movie;
import com.texoit.movie.entity.Producer;
import com.texoit.movie.entity.Studio;

import java.util.*;

public class Fixture {
    public static Movie getMovie() {
        Studio studio = StudioFixture.createStudio("Some Studio");
        Producer producer = ProducerFixture.createProducer("Some Producer", new ArrayList<>());
        return MovieFixture.createMovie("Some Movie", 2018, Arrays.asList(studio), Arrays.asList(producer), true);
    }

    public static Producer getProducer() {
        return ProducerFixture.createProducer("Some Producer", new ArrayList<>());
    }

    public static Studio getStudio() {
        return StudioFixture.createStudio("Some Studio");
    }

    public static Producer getProducerMin() {
        return ProducerFixture.createProducer("Some Producer Min", createMoviesForProducerMin());
    }

    public static Producer getProducerBetweenMedium() {
        return ProducerFixture.createProducer("Some Producer Between Medium", createMoviesForProducerBetweenMedium());
    }

    public static Producer getProducerMax() {
        return ProducerFixture.createProducer("Some Producer Max", createMoviesForProducerMax());
    }

    public static Producer getProducerBetweenLong() {
        return ProducerFixture.createProducer("Some Producer Between Long", createMoviesForProducerBetweenLong());
    }

    public static Map<String, List<ProducerIntervalDTO>> getProducerMinMax() {
        Map<Long, ProducerIntervalDTO> minMapByInterval = new HashMap<>();
        Map<Long, ProducerIntervalDTO> maxMapByInterval = new HashMap<>();

        minMapByInterval.put(1L, new ProducerIntervalDTO("Some Producer", 1, 2017, 2018));
        minMapByInterval.put(6L, new ProducerIntervalDTO("Some Other Producer", 6, 2010, 2016));

        Map<String, List<ProducerIntervalDTO>> result = new HashMap<>();
        result.put("min", new ArrayList<>(minMapByInterval.values()));
        result.put("max", new ArrayList<>(maxMapByInterval.values()));

        return result;
    }

    private static List<Movie> createMoviesForProducerMin() {
        Studio studio = StudioFixture.createStudio("Studio A");
        return Arrays.asList(
                MovieFixture.createMovie("Some Movie Min One", 2017, Arrays.asList(studio), new ArrayList<>(), true),
                MovieFixture.createMovie("Some Movie Min Two", 2018, Arrays.asList(studio), new ArrayList<>(), true)
        );
    }

    private static List<Movie> createMoviesForProducerBetweenMedium() {
        Studio studio = StudioFixture.createStudio("Studio B");
        return Arrays.asList(
                MovieFixture.createMovie("Some Movie Between Medium One", 2010, Arrays.asList(studio), new ArrayList<>(), true),
                MovieFixture.createMovie("Some Movie Between Medium Two", 2015, Arrays.asList(studio), new ArrayList<>(), true)
        );
    }

    private static List<Movie> createMoviesForProducerMax() {
        Studio studio = StudioFixture.createStudio("Studio C");
        return Arrays.asList(
                MovieFixture.createMovie("Some Movie Max One", 1991, Arrays.asList(studio), new ArrayList<>(), true),
                MovieFixture.createMovie("Some Movie Max Two", 2017, Arrays.asList(studio), new ArrayList<>(), true)
        );
    }

    private static List<Movie> createMoviesForProducerBetweenLong() {
        Studio studio = StudioFixture.createStudio("Studio D");
        return Arrays.asList(
                MovieFixture.createMovie("Some Movie Between Long One", 1997, Arrays.asList(studio), new ArrayList<>(), true),
                MovieFixture.createMovie("Some Movie Between Long Two", 2014, Arrays.asList(studio), new ArrayList<>(), true)
        );
    }
}
