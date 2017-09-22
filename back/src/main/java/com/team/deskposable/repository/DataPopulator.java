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
    @Autowired
    private ItemRepository itemRepository;

    @PostConstruct
    public void init() {

        User user1 = new User("Zawialoff", "Alexis", "29/03/1996", "alexis@gmail.com", "azerty");
        User user2 = new User("Adaine-Jean-Pierre", "Axel", "29/03/1996", "axel.adainejeanpierre@viacesi.fr", "azerty");
        User user3 = new User("Lesaffre", "FX", "12/09/1982", "francoisxavier.lesaffre@viacesi.fr", "120982");
        User user4 = new User("Etourneau", "Marc", "29/03/1996", "marc.etourneau@viacesi.fr", "azerty");

        userRepository.save(Arrays.asList(user1, user2, user3, user4));

        Building building1 = new Building("Business Unit", null);
        Building building2 = new Building("Centre de Services", null);
        Building building3 = new Building("Centre des Serveurs", null);

        buildingRepository.save(Arrays.asList(building1, building2, building3));

        Map map1 = new Map("Etage 2 - Batiment 1 - Atos Intégration", "plan1.png", building1, null);
        Map map2 = new Map("Etage 4 - Batiment 3 - Atos Intégration", "plan2.jpg", building2, null);
        Map map3 = new Map("Open Space A", "plan2.jpg", building1, null);

        mapRepository.save(Arrays.asList(map1, map2, map3));

        Desk desk1 = new Desk("Bureau du CP", 282.0, 293.0, 0, map1);

        deskRepository.save(Arrays.asList(desk1));

        Person person1 = new Person("Ken", "Kaneki", "ICD", null);
        Person person2 = new Person("Shirou", "Emiya", "CP", null);
        Person person3 = new Person("Izuku", "Midoriya", "DEV", null);
        Person person4 = new Person("Marc", "ETOURNEAU", "DEV", null);
        Person person5 = new Person("Alexis", "ZAWIALOFF", "DEV", null);
        Person person6 = new Person("Axel", "ADAINEJP", "Chef de Projet", desk1);
        Person person7 = new Person("FX", "LESAFFRE", "DEV", null);

        personRepository.save(Arrays.asList(person1, person2, person3, person4, person5, person6, person7));

        Item item1 = new Item("Telephone", person6, desk1);
        Item item2 = new Item("Imprimante", null, desk1);
        Item item3 = new Item("EcranYBC1", null, null);
        Item item4 = new Item("EcranYBC2", null, null);

        itemRepository.save(Arrays.asList(item1, item2, item3, item4));

    }
}
