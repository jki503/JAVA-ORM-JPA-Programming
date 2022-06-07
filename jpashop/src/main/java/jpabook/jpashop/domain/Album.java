package jpabook.jpashop.domain;

import javax.persistence.Entity;

@Entity
public class Album extends Item{

    private String artist;
    private String etc;

    protected Album(){}

    public Album(String name, int price, int stockQuantity, String artist, String etc) {
        super(name, price, stockQuantity);
        this.artist = artist;
        this.etc = etc;
    }
}
