package com.team.deskposable.controller;

import com.team.deskposable.entity.Desk;
import com.team.deskposable.repository.DeskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by fx on 28/07/17.
 */
@RestController
@RequestMapping("/desks")
public class DeskController {

    @Autowired
    DeskRepository deskRepository;

    @GetMapping()
    public Iterable<Desk> desks() { return deskRepository.findAll(); }

    @GetMapping("/{id}")
    public Desk desk(@PathVariable Long id) {
        return deskRepository.findOne(id);
    }

    @PostMapping()
    public Desk newBuilding(@RequestBody Desk desk) {
        deskRepository.save(desk);
        return desk;
    }

    @DeleteMapping("/{id}")
    public Desk deleteBuilding(@PathVariable Long id) {
        Desk deskToDelete = deskRepository.findOne(id);

        deskRepository.delete(id);
        return deskToDelete;
    }

    @PutMapping("/{id}")
    public Desk modifyBuilding(@PathVariable Long id,@RequestBody Desk desk){
        Desk deskToEdit = deskRepository.findOne(id);

        if(deskToEdit != null) {
            deskToEdit.setLabel(desk.getLabel());
            deskToEdit.setX(desk.getX());
            deskToEdit.setY(desk.getY());
            deskToEdit.setOrientation(desk.getOrientation());
        }
        deskRepository.save(deskToEdit);

        return deskToEdit;
    }
}
