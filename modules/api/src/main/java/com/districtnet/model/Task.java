package com.districtnet.model;

import jakarta.persistence.*;

import java.lang.annotation.Inherited;

import javax.annotation.processing.Generated;

@Entity
@Table(name="task")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="task_id")
    Long id;
    
}
