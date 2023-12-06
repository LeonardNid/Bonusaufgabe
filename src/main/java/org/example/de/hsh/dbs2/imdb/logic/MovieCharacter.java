package org.example.de.hsh.dbs2.imdb.logic;

import jakarta.persistence.*;

@Entity()
@Table(name = "UE08_MOVIECHARACTER")
public class MovieCharacter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String character;
    private String alias;
    private Integer position;

    @ManyToOne
    @JoinColumn(name = "movieid")
    private Movie movie;

    @ManyToOne
    @JoinColumn(name = "personid")
    private Person person;


    public MovieCharacter(Long movCharID, String character, String alias) {
        this.id = movCharID;
        this.character = character;
        this.alias = alias;
    }
    public MovieCharacter(String character, String alias) {
        this.character = character;
        this.alias = alias;
    }

    public MovieCharacter() {

    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public void addMovie(Long movieId, EntityManager em) {
        this.movie = em.find(Movie.class, movieId);
    }

    public void addPerson(Long personId, EntityManager em) {
        this.person = em.find(Person.class, personId);
    }
    public void addPerson(String name, EntityManager em) {
        this.person = em.createQuery("select p from Person p where p.name = :name", Person.class)
                .setParameter("name", name)
                .getSingleResult();
    }

    public Long getId() {
        return id;
    }

    public String getCharacter() {
        return character;
    }

    public String getAlias() {
        return alias;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public Person getPerson() {
        return person;
    }
}
