package org.example.de.hsh.dbs2.imdb.logic;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity()
@Table(name = "UE08_MOVIE")
public class Movie {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private int year;
    private String type;
    @ManyToMany
    @JoinTable(name = "UE08_HASGENRE")
    private Set<Genre> genres = new HashSet<>();
    @OneToMany(mappedBy = "movie", cascade = CascadeType.PERSIST)
    private List<MovieCharacter> movieCharacters = new ArrayList<>();
    @Transient
    private int positionCount = 0;

    public Movie(Long movieid, String title, Integer year, String type) {
        this.id = movieid;
        this.title = title;
        this.year = year;
        this.type =type;
    }

    public Movie(String title, String type, int year) {
        this.title = title;
        this.type = type;
        this.year = year;
    }

    public Movie() {

    }

    public void addGenre(Long genreId, EntityManager em) {
        genres.add(em.find(Genre.class, genreId));
    }
    public void addGenre(String genre, EntityManager em) {
        genres.add(em.createQuery("SELECT g FROM Genre g WHERE g.genre = :genre", Genre.class)
                .setParameter("genre", genre)
                .getSingleResult()
        );
    }

    public void addMovieCharacter(Long movCharId, EntityManager em) {
        MovieCharacter movieCharacter = em.find(MovieCharacter.class, movCharId);
        movieCharacter.setPosition(positionCount++);
        movieCharacters.add(movieCharacter);
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getType() {
        return type;
    }

    public int getYear() {
        return year;
    }

    public Set<Genre> getGenres() {
        return genres;
    }

    public List<MovieCharacter> getMovieCharacters() {
        return movieCharacters;
    }
}
