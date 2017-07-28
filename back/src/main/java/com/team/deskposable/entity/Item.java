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

    @OneToMany
    private Person person;

    @OneToMany
    private Desk desk;
}
