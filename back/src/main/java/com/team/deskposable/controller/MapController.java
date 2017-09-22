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
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/maps")
public class MapController {

    //  AXEL PATH :  public static final String UPLOADED_FOLDER = "C:\\Users\\A637191\\Desktop\\CESI\\JAVA_PROJET\\DeskPosable\\DeskPosable\\back\\src\\main\\resources\\static\\images\\";
    public static final String UPLOADED_FOLDER = "C:\\Users\\alec_\\Documents\\JavaProject\\DeskPosable\\back\\src\\main\\resources\\static\\images\\";

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
    public void newMap(@RequestPart("image") MultipartFile file, @RequestPart("name") String name, @RequestPart("idBuilding") String idBuilding, HttpServletResponse response) {
        if (file.isEmpty()) {
            response.setStatus(400);
        }
        long idBuild = Long.parseLong(idBuilding);
        try {
            // Get the file and save it somewhere
            byte[] bytes = file.getBytes();
            String filename = file.getOriginalFilename().toLowerCase();
            String strTab[] = filename.split("\\.");
            String ext = "";
            if (strTab.length >= 1) {
                ext = strTab[strTab.length - 1];
            }
            if (!ext.equals("png") && !ext.equals("jpg")) {
                throw new Exception("Le format du fichier est incorrect");
            }
            //clé permettant d'être sûr que 2 images n'auront pas le meme nom
            String randomKey = String.valueOf((int) (Math.random() * (100000)));
            SimpleDateFormat formatDate = new SimpleDateFormat("yyyyMMddHHmmssSSS");
            Date now = new Date();
            //filename = randomKey + formatDate.format(now) + "." + ext;
            filename = "plan1.png";
            String imgPath = String.valueOf(this.getClass().getResource(UPLOADED_FOLDER + filename));
            File newFile = new File(imgPath);
            if (!newFile.exists()) {
                newFile.createNewFile();
            }
            Path path = Paths.get(UPLOADED_FOLDER + filename);
            Files.write(path, bytes);

            Map newMap = new Map();
            newMap.setLabel(name);
            newMap.setImagePath(filename);
            newMap.setBuilding(buildingRepository.findOne(idBuild));
            newMap.setBuilding(buildingRepository.findOne(idBuild));
            mapRepository.save(newMap);
            response.setStatus(200);
        } catch (Exception e) {
            System.out.println();
            response.setStatus(400);
        }
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
