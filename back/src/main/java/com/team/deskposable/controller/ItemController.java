package com.team.deskposable.controller;

import com.team.deskposable.entity.Item;
import com.team.deskposable.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by fx on 28/07/17.
 */
@RestController
@RequestMapping("/items")
public class ItemController {

    @Autowired
    ItemRepository itemRepository;

    @GetMapping
    public @ResponseBody Iterable<Item> readAllItems () {
        return itemRepository.findAll();
    }

    @GetMapping("/{id}")
    public @ResponseBody Item readItem (@PathVariable("id") String id) {
        return itemRepository.findOne(Long.parseLong(id));
    }

    @PostMapping
    public @ResponseBody Item createItem (@RequestParam String label) {
        Item i = new Item();
        i.setLabel(label);
        itemRepository.save(i);
        return i;
    }

    @DeleteMapping("/{id}")
    public @ResponseBody Item deleteItem (@PathVariable Long id) {
        Item i = itemRepository.findOne(id);
        itemRepository.delete(i);
        return i;
    }

    @PutMapping("/{id}")
    public @ResponseBody Item updateItem (@PathVariable Long id, @RequestBody Item item) {
        Item i = itemRepository.findOne(id);
        i.setLabel(item.getLabel());
        itemRepository.save(i);
        return i;
    }
}
