package com.team.deskposable.controller;

import com.team.deskposable.entity.Building;
import com.team.deskposable.entity.Map;
import com.team.deskposable.repository.BuildingRepository;
import com.team.deskposable.repository.MapRepository;
import org.hibernate.exception.GenericJDBCException;
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
    public String newMap(@RequestBody Map map) {

        try {
            mapRepository.save(map);
            return "OK";
        } catch (GenericJDBCException exception) {
            return exception.getMessage();
        }
    }

    @DeleteMapping("/{id}")
    public String deleteMap(@PathVariable Long id) {

        try {
            mapRepository.delete(id);
            return "Ok";
        } catch (GenericJDBCException exception) {
            return exception.getMessage();
        }
    }

    @PutMapping("/{id}")
    public String modifyMap(@PathVariable Long id,@RequestBody Map map){
        Building building = buildingRepository.findOne(id);

        if(building != null) {
            return "ERROR : building not found !";
        }
        else {
            mapRepository.save(map);
            return "ok";
        }
    }

}
