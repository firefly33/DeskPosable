package com.team.deskposable.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Marc ETOURNEAU on 27/07/2017.
 */

@Entity
public class Map {

    @Id
    @GeneratedValue
    private Long id;
    private String label;
    private String imagePath;

    @JsonIgnore
    @ManyToOne
    private Building building;

    @JoinColumn(name = "map_id", referencedColumnName = "id")
    @OneToMany
    private List<Desk> desks;

    public Map() {
        // NOTE : for bean convention
    }
    public Map(String label, String imagePath, Building building, List<Desk> desks) {
        this.label = label;
        this.imagePath = imagePath;
        this.building = building;
        this.desks = desks;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getLabel() {
        return label;
    }
    public void setLabel(String label) {
        this.label = label;
    }
    public String getImagePath() {
        return imagePath;
    }
    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
    public Building getBuilding() {
        return building;
    }
    public void setBuilding(Building building) {
        this.building = building;
    }
    public List<Desk> getDesks() {
        return desks;
    }
    public void setDesks(List<Desk> desks) {
        this.desks = desks;
    }

    @Override
    public String toString() {
        return "Map{" +
                "id=" + id +
                ", label='" + label + '\'' +
                ", imagePath='" + imagePath + '\'' +
                ", building=" + building +
                ", desks=" + desks +
                '}';
    }
}
