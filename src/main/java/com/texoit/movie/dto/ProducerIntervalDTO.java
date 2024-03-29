package com.texoit.movie.dto;

import lombok.*;

import java.util.Objects;

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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProducerIntervalDTO that = (ProducerIntervalDTO) o;
        return yearInterval == that.yearInterval &&
                previousWin == that.previousWin &&
                followingWin == that.followingWin &&
                Objects.equals(producer, that.producer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(producer, yearInterval, previousWin, followingWin);
    }

}
