package hellojpa;

import javax.persistence.Entity;

@Entity
public class Movie extends Item{

    private String director;
    private String actor;

    protected Movie(){}

    public Movie(String name, int price, String director, String actor) {
        super(name, price);
        this.director = director;
        this.actor = actor;
    }
}
