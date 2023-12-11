package org.example.de.hsh.dbs2.imdb.logic;

import jakarta.persistence.*;

import java.util.*;

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
    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL)
    private List<MovieCharacter> movieCharacters = new ArrayList<>();
    @Transient
    private int positionCount = 0;

    public Movie(Long id, String title, String type, int year) {
        this.id = id;
        this.title = title;
        this.type = type;
        this.year = year;
    }

    public Movie(String title, String type, int year) {
        this.title = title;
        this.type = type;
        this.year = year;
    }

    public Movie() {

    }

    public void addGenre(String genre, EntityManager em) {
        genres.add(em.createQuery("SELECT g FROM Genre g WHERE g.genre = :genre", Genre.class)
                .setParameter("genre", genre)
                .getSingleResult()
        );
    }

    public void addMovieCharacter(MovieCharacter movieCharacter) {
        movieCharacter.setPosition(positionCount++);
        movieCharacters.add(movieCharacter);
    }

    public void clearMoviecharacters(EntityManager em) {
        for (MovieCharacter movieCharacter : movieCharacters) {
            em.remove(movieCharacter);
        }
        movieCharacters.clear();
        positionCount = 0;
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

    public List<MovieCharacter> getSortedMovieCharacters() {
        movieCharacters.sort(Comparator.comparingInt(MovieCharacter::getPosition));
        return movieCharacters;
    }
}
