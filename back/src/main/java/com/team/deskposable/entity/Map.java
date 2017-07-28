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
    private String label;
    private String imagePath;

    @ManyToOne
    private Building building;

    public Map() {
        // NOTE : for bean convention
    }
    public Map(String label, String imagePath, Building building) {
        this.label = label;
        this.imagePath = imagePath;
        this.building = building;
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
}
