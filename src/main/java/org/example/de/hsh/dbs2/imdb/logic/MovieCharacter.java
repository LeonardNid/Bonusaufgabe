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


    public MovieCharacter(String character, String alias) {
        this.character = character;
        this.alias = alias;
    }

    public MovieCharacter() {

    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public void setPerson(String name, EntityManager em) {
        this.person = em.createQuery("select p from Person p where p.name = :name", Person.class)
                .setParameter("name", name)
                .getSingleResult();
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
