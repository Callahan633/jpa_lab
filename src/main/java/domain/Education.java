package domain;

import services.EducationService;

import javax.persistence.*;

@Table(name = "education")
@Entity
public class Education {
    @Id
    @GeneratedValue
    public int id;

    public Education() {
    }

    public Education(String number, Address address) {
        this.number = number;
        this.address = address;
    }

    public String number;

    @ManyToOne
    @JoinColumn(name = "addressId")
    public Address address;

    public void Save() {
        new EducationService().saveEducation(this);
    }
}
