package kr.ac.daegu.springbootapi.board.service;

import kr.ac.daegu.springbootapi.board.model.BoardDAO;
import kr.ac.daegu.springbootapi.board.model.BoardDTO;
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

    public List<BoardDTO> getBoardList() {
        return boardDAO.getBoardList();
    }

    public BoardDTO postBoard(BoardDTO boardDTO) throws Exception {
        log.debug(boardDTO.toString());

        // Inseted Date, Time
        boardDTO.setWriteDate(LocalDate.now());
        boardDTO.setWriteTime(LocalTime.now());

        int insertedRowCount = boardDAO.postBoard(boardDTO);
        if(insertedRowCount > 0){
            return boardDTO;
        } else {
            throw new Exception("failed to insert board data");
        }
    }

    public String putBoard(int id, BoardDTO boardDTO) throws Exception {
        // author,
        // content,
        // subject,
        // content
        // writeDate, writeTime 업데이트
        boardDTO.setId(id);
        boardDTO.setWriteDate(LocalDate.now());
        boardDTO.setWriteTime(LocalTime.now());
        int result = boardDAO.putBoard(boardDTO);

        if(result > 0){
            return result + " rows updated";
        }
        throw new Exception("failed to update " + id + "content");
    }

    // 글 읽기

    public ApiResponse<BoardDTO> getBoardById(int id) {
        BoardDTO data = boardDAO.getBoardById(id);
        return new ApiResponse(true, data);
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
