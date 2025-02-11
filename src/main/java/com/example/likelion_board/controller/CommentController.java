package com.example.likelion_board.controller;


import com.example.likelion_board.domain.Board;
import com.example.likelion_board.domain.Comment;
import com.example.likelion_board.domain.CommentEdit;
import com.example.likelion_board.service.BoardService;
import com.example.likelion_board.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/comment")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;
    private final BoardService boardService;


    @PostMapping("/add")
    public String addComment(@RequestParam("boardId") Long boardId, @RequestParam("currentPage") int currentPage,
                             @ModelAttribute Comment comment) {

        commentService.addComment(comment);

        return "redirect:/board/view?id=" + boardId + "&currentPage=" + currentPage;
    }

    @PostMapping("/edit")
    public String editComment(@RequestParam("id") Long commentId, @RequestParam("boardId") Long boardId,
                              @RequestParam("currentPage") int currentPage,
                              @ModelAttribute CommentEdit commentEdit, Model model){

        try {
            commentService.editComment(commentEdit);
        } catch (RuntimeException e) {
            int page =1;
            int size =5;
            Pageable commentPageable = PageRequest.of(page-1,size);
            model.addAttribute("editErrorMessage", e.getMessage());
            model.addAttribute("currentPage", currentPage);
            model.addAttribute("board",boardService.findBoard(boardId));
            model.addAttribute("comments",commentService.findAllComment(boardId,commentPageable));

            return "board/view";
        }


        return "redirect:/board/view?id=" + boardId + "&currentPage=" + currentPage;
    }

    @PostMapping("/delete")
    public String deleteComment(@RequestParam("id") Long commentId, @RequestParam("boardId") Long boardId,
                              @RequestParam("currentPage") int currentPage,
                              @ModelAttribute CommentEdit commentEdit, Model model){
        try {

            commentService.deleteComment(commentEdit);
        } catch (RuntimeException e) {
            int page =1;
            int size =5;
            Pageable commentPageable = PageRequest.of(page-1,size);
            model.addAttribute("deleteErrorMessage", e.getMessage());
            model.addAttribute("currentPage", currentPage);
            model.addAttribute("board",boardService.findBoard(boardId));
            model.addAttribute("comments",commentService.findAllComment(boardId,commentPageable));


            return "board/view";
        }


        return "redirect:/board/view?id=" + boardId + "&currentPage=" + currentPage;
    }



}
