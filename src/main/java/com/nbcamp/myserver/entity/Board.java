package com.nbcamp.myserver.entity;

import com.nbcamp.myserver.dto.BoardRequestDto;
import com.nbcamp.myserver.dto.BoardResponseDto;
import com.nbcamp.myserver.dto.CommentRequestDto;
import com.nbcamp.myserver.dto.CreateBoardDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

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
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String context;

    @Column(nullable = false)
    private Long userId;

    @OneToMany
    private List<Comment> comments = new ArrayList<>();

    public Board(BoardRequestDto dto, Long userId) {
        this.title = dto.getTitle();
        this.username = dto.getUsername();
        this.password = dto.getPassword();
        this.context = dto.getContext();
        this.userId = userId;
    }

    public BoardResponseDto createResponse(CreateBoardDto createBoardDto) {
        BoardResponseDto boardResponseDto = createBoardDto.create(id, title, username, context, super.getCreatedAt().toString());
        return boardResponseDto;
    }

    public void update(BoardRequestDto dto) {
        this.title = dto.getTitle();
        this.username = dto.getUsername();
        this.password = dto.getPassword();
        this.context = dto.getContext();
    }

    public void postComment(Comment comment) {
        this.comments.add(comment);
    }
}
