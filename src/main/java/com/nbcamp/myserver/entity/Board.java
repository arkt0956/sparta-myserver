package com.nbcamp.myserver.entity;

import jakarta.persistence.Column;

public class Board extends Timestamped{
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String author;
    @Column(nullable = false)
    private int password;
    @Column(nullable = false, unique = true)
    private String context;

}
