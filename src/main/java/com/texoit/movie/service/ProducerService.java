package com.texoit.movie.service;

import com.texoit.movie.dto.ProducerIntervalDTO;
import com.texoit.movie.entity.Producer;

import java.util.List;
import java.util.Map;

public interface ProducerService extends BaseService<Producer> {

    Map<String, List<ProducerIntervalDTO>> findProducerIntervalsMinMax();
}
