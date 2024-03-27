package com.texoit.movie.service;

import com.texoit.movie.dto.ProducerIntervalDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface MovieService {
    void processCSVFile(MultipartFile file) throws IOException;
    List<ProducerIntervalDTO> findProducersWithMaxInterval();
}
