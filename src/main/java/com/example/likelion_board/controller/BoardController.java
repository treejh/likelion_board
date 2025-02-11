package com.example.likelion_board.controller;


import com.example.likelion_board.domain.Board;
import com.example.likelion_board.domain.Comment;
import com.example.likelion_board.service.BoardService;
import com.example.likelion_board.service.CommentService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;
    private final CommentService commentService;



    @GetMapping("/list")
    public String List(Model model, @RequestParam(name="page", required = false, defaultValue = "1") int page,
                       @RequestParam(name="size", required = false, defaultValue = "5") int size){
        Pageable pageable = PageRequest.of(page-1,size);
        model.addAttribute("board",boardService.findAllPages(pageable));
        model.addAttribute("currentPage",page);


        return "board/list";
    }

    @GetMapping("/add")
    public String addBoard(Model model){
        model.addAttribute(new Board());
        return "board/add";
    }

    @PostMapping("/add")
    public String addBoard(@ModelAttribute Board board){
        boardService.addBoard(board);

        return "redirect:/board/list";
    }

    @GetMapping("/view")
    public String viewBoard(@RequestParam("id") Long id, @RequestParam("currentPage") int currentPage,
                            @RequestParam(name="page", required = false, defaultValue = "1") int page,
                            @RequestParam(name="size", required = false, defaultValue = "5") int size,
                            Model model){
        Pageable commentPageable = PageRequest.of(page-1,size);
        model.addAttribute("board",boardService.findBoard(id));
        model.addAttribute("currentPage",currentPage);
        model.addAttribute("comments",commentService.findAllComment(id,commentPageable));

        return "board/view";
    }


    @GetMapping("/edit")
    public String editBoard(@RequestParam("id") Long id, @RequestParam("currentPage") int currentPage, Model model){
        model.addAttribute("board",boardService.findBoard(id));
        model.addAttribute("currentPage",currentPage);
        return "board/edit";
    }

    @PostMapping("/edit")
    public String editBoard(@RequestParam("id") Long id, @RequestParam("currentPage") int currentPage,
                            @ModelAttribute Board board,Model model){
        try{
            boardService.editBoard(board);
        }
        catch (RuntimeException e){
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("board",board);
            model.addAttribute("currentPage",currentPage);
            return "board/edit";
        }
        return "redirect:/board/list";

    }

    @PostMapping("/delete")
    public String deleteBoard(@RequestParam("id") Long id, @RequestParam("currentPage") int currentPage,
                              @ModelAttribute Board board,Model model){
        try{
            boardService.deleteBoard(board);
        }
        catch (RuntimeException e){
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("board",board);
            model.addAttribute("currentPage",currentPage);
            return "board/delete";
        }
        return "redirect:/board/list";
    }

    @GetMapping("/delete")
    public String deleteBoard(@RequestParam("id") Long id, @RequestParam("currentPage") int currentPage,
                              Model model){

        model.addAttribute("board",boardService.findBoard(id));
        model.addAttribute("currentPage",currentPage);
        return "board/delete";
    }






}
