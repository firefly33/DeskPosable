package com.team.deskposable.controller;

import com.team.deskposable.repository.DeskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by fx on 28/07/17.
 */
@RestController
@RequestMapping("/desks")
public class DeskController {

    @Autowired
    DeskRepository DeskRepository;

}
