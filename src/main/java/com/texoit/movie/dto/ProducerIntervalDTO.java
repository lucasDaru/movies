package com.texoit.movie.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class ProducerIntervalDTO {
    private String producer;
    private int yearInterval;
    private int previousWin;
    private int followingWin;

}
