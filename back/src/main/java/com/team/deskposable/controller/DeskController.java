package com.team.deskposable.controller;

import com.team.deskposable.entity.Desk;
import com.team.deskposable.entity.Map;
import com.team.deskposable.repository.DeskRepository;
import com.team.deskposable.repository.MapRepository;
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
    @Autowired
    MapRepository mapRepository;

    @GetMapping()
    public Iterable<Desk> readAllDesks () { return deskRepository.findAll(); }

    @GetMapping("/{id}")
    public Desk readDesk (@PathVariable Long id) {
        return deskRepository.findOne(id);
    }

    @PostMapping()
    public Desk createDesk(@RequestBody Desk desk) {
        deskRepository.save(desk);
        return desk;
    }

    @DeleteMapping("/{id}")
    public Desk deleteDesk (@PathVariable Long id) {
        Desk deskToDelete = deskRepository.findOne(id);

        deskRepository.delete(id);
        return deskToDelete;
    }

    @PutMapping("/{id}")
    public Desk updateDesk(@PathVariable Long id,@RequestBody Desk desk){
        Desk deskToUpdate = deskRepository.findOne(id);

        if(deskToUpdate != null) {
            deskToUpdate.setLabel(desk.getLabel());
            deskToUpdate.setX(desk.getX());
            deskToUpdate.setY(desk.getY());
            deskToUpdate.setOrientation(desk.getOrientation());
        }
        deskRepository.save(deskToUpdate);

        return deskToUpdate;
    }

}
