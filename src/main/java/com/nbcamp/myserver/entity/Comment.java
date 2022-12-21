package com.nbcamp.myserver.entity;

import com.nbcamp.myserver.dto.CommentRequestDto;
import com.nbcamp.myserver.dto.CommentResponseDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Getter
@NoArgsConstructor
public class Comment extends Timestamped {
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
        return new CommentResponseDto(id, board.getId(), user.getUsername(), context, getCreatedAt().toString());
    }

    public void update(CommentRequestDto commentRequestDto) {
        this.context = commentRequestDto.getContext();
    }

    @Override
    public String toString() {
        return "{" + System.lineSeparator() +
                "id=" + id +
                ", context='" + context + '\'' +
                ", user=" + user.getUsername() +
                ", board=" + board.getId() +
                ", createAt=" + getCreatedAt() +
                System.lineSeparator() +"}";
    }

//    @Override
//    public int compareTo(Comment comment) {
//        if(this.getCreatedAt().isBefore(comment.getCreatedAt())) {
//            return -1;
//        } else if (this.getCreatedAt().isAfter(comment.getCreatedAt())) {
//            return 1;
//        } else {
//            return 0;
//        }
//    }
}
