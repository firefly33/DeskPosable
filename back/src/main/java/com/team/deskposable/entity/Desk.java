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
}
