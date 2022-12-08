package com.nbcamp.myserver.repository;

import com.nbcamp.myserver.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {

}
