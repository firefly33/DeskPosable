package com.team.deskposable.repository;

import com.team.deskposable.entity.*;
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
    @Autowired
    private DeskRepository deskRepository;
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private SessionRepository sessionRepository;

    @PostConstruct
    public void init() {

        User user1 = new User("Zawialoff", "Alexis", "29/03/1996", "alexis.zawialoff@viacesi.fr", "azerty");
        User user2 = new User("Adaine-Jean-Pierre", "Axel", "29/03/1996", "axel.adainejeanpierre@viacesi.fr", "azerty");
        User user3 = new User("Lesaffre", "FX", "12/09/1982", "francoisxavier.lesaffre@viacesi.fr", "120982");
        User user4 = new User("Etourneau", "Marc", "29/03/1996", "marc.etourneau@viacesi.fr", "azerty");

        userRepository.save(Arrays.asList(user1, user2, user3, user4));

        Building building1 = new Building("label1", null);
        Building building2 = new Building("label2", null);
        Building building3 = new Building("label3", null);

        buildingRepository.save(Arrays.asList(building1, building2, building3));

        Map map1 = new Map("Etage 2 - Batiment 1 - Atos Intégration", "plan1.png", building1, null);
        Map map2 = new Map("Etage 4 - Batiment 3 - Atos Intégration", "plan2.jpg", building2, null);
        Map map3 = new Map("map3", "path3", null, null);

        mapRepository.save(Arrays.asList(map1, map2, map3));

        Person person1 = new Person("Ken", "Kaneki", "ICD", null);
        Person person2 = new Person("Shirou", "Emiya", "CP", null);
        Person person3 = new Person("Izuku", "Midoriya", "DEV", null);

        personRepository.save(Arrays.asList(person1, person2, person3));
    }
}
