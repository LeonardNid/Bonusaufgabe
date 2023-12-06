package org.example.de.hsh.dbs2.imdb.logic;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity()
@Table(name = "UE08_GENRE")
public class Genre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String genre;
    @ManyToMany(mappedBy = "genres")
    private Set<Movie> movies = new HashSet<>();

    public Genre(String genre) {
        this.genre = genre;
    }
    public Genre(Long genreid, String genre) {
        this.id = genreid;
        this.genre = genre;
    }

    public Genre() {

    }

    public Long getId() {
        return id;
    }

    public String getGenre() {
        return genre;
    }
}
