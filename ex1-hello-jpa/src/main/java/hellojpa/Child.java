package hellojpa;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Child {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PARENT_ID")
    private Parent parent;

    protected Child(){}

    public Child(String name) {
        this.name = name;
    }


    public void setParent(Parent parent) {
        if(Objects.nonNull((this.parent))){
            this.parent.getChildren().remove(this);
        }

        parent.getChildren().add(this);
        this.parent = parent;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Parent getParent() {
        return parent;
    }

}
