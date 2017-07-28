package com.team.deskposable.controller;

import com.team.deskposable.entity.Map;
import com.team.deskposable.repository.BuildingRepository;
import com.team.deskposable.repository.MapRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/maps")
public class MapController {

    @Autowired
    MapRepository mapRepository;
    @Autowired
    BuildingRepository buildingRepository;

    @GetMapping()
    public Iterable<Map> maps() {
        return mapRepository.findAll();
    }

    @GetMapping("{/id}")
    public Map map(@PathVariable Long id) {
        return mapRepository.findOne(id);
    }

    @PostMapping()
    public Map newMap(@RequestBody Map map) {
        mapRepository.save(map);
        return map;
    }

    @DeleteMapping("/{id}")
    public Map deleteMap(@PathVariable Long id) {
        Map mapToDelete = mapRepository.findOne(id);

        mapRepository.delete(id);
        return mapToDelete;
    }

    @PutMapping("/{id}")
    public Map modifyMap(@PathVariable Long id, @RequestBody Map map) {
        Map mapToEdit = mapRepository.findOne(id);

        if (mapToEdit != null) {
            mapToEdit.setLabel(map.getLabel());
            mapToEdit.setImagePath(map.getImagePath());
            mapToEdit.setBuilding(map.getBuilding());
            mapRepository.save(map);
        }

        return mapToEdit;
    }

}
