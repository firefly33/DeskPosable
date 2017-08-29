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
        Map m = mapRepository.findOne(id);
        mapRepository.delete(id);
        return m;
    }

    @PutMapping("/{id}")
    public Map modifyMap(@PathVariable Long id, @RequestBody Map map) {
        Map m = mapRepository.findOne(id);

        if (m != null) {
            if (map.getLabel() != null) m.setLabel(map.getLabel());
            if (map.getBuilding() != null) m.setImagePath(map.getImagePath());
            if (map.getBuilding() != null) m.setBuilding(map.getBuilding());
            mapRepository.save(m);
        }

        return m;
    }

}
