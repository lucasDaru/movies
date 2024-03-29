package com.texoit.movie.config;

import com.texoit.movie.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class AppStartupRunner implements ResourceLoaderAware, CommandLineRunner {

    @Autowired
    @Lazy
    private MovieService movieService;

    private ResourceLoader resourceLoader;

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @Override
    public void run(String... args) throws Exception {
        try {
            Resource resource = resourceLoader.getResource("classpath:static/movies.csv");
            movieService.readFileCSVFile(resource.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
