package com.example.likelion_board.controller;


import com.example.likelion_board.domain.Board;
import com.example.likelion_board.service.BoardService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;


    @GetMapping("/list")
    public String List(Model model, @RequestParam(name="page", required = false, defaultValue = "1") int page,
                       @RequestParam(name="size", required = false, defaultValue = "5") int size){
        Pageable pageable = PageRequest.of(page-1,size);
        model.addAttribute("board",boardService.findAllPages(pageable));
        model.addAttribute("currentPage",page);

        List<Board> a = boardService.findAllPages(pageable).get().collect(Collectors.toList());
        for(Board boad : a){
            System.out.println(boad.getName());
        }

        return "board/list";
    }

    @GetMapping("/view")
    public String viewBoard(@RequestParam("id") Long id, @RequestParam("currentPage") int currentPage, Model model){
        System.out.println("hihihihihi222222");
        model.addAttribute("board",boardService.findBoard(id));
        System.out.println(boardService.findBoard(id).getTitle());
        System.out.println("hihihihihi");
        model.addAttribute("currentPage",currentPage);

        return "board/view";
    }




}
