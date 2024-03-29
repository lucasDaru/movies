package com.texoit.movie.entity;

import lombok.*;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.io.Serializable;

@Entity
@Table(name = "movie")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Movie implements BasicEntity, Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    @Column(name = "id_movie")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "release_year")
    private Integer year;

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(name="movie_studio", joinColumns=
            {@JoinColumn(name="id_movie")}, inverseJoinColumns=
            {@JoinColumn(name="id_studio")})
    private List<Studio> studios = new ArrayList<>();

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(name="movie_producer", joinColumns=
            {@JoinColumn(name="id_movie")}, inverseJoinColumns=
            {@JoinColumn(name="id_producer")})
    private List<Producer> producers = new ArrayList<>();

    @Column(name = "flg_winner")
    private Boolean winner;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Movie movie = (Movie) o;
        return Objects.equals(id, movie.id);
    }
}
