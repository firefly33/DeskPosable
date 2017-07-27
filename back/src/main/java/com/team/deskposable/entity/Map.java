package com.team.deskposable.entity;

import javax.persistence.*;

/**
 * Created by Marc ETOURNEAU on 27/07/2017.
 */

@Entity
public class Map {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String imagePath;

    @ManyToOne
    private Building building;


}
