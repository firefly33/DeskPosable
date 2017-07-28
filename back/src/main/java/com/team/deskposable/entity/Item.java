package com.team.deskposable.entity;

import javax.persistence.*;

/**
 * Created by fx on 28/07/17.
 */
@Entity
public class Item {
    @Id
    @GeneratedValue
    private Long id;

    private String label;

    @ManyToOne
    private Person person;

    @ManyToOne
    private Desk desk;

    public Long getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

    public Person getPerson() {
        return person;
    }

    public Desk getDesk() {
        return desk;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
