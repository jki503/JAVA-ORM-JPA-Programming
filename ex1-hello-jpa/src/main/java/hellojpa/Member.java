package hellojpa;

import javax.persistence.*;
import java.util.*;

@Entity
public class Member {

    @Id
    @GeneratedValue
    @Column(name = "MEMBER_ID")
    private Long id;

    @Column(name = "USER_NAME")
    private String name;

    @Embedded
    private Address address;

    @ElementCollection
    @Column(name = "FOOD_NAME")
    @CollectionTable(name = "FAVORITE_FOOD", joinColumns =
        @JoinColumn(name = "MEMBER_ID")
    )
    private Set<String> favoriteFoods = new HashSet<>();

//    @ElementCollection
//    @CollectionTable(name = "ADDRESS", joinColumns =
//        @JoinColumn(name = "MEMBER_ID")
//    )
//    private List<Address> addressHistory = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "MEMBER_ID")
    private List<AddressEntity> addressHistory = new ArrayList<>();

    protected Member() {
    }

    public Member(String name, Address address, Set<String> favoriteFoods, List<AddressEntity> addressHistory) {
        this.name = name;
        this.address = address;
        this.favoriteFoods = favoriteFoods;
        this.addressHistory = addressHistory;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Member.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("name='" + name + "'")
                .toString();
    }
}
