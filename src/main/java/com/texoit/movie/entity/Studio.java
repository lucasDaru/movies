package com.texoit.movie.entity;

import lombok.*;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "studio")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Studio implements BasicEntity, Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    @Column(name = "id_studio")
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToMany(mappedBy = "studios")
    private List<Movie> movies = new ArrayList<>();
}
