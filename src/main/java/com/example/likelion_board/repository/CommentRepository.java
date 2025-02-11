package com.example.likelion_board.repository;

import com.example.likelion_board.domain.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CommentRepository extends CrudRepository<Comment,Long>, PagingAndSortingRepository<Comment,Long> {
    Page<Comment> findByBoardId(Long board_id, Pageable pageable);

}
