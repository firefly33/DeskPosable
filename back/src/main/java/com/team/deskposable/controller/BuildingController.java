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
    public Iterable<Building> readAllBuildings() { return buildingRepository.findAll(); }

    @GetMapping("/{id}")
    public Building readBuilding(@PathVariable Long id) {
        return buildingRepository.findOne(id);
    }

    @PostMapping()
    public Building createBuilding(@RequestBody Building building) {
            buildingRepository.save(building);
            return building;
    }

    @DeleteMapping("/{id}")
    public Building deleteBuilding(@PathVariable Long id) {
        Building b = buildingRepository.findOne(id);

        buildingRepository.delete(id);
        return b;
    }

    @PutMapping("/{id}")
    public Building updateBuilding(@PathVariable Long id,@RequestBody Building building){
        Building b = buildingRepository.findOne(id);

        if(b != null) {
            b.setLabel(building.getLabel());
            b.setMaps(building.getMaps());
        }
        buildingRepository.save(b);

        return b;
    }

}
