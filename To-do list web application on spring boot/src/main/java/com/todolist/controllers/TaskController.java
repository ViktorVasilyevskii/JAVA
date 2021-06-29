package com.todolist.controllers;


import com.todolist.model.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;


@Controller
@RequestMapping("")
public class TaskController {

    @Autowired
    private TaskRepository taskRepository;

    @GetMapping
    public String index(Model model){
        Iterable<Task> tasks = taskRepository.findAll();
        model.addAttribute("tasks", tasks);
        return "index";
    }

    @PostMapping
    public String addTask(@RequestParam String textTask, Model model){

        Task task  = new Task(textTask);
        taskRepository.save(task);
        Iterable<Task> tasks = taskRepository.findAll();
        model.addAttribute("tasks", tasks);
        return "index";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") int id, Model model){
        if(!taskRepository.existsById(id)){
            return "index";
        }

        Optional<Task> task = taskRepository.findById(id);
        ArrayList<Task> res = new ArrayList<>();
        task.ifPresent(res::add);
        model.addAttribute("taskOne", res);
        return "edit";
    }

    @PostMapping("/edit/{id}")
    public String updateTask(@PathVariable("id") int id, @RequestParam String textTask, Model model){

        Task task  = taskRepository.findById(id).orElseThrow();
        task.setName(textTask);
        taskRepository.save(task);

        return "redirect:/";
    }

    @PostMapping("/remove/{id}")
    public String deleteTask(@PathVariable("id") int id, Model model){

        Task task  = taskRepository.findById(id).orElseThrow();
        taskRepository.delete(task);

        return "redirect:/";
    }

}
