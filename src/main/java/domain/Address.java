package domain;

import services.AddressService;

import javax.persistence.*;

@Table(name = "addresses")
@Entity
public class Address {
    @Id
    @GeneratedValue
    public int id;

    @ManyToOne
    @JoinColumn(name = "areaId")
    public Area area;

    public Address() {
    }

    public Address(String value) {
        this.value = value;
    }

    public String value;

    public void Save() {
        new AddressService().saveAddress(this);
    }
}
