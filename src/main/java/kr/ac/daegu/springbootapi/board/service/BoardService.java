package kr.ac.daegu.springbootapi.board.service;

import kr.ac.daegu.springbootapi.board.model.BoardDAO;
import kr.ac.daegu.springbootapi.board.model.BoardDTO;
import kr.ac.daegu.springbootapi.comment.model.CommentDAO;
import kr.ac.daegu.springbootapi.comment.model.CommentDTO;
import kr.ac.daegu.springbootapi.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class BoardService {

    public final BoardDAO boardDAO;
    public final CommentDAO commentDAO;

    public List<BoardDTO> getBoardList() {
        return boardDAO.getBoardList();
    }

    public BoardDTO postBoard(BoardDTO boardDTO) throws Exception {
        log.debug(boardDTO.toString());

        // Inserted Date, Time
        boardDTO.setWriteDate(LocalDate.now());
        boardDTO.setWriteTime(LocalTime.now());

        int insertedRowCount = boardDAO.postBoard(boardDTO);
        if(insertedRowCount > 0){
            return boardDTO;
        } else {
            throw new Exception("failed to insert board data");
        }
    }

    public ApiResponse<?> putBoard(int id, BoardDTO boardDTO) throws Exception {
        // board의 비번 검사
        String boardPassword = boardDAO.getBoardById(id).getPassword();
        if(isBoardPasswordMisMatch(boardDTO, boardPassword)){
            return new ApiResponse<>(false, "board password is not match");
        }

        boardDTO.setId(id);
        boardDTO.setWriteDate(LocalDate.now());
        boardDTO.setWriteTime(LocalTime.now());
        int result = boardDAO.putBoard(boardDTO);

        if(result > 0){
            return new ApiResponse<>(true, result + "rows updated");
        }
        throw new Exception("failed to update " + id + "content");
    }

    private boolean isBoardPasswordMisMatch(BoardDTO boardDTO, String boardPassword) {
        log.debug("boardPassword="+boardPassword);
        log.debug("requestPassword="+boardDTO.getPassword());
        boolean isMisMatch = !boardDTO.getPassword().equals(boardPassword);
        log.debug("isMisMatch=" + isMisMatch);
        return isMisMatch;
    }

    // 글 읽기

    public ApiResponse<BoardDTO> getBoardById(int id) {
        BoardDTO data = boardDAO.getBoardById(id);
        if(isDeletedData(data)){
            return new ApiResponse(false, "boardId " + id + " is already deleted" );
        }
        List<CommentDTO> commentsById = commentDAO.getCommentsByBoardId(id);
        data.setComments(commentsById);
        return new ApiResponse(true, data);
    }

    private boolean isDeletedData(BoardDTO data) {
        return data.getIsDel().equals("Y");
    }


    // Board테이블의 isDel 컬럼의 데이터를 'Y'로 업데이트
    public ApiResponse<BoardDTO> updateIsDelBoardById(int id){
        int updatedRow = boardDAO.updateIsDelBoardById(id);
            if(updatedRow > 0){
                return new ApiResponse(true, "board id " + id + " is successfully deleted");
            }
            return new ApiResponse(false, "failed to delete board id " + id);
    }
}
