package com.onlinepowers.board;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.onlinepowers.board.domain.Board;
import com.onlinepowers.board.domain.BoardCfg;
import com.onlinepowers.board.domain.BoardComment;
import com.onlinepowers.board.domain.BoardSearchParam;
import com.onlinepowers.board.support.BoardContext;
import com.onlinepowers.framework.file.domain.TempFiles;
import com.onlinepowers.framework.file.domain.UploadFile;

public interface BoardService {
	public BoardCfg getBoardCfg(String boardCode);
	
	public List<BoardCfg> getBoardCfgList();

	public int getBoardCount(BoardSearchParam boardSearchParam);

	public List<Board> getBoardList(BoardSearchParam boardSearchParam);

	public Board getBoardByContext(BoardContext boardContext);
	
	public Board getBoardView(BoardContext boardContext);
	
	/**
	 * 글 등록 
	 * @param board
	 * @param tempFiles
	 */
	public void insertBoard(Board board, TempFiles tempFiles);
	
	
	/**
	 * 글 수정.
	 * @param board
	 * @param tempFiles
	 */
	public void updateBoard(Board board, TempFiles tempFiles);
	

	/**
	 * 글 등록, 수정, 답변글 등록 처리. (Multipart로 파일 업로드 하는 경우)
	 * @param board
	 * @param mode
	 * @param multipartFiles
	 * @param tempFileIds
	 */
	public void save(Board board, String mode, MultipartFile[] multipartFiles, int[] tempFileIds);
	
	
	/**
	 * 글 등록, 수정, 답변글 등록 처리.
	 * @param board 게시물 데이
	 * @param mode 등록 구분 
	 * @param tempfiles 임시 저장 파일 정
	 */
	public void save(Board board, String mode, TempFiles tempFiles);

	public List<UploadFile> getBoardFileList(Board board);

	public void deleteBoard(Board board);

	public Board getBoard(Board boardParam);

	
	/**
	 * 첨부된 파일을 삭제한다.
	 * @param board
	 * @param fileId
	 */
	public void deleteFile(Board board, int fileId);

	
	/**
	 * 해당 게시물의 댓글 수를 가져온다.
	 * @param boardSearchParam
	 * @return
	 */
	public int getBoardCommentCount(BoardSearchParam boardSearchParam);

	
	/**
	 * 해당 게시물의 댓글 목록을 가져온다.
	 * @param boardSearchParam
	 * @return
	 */
	public List<BoardComment> getBoardCommentList(BoardSearchParam boardSearchParam);

	
	/**
	 * 댓글을 등록한다.
	 * @param boardComment
	 */
	public void insertComment(BoardComment boardComment);

	
	/**
	 * 댓글을 삭제한다.
	 * @param boardComment
	 */
	public void deleteComment(BoardComment boardComment);

	
	/**
	 * 댓글을 가져온다.
	 * @param boardComment
	 * @return
	 */
	public BoardComment getBoardComment(BoardComment boardComment);

	
	/**
	 * 이전 게시물 정보를 가져온다.
	 * @param board
	 * @return
	 */
	public Board getBoardPrev(BoardSearchParam boardSearchParam);

	
	/**
	 * 다음 게시물 정보를 가져온다.
	 * @param board
	 * @return
	 */
	public Board getBoardNext(BoardSearchParam boardSearchParam);
	
	
	/**
	 * 게시판의 최신 글 목록을 가져옴
	 * @param boardSearchParam
	 * @return
	 */
	public List<Board> getLatestList(BoardSearchParam boardSearchParam);

	
	/**
	 * 공지 기능이 있는 경우 공지 목록을 가져옴
	 * @param boardCfg
	 * @return
	 */
	public List<Board> getNoticeList(BoardCfg boardCfg);
	
	/**
	 * (관리자) 게시판 코드 중복 여부 체크
	 * @param boardCode
	 * @return
	 */
	public int getBoardCfgCountByBoardCode(String boardCode);
	
	/**
	 * (관리자) 게시판 설정값 삽입
	 * @param boardCfg
	 */
	public void insertBoardCfg(BoardCfg boardCfg);
	
	/**
	 * (관리자) 게시판 설정 글 개수 조회
	 * @param searchParam
	 * @return
	 */
	public int getBoardCfgCount(BoardSearchParam searchParam);
	
	/**
	 * (관리자) 게시판 설정 글 목록 조회
	 * @param searchParam
	 * @return
	 */
	public List<BoardCfg> getBoardCfgListByParam(BoardSearchParam searchParam);
	
	/**
	 * (관리자) 게시판 설정 글 상세 조회
	 * @param boardCode
	 * @return
	 */
	public BoardCfg getBoardCfgByBoardCode(String boardCode);
	
	/**
	 * (관리자) 게시판 설정 데이터 수정
	 * @param boardCfg
	 */
	public void updateBoardCfg(BoardCfg boardCfg);
	
	/**
	 * (관리자) 게시판 설정 데이터 삭제 (STATUS_CODE)
	 * @param boardCode
	 */
	public void updateBoardCfgStatusCode(String boardCode);
}
