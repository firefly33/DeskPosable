package com.team.deskposable.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class Building {

    @Id
    @GeneratedValue
    private Long id;
    private String label;

    @OneToMany(mappedBy="building")
    private List<Map> maps;

    public Building() {
        // NOTE : for bean convention
    }

    public Building(String label, List<Map> maps) {
        this.label = label;
        this.maps = maps;
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
    public List<Map> getMaps() {
        return maps;
    }
    public void setMaps(List<Map> maps) {
        this.maps = maps;
    }
}
