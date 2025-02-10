package com.example.likelion_board.service;


import com.example.likelion_board.domain.Board;
import com.example.likelion_board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;

    public Page<Board> findAllPages(Pageable pageable){
        Pageable pageRequest = PageRequest.of(pageable.getPageNumber(),pageable.getPageSize(),
                Sort.by(Direction.DESC,"updated_at"));

        return boardRepository.findAll(pageRequest);
    }


    @Transactional(readOnly = true)
    public Board findBoard(Long id){
        return boardRepository.findById(id).orElse(null); //예외처리 추가
    }

    public Board editBoard(Board board){
        return boardRepository.save(board);

    }


}
