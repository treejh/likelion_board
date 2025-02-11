package com.example.likelion_board.service;


import com.example.likelion_board.domain.Board;
import com.example.likelion_board.repository.BoardRepository;
import java.time.LocalDateTime;
import java.util.List;
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

    public void addBoard(Board board){
        board.setCreated_at(LocalDateTime.now());
        board.setUpdated_at(LocalDateTime.now());
        boardRepository.save(board);
    }

    public Page<Board> findAllPages(Pageable pageable) {
        Pageable pageRequest = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(),
                Sort.by(Direction.DESC, "updated_at"));


        return boardRepository.findAll(pageRequest);
    }


    @Transactional(readOnly = true)
    public Board findBoard(Long id) {
        return boardRepository.findById(id).orElse(null); //예외처리 추가
    }

    public Board editBoard(Board newBoard) {
        Board board = boardRepository.findById(newBoard.getId())
                .orElseThrow(() -> new RuntimeException("없는 게시판 입니다."));

        if (newBoard.getPassword().equals(board.getPassword())){
            board.setName(newBoard.getName());
            board.setTitle(newBoard.getTitle());
            board.setContent(newBoard.getContent());
            board.setUpdated_at(LocalDateTime.now());
            return boardRepository.save(board);
        }

        throw new RuntimeException("비밀번호가 같지 않습니다");

    }

    public void deleteBoard(Board newBoard){
        Board board = boardRepository.findById(newBoard.getId())
                .orElseThrow(() -> new RuntimeException("없는 게시판 입니다."));
        String password = newBoard.getPassword();
        String password2 = board.getPassword();

        if (password.equals(password2)){
            boardRepository.deleteById(newBoard.getId());
            return;
        }

        throw new RuntimeException("비밀번호가 같지 않습니다");

    }

}



