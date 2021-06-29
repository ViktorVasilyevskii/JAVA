package com.todolist.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table
@Data
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String name;

    public Task(){}

    public Task(String taskName) {
        this.name = taskName;
    }
}

