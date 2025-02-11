package com.example.likelion_board.domain;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentEdit {
    Long id;
    Long boardId;
    String commentContent;
    String commentPassword;

}
