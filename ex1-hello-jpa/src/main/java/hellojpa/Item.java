package hellojpa;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@DiscriminatorColumn
public abstract class Item {

    @Id
    @GeneratedValue
    private Long Id;

    private String name;

    private int price;

    protected Item(){}

    public Item(String name, int price) {
        this.name = name;
        this.price = price;
    }

    public Long getId() {
        return Id;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }
}
