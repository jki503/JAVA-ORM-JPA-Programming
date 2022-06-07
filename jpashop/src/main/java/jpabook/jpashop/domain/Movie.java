package jpabook.jpashop.domain;

import javax.persistence.Entity;

@Entity
public class Movie extends Item{

    private String director;
    private String actor;

    protected Movie(){}

    public Movie(String name, int price, int stockQuantity, String director, String actor) {
        super(name, price, stockQuantity);
        this.director = director;
        this.actor = actor;
    }

    public String getDirector() {
        return director;
    }

    public String getActor() {
        return actor;
    }
}
