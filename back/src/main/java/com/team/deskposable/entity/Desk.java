package com.team.deskposable.entity;

import javax.persistence.*;

/**
 * Created by fx on 28/07/17.
 */
@Entity
public class Desk {
    @Id
    @GeneratedValue
    private Long id;
    private String label; // TODO : Add label to the MCD
    private Double x;
    private Double y;
    private int orientation;

    public Desk(String label, Double x, Double y, int orientation) {
        this.label = label;
        this.x = x;
        this.y = y;
        this.orientation = orientation;
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

    public Double getX() {
        return x;
    }

    public void setX(Double x) {
        this.x = x;
    }

    public Double getY() {
        return y;
    }

    public void setY(Double y) {
        this.y = y;
    }

    public int getOrientation() {
        return orientation;
    }

    public void setOrientation(int orientation) {
        this.orientation = orientation;
    }
}
