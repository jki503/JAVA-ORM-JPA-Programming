package hellojpa;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Address {

    @Column(nullable = false)
    private String city;
    private String street;
    private String zipcode;

    protected Address(){}

    public Address(String street, String zipcode) {
        this.street = street;
        this.zipcode = zipcode;
    }

    public Address(String city, String street, String zipcode) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }

    public String getCity() {
        return city;
    }

    public String getStreet() {
        return street;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }
}
