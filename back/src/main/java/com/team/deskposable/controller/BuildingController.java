package com.team.deskposable.controller;

import com.team.deskposable.entity.Building;
import com.team.deskposable.repository.BuildingRepository;
import org.hibernate.exception.GenericJDBCException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/buildings")
public class BuildingController {
    @Autowired
    BuildingRepository buildingRepository;

    @GetMapping()
    public Iterable<Building> buildings() { return buildingRepository.findAll(); }

    @GetMapping("/{id}")
    public Building building(@PathVariable Long id) {
        return buildingRepository.findOne(id);
    }

    @PostMapping()
    public String newBuilding(@RequestBody Building building) {
        try {
            buildingRepository.save(building);
            return "OK";
        } catch (GenericJDBCException exception) {
            return exception.getMessage();
        }
    }

    @DeleteMapping("/{id}")
    public String deleteBuilding(@PathVariable Long id) {
        try {
            buildingRepository.delete(id);
            return "OK";
        } catch (GenericJDBCException exception) {
            return exception.getMessage();
        }
    }

    @PutMapping("/{id}")
    public String modifyBuilding(@PathVariable Long id,@RequestBody Building building){
        Building buildingToEdit = buildingRepository.findOne(id);

        if(buildingToEdit != null) {
            return "ERROR : building not found !";
        }
        else {
            buildingRepository.save(buildingToEdit);
            return "ok";
        }
    }

}
