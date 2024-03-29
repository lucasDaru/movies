package com.texoit.movie.fixture;

import com.texoit.movie.entity.Movie;
import com.texoit.movie.entity.Producer;

import java.util.List;

public class ProducerFixture {

    public static Producer createProducer(String name, List<Movie> movies) {
        Producer producer = new Producer();
        producer.setName(name);
        producer.setMovies(movies);
        return producer;
    }

}
