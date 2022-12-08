package com.nbcamp.myserver.entity;

import com.nbcamp.myserver.dto.BoardRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Board extends Timestamped{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String author;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String context;

    public Board(BoardRequestDto dto) {
        this.title = dto.getTitle();
        this.author = dto.getAuthor();
        this.password = dto.getPassword();
        this.context = dto.getContext();
    }

    public void update(BoardRequestDto dto) {
        this.title = dto.getTitle();
        this.author = dto.getAuthor();
        this.password = dto.getPassword();
        this.context = dto.getContext();
    }
}
