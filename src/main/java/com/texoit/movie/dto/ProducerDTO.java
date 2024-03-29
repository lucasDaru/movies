package com.texoit.movie.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
@AllArgsConstructor
public class ProducerDTO extends BaseDTO {

    private static final long serialVersionUID = 1L;
}
