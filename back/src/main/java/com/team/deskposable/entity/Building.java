package com.team.deskposable.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Marc ETOURNEAU on 27/07/2017.
 */

@Entity
public class Building {

    @Id
    @GeneratedValue
    private Long id;
    private String label;

    @JsonIgnore
    @ManyToMany(mappedBy = "building")
    private List<Map> maps;

}
