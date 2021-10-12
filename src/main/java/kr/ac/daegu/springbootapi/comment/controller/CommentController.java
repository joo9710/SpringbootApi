package kr.ac.daegu.springbootapi.comment.controller;

import kr.ac.daegu.springbootapi.comment.model.CommentDTO;
import kr.ac.daegu.springbootapi.comment.service.CommentService;
import kr.ac.daegu.springbootapi.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping(value = "/comment")

public class CommentController {
    public final CommentService commentService;


    //댓글 목록 불러오기
   @GetMapping(value = "/{boardId}")
   public ApiResponse<CommentDTO> getCommentsByBoardId(@PathVariable int boardId) throws Exception{
        return commentService.getCommentsByBoardId(boardId);
   }

    // 댓글 쓰기
    @PostMapping(value = "/")
    public ApiResponse<CommentDTO> postComment(@RequestBody CommentDTO commentDTO) throws Exception {
        return commentService.postComment(commentDTO);

    }




}


