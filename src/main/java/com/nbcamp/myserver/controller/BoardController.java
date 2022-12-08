package com.nbcamp.myserver.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class BoardController {
    @GetMapping("/api/showall")
    @ResponseBody
    public String showAll() {

    }

    @PostMapping("/api/posting")
    @ResponseBody
    public String posting() {

    }

    @GetMapping("/api/show")
    @ResponseBody
    public String show() {

    }

    @PutMapping("/api/edit")
    @ResponseBody
    public String edit() {

    }

    @DeleteMapping("/api/delete")
    @ResponseBody
    public String delete() {

    }
}
