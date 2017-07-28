package com.team.deskposable.repository;

import com.team.deskposable.entity.Building;
import com.team.deskposable.entity.Map;
import com.team.deskposable.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Arrays;

@Profile("generate")
@Component
public class DataPopulator {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BuildingRepository buildingRepository;
    @Autowired
    private MapRepository mapRepository;

    @PostConstruct
    public void init() {

        User user1 = new User("Zawialoff", "Alexis", "29/03/1996");
        User user2 = new User("Adaine-Jean-Pierre", "Axel", "29/03/1996");
        User user3 = new User("Lesaffre", "FX", "29/03/1996");
        User user4 = new User("Etourneau", "Marc", "29/03/1996");

        userRepository.save(Arrays.asList(user1, user2, user3, user4));

        Building building1 = new Building("label1", null);
        Building building2 = new Building("label2", null);
        Building building3 = new Building("label3", null);

        buildingRepository.save(Arrays.asList(building1, building2, building3));

        Map map1 = new Map("map1", "path1", null);
        Map map2 = new Map("map2", "path2", null);
        Map map3 = new Map("map3", "path3", null);

        mapRepository.save(Arrays.asList(map1, map2, map3));
    }
}
