package hellojpa;

import javax.persistence.*;

@Entity
@Table(name = "ADDRESS")
public class AddressEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Embedded
    private Address address;

    protected AddressEntity(){}

    public AddressEntity(Address address) {
        this.address = address;
    }

    public Long getId() {
        return id;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
