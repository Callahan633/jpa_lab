package domain;

import services.ParentsService;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Table(name = "parents")
@Entity
public class Parents {
    @Id
    @GeneratedValue
    public int id;
    public String FullnameFirst;
    public String FullnameSecond;

    @OneToMany(mappedBy = "parents", cascade = CascadeType.ALL)
    public List<Child> children = new ArrayList<Child>();

    @ManyToOne
    @JoinColumn(name = "addressId")
    public Address address;

    public void save() {
        new ParentsService().saveParents(this);
    }

    public void update() {
        new ParentsService().updateParents(this);
    }
}
