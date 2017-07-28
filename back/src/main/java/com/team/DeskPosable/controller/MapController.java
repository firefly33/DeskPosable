package com.team.deskposable.controller;

import com.team.deskposable.entity.Map;
import com.team.deskposable.repository.MapRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by Marc ETOURNEAU on 27/07/2017.
 */

@RestController
@RequestMapping("/maps")
public class MapController {

    @Autowired
    MapRepository mapRepository;

    /*@GetMapping()
    public List<Map> getAllUsers() {
        return mapRepository.findAll();
    }*/
}
