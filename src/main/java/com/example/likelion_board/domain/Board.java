package com.example.likelion_board.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Board {

    @Id
    private Long id;
    private String name;
    private String title;
    private String password;
    private String content;
    private String created_at;
    private String updated_at;

}
