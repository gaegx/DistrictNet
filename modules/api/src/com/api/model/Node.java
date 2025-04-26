package com.api.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;


@Entity
public class Node {
    @Id
    @GeneratedValue(strategy=GenerationType.IDETITY)
    

    
}
