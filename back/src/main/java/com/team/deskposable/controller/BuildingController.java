package com.team.deskposable.controller;

import com.team.deskposable.entity.Building;
import com.team.deskposable.repository.BuildingRepository;
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
    public Building newBuilding(@RequestBody Building building) {
            buildingRepository.save(building);
            return building;
    }

    @DeleteMapping("/{id}")
    public Building deleteBuilding(@PathVariable Long id) {
        Building buildingToDelete = buildingRepository.findOne(id);

        buildingRepository.delete(id);
        return buildingToDelete;
    }

    @PutMapping("/{id}")
    public Building modifyBuilding(@PathVariable Long id,@RequestBody Building building){
        Building buildingToEdit = buildingRepository.findOne(id);

        if(buildingToEdit != null) {
            buildingToEdit.setLabel(building.getLabel());
            buildingToEdit.setMaps(building.getMaps());
        }

        return buildingToEdit;
    }

}
