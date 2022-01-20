package domain;

import services.AreaService;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Table(name = "area")
@Entity
public class Area {
    @Id
    @GeneratedValue
    public int id;

    @OneToMany(mappedBy = "area", cascade = CascadeType.ALL)
    public List<Address> addresses = new ArrayList<>();

    public void Save() {
        new AreaService().saveArea(this);
    }
}
