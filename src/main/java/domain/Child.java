package domain;

import services.ChildService;

import javax.persistence.*;

@Table(name = "child")
@Entity
public class Child {
    @Id
    @GeneratedValue
    public int id;

    @ManyToOne
    @JoinColumn(name = "parentsId")
    public Parents parents;

    public String fullname;

    public Child() {
    }

    public Child(String fullname, Parents parents, int age) {
        this.fullname = fullname;
        this.parents = parents;
        this.age = age;
    }

    public int age;

    @ManyToOne
    @JoinColumn(name = "educationId")
    public Education education;

    public void save() {
        new ChildService().saveChild(this);
    }

    public void update() {
        new ChildService().updateChild(this);
    }
}
