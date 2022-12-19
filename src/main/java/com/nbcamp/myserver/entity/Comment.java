package com.nbcamp.myserver.entity;

import com.nbcamp.myserver.dto.CommentResponseDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Getter
@NoArgsConstructor
public class Comment {
    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String context;

    @ManyToOne
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "BOARD_ID", nullable = false)
    private Board board;

    public Comment(String context, User user, Board board) {
        this.context = context;
        this.user = user;
        this.board = board;
    }

    public CommentResponseDto createResponse() {
        return new CommentResponseDto(id, board.getId(), user.getUsername(), context);
    }
}
