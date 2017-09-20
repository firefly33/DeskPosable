package com.team.deskposable.controller;

import com.team.deskposable.entity.Desk;
import com.team.deskposable.entity.Map;
import com.team.deskposable.repository.BuildingRepository;
import com.team.deskposable.repository.MapRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

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

    @GetMapping("/{id}")
    public Map map(@PathVariable Long id) {
        return mapRepository.findOne(id);
    }

    @PostMapping()
    public Map newMap(@RequestBody Map map) {
        mapRepository.save(map);
        return map;
    }

    /*@PostMapping("/save")
    public String saveMap(@RequestParam("label_plan") MultipartFile labelPlan,@RequestParam("myFile") MultipartFile plan) {
        if (!plan.isEmpty() && plan.getSize() <= 512 * 1024) {
            if (plan.getOriginalFilename().endsWith(".pdf")) {
                userService.saveDocumentPapier(user, plan);
            } else {
                model.addAttribute("erreurFormat", true);
                return "front/moncompte";
            }

            return "redirect:/moncompte";
        } else {
            System.out.println("Fichier vide!!");
            return "redirect:/moncompte";
        }
        Map map = new Map();
        mapRepository.save(map);
        System.out.println(map);
        return map.toString();
    }

    @DeleteMapping("/{id}")
    public Map deleteMap(@PathVariable Long id) {
        Map m = mapRepository.findOne(id);
        mapRepository.delete(id);
        return m;
    }*/

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

    @PutMapping("/{id}/addDesk")
    public Map addDesk(@PathVariable Long id, @RequestBody Desk desk) {
        Map mapToEdit = mapRepository.findOne(id);

        if (mapToEdit != null) {
            List<Desk> desksOfMap = mapToEdit.getDesks();
            desksOfMap.add(desk);

            mapRepository.save(mapToEdit);
        }

        return mapToEdit;
    }

    public String show(Map map) {

        return "";
    }
}
