package com.example.likelion_board.service;


import com.example.likelion_board.domain.Board;
import com.example.likelion_board.domain.Comment;
import com.example.likelion_board.domain.CommentEdit;
import com.example.likelion_board.repository.CommentRepository;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;

    public void addComment(Comment comment){
        comment.setCreated_at(LocalDateTime.now());
        comment.setUpdated_at(LocalDateTime.now());
        commentRepository.save(comment);

    }

    public Page<Comment> findAllComment(Long board_id,Pageable pageable) {
        Pageable pageRequest = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(),
                Sort.by(Direction.DESC, "updated_at"));

        return commentRepository.findByBoardId(board_id,pageRequest);
    }


    public Comment editComment(CommentEdit commentEdit) {
        Comment comment = commentRepository.findById(commentEdit.getId())
                .orElseThrow(() -> new RuntimeException("없는 댓글 입니다."));



        if (comment.getPassword().equals(commentEdit.getCommentPassword())){
            comment.setContent(commentEdit.getCommentContent());
            comment.setUpdated_at(LocalDateTime.now());

            return commentRepository.save(comment);
        }

        throw new RuntimeException("비밀번호가 같지 않습니다");

    }

    public void deleteComment(CommentEdit commentEdit) {
        Comment comment = commentRepository.findById(commentEdit.getId())
                .orElseThrow(() -> new RuntimeException("없는 댓글 입니다."));


        if (comment.getPassword().equals(commentEdit.getCommentPassword())){
                commentRepository.delete(comment);
                return;
        }

        throw new RuntimeException("비밀번호가 같지 않습니다");

    }
}
