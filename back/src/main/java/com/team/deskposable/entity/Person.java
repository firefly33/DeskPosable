package com.team.deskposable.entity;

import javax.persistence.*;

/**
 * Created by alec_ on 28/07/2017.
 */

@Entity
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String firstname;
    private String lastname;
    private String employment;

    @ManyToOne
    private Desk desk;

    public Person(String firstname, String lastname, String employment, Desk desk) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.employment = employment;
        this.desk = desk;
    }

    public Person() {
        // NOTE : for bean convention
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getFirstname() {
        return firstname;
    }
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }
    public String getLastname() {
        return lastname;
    }
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
    public String getEmployment() {
        return employment;
    }
    public void setEmployment(String employment) {
        this.employment = employment;
    }
    public Desk getDesk() {
        return desk;
    }
    public void setDesk(Desk desk) {
        this.desk = desk;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", employment='" + employment + '\'' +
                ", desk=" + desk +
                '}';
    }
}
