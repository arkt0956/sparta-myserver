package com.nbcamp.myserver.repository;

import com.nbcamp.myserver.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Long> {
    List<Board> findAllByUserId(Long userId);
    Optional<Board> findByIdAndUserId(Long id, Long userId);
}
