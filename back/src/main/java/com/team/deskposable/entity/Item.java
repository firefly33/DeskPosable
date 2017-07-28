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
}
