package com.onlinepowers.board;

import java.util.List;

import com.onlinepowers.board.domain.Board;
import com.onlinepowers.board.domain.BoardCfg;
import com.onlinepowers.board.domain.BoardComment;
import com.onlinepowers.board.domain.BoardNew;
import com.onlinepowers.board.domain.BoardSearchParam;
import com.onlinepowers.board.support.BoardContext;
import com.onlinepowers.framework.orm.mybatis.annotation.Mapper;

@Mapper("boardMapper")
public interface BoardMapper {
	BoardCfg getBoardCfg(String boardCode);

	void updateBoardCfgNoticeBoardId(BoardCfg boardCfg);
	
	int getBoardCount(BoardSearchParam boardSearchParam);

	List<Board> getBoardList(BoardSearchParam boardSearchParam);

	Board getBoardByContext(BoardContext boardContext);

	void addHitCount(BoardContext boardContext);

	int getNewGroupId(Board board);

	void insertBoard(Board board);

	void insertBoardNew(BoardNew boardNew);

	List<String> getNoticeBoardIdList(Board board);

	List<String> creationDateList(String requestToken);

	Board getBoard(Board board);

	void updateBoard(Board board);

	void updateBoardNew(Board board);

	void updateBoardStep(Board boardData);

	int getBoardCountByParentId(Board board);

	void deleteBoardRecommend(Board board);

	void deleteBoardComment(Board board);

	void deleteBoardFile(Board board);

	void deleteBoardNew(Board board);

	void deleteBoardTag(Board board);

	void deleteBoard(Board board);

	int getBoardCommentCount(BoardSearchParam boardSearchParam);

	List<BoardComment> getBoardCommentList(BoardSearchParam boardSearchParam);

	void insertComment(BoardComment boardComment);

	void updateCommentCount(BoardComment boardComment);

	void deleteComment(BoardComment boardComment);

	BoardComment getBoardComment(BoardComment boardComment);

	Board getBoardPrev(BoardSearchParam boardSearchParam);

	Board getBoardNext(BoardSearchParam boardSearchParam);

	
	/**
	 * 게시판의 최신글 목록을 가져옴.
	 * @param boardSearchParam
	 * @return
	 */
	List<Board> getLatestList(BoardSearchParam boardSearchParam);

	
	/**
	 * 공지 기능이 있는 경우 공지 목록을 가져옴
	 * @param boardCfg
	 * @return
	 */
	List<Board> getNoticeList(BoardCfg boardCfg);

	Board getBoardPrevBySortField(BoardSearchParam boardSearchParam);

	Board getBoardNextBySortField(BoardSearchParam boardSearchParam);

	/**
	 * 게시판 설정 정보 목록.
	 * @return
	 */
	List<BoardCfg> getBoardCfgList();

	/**
	 * (관리자) 게시판 코드 중복여부 체크
	 * @param boardCode
	 * @return
	 */
	int getBoardCfgCountByBoardCode(String boardCode);
	
	/**
	 * (관리자) 게시판 설정값 삽입
	 * @param boardCfg
	 */
	void insertBoardCfg(BoardCfg boardCfg);
	
	/**
	 * (관리자) 게시판 설정 글 개수 조회
	 * @param searchParam
	 * @return
	 */
	int getBoardCfgCount(BoardSearchParam searchParam);
	
	/**
	 * (관리자) 게시판 설정 글 목록 조회
	 * @param searchParam
	 * @return
	 */
	List<BoardCfg> getBoardCfgListByParam(BoardSearchParam searchParam);
	
	/**
	 * (관리자) 게시판 설정 글 상세 조회
	 * @param boardCode
	 * @return
	 */
	BoardCfg getBoardCfgByBoardCode(String boardCode);
	
	/**
	 * (관리자) 게시판 설정 데이터 수정
	 * @param boardCfg
	 */
	void updateBoardCfg(BoardCfg boardCfg);
	
	/**
	 * (관리자) 게시판 설정 데이터 삭제 (STATUS_CODE)
	 * @param boardCode
	 */
	void updateBoardCfgStatusCode(String boardCode);
}
