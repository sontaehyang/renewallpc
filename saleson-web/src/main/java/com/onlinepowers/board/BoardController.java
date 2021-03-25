/*
 * Copyright(c) 2009-2011 Onlinepowers Development Team
 * http://www.onlinepowers.com
 * 
 * @file com.onlinepowers.framework.web.board.BoardController.java
 * @author skc
 * @date 2011. 06. 02.
 */
package com.onlinepowers.board;

import com.onlinepowers.board.domain.Board;
import com.onlinepowers.board.domain.BoardCfg;
import com.onlinepowers.board.domain.BoardComment;
import com.onlinepowers.board.domain.BoardSearchParam;
import com.onlinepowers.board.support.BoardContext;
import com.onlinepowers.framework.context.RequestContext;
import com.onlinepowers.framework.exception.BusinessException;
import com.onlinepowers.framework.exception.NotAjaxRequestException;
import com.onlinepowers.framework.file.domain.TempFiles;
import com.onlinepowers.framework.file.domain.UploadFile;
import com.onlinepowers.framework.file.service.FileService;
import com.onlinepowers.framework.file.view.FileDownloadView;
import com.onlinepowers.framework.util.*;
import com.onlinepowers.framework.web.bind.annotation.RequestProperty;
import com.onlinepowers.framework.web.pagination.Pagination;
import com.onlinepowers.framework.web.servlet.view.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import saleson.common.Const;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping({"/board/*", "/m/board/*", "/opmanager/board/*"})
@RequestProperty(title="게시판")
public class BoardController {

	@Autowired
	private BoardService boardService;
	
	@Autowired FileService fileService;
	
	/**
	 * 게시판 리스트
	 * @param boardCode
	 * @param boardSearchParam
	 * @param requestContext
	 * @param model
	 * @return
	 */
	@GetMapping("{boardCode}")
	public String list(@ModelAttribute("boardSearchParam") BoardSearchParam boardSearchParam,
			RequestContext requestContext, BoardContext boardContext, Model model, HttpServletRequest request){
		boardSearchParam.setBoardCode(boardContext.getBoardCode());
		
		int itemPerPage = boardSearchParam.getItemsPerPage();
		boardSearchParam.setItemsPerPage(boardContext.getBoardCfg().getPageSize());
		boardSearchParam.setSortField(StringUtils.nullToString(boardContext.getBoardCfg().getSortField(), ""));
		
		if (itemPerPage > 0) {
			boardSearchParam.setItemsPerPage(itemPerPage);
		}
		
		int totalCount = boardService.getBoardCount(boardSearchParam);
		
		Pagination pagination = Pagination.getInstance(totalCount, boardContext.getBoardCfg().getPageSize());
		
		boardSearchParam.setPagination(pagination);
		
		List<Board> list = boardService.getBoardList(boardSearchParam);
		
		// 공지 기능이 있는 경우
		List<Board> noticeList = new ArrayList<>();
		
		if (boardContext.getBoardCfg().getUseNotice().equals("1")) {
			noticeList = boardService.getNoticeList(boardContext.getBoardCfg());
		}
		
		// 오늘 날짜 구하기
		String today = DateUtils.getToday(Const.DATETIME_FORMAT);

		model.addAttribute("today", today);
		model.addAttribute("list", list);
		model.addAttribute("noticeList", noticeList);
		model.addAttribute("pagination", pagination);
		model.addAttribute("boardSearchParam", boardSearchParam);
		model.addAttribute("totalCount", totalCount);
		return ViewUtils.getBoardView("list");
	}
	
	/**
	 * 게시판 리스트
	 * @param boardCode
	 * @param boardSearchParam
	 * @param requestContext
	 * @param model
	 * @return
	 */
	@GetMapping("{boardCode}/more")
	@RequestProperty(template="mobile", layout="blank")
	public String listAjax(@ModelAttribute("boardSearchParam") BoardSearchParam boardSearchParam,
			RequestContext requestContext, BoardContext boardContext, Model model, HttpServletRequest request){
		
		if (!requestContext.isAjaxRequest()) { 
		    throw new NotAjaxRequestException();
		}
		boardSearchParam.setBoardCode(boardContext.getBoardCode());
		
		int itemPerPage = boardSearchParam.getItemsPerPage();
		boardSearchParam.setItemsPerPage(boardContext.getBoardCfg().getPageSize());
		boardSearchParam.setSortField(StringUtils.nullToString(boardContext.getBoardCfg().getSortField(), ""));
		
		if (itemPerPage > 0) {
			boardSearchParam.setItemsPerPage(itemPerPage);
		}
		
		int totalCount = boardService.getBoardCount(boardSearchParam);
		
		Pagination pagination = Pagination.getInstance(totalCount, boardContext.getBoardCfg().getPageSize());
		
		boardSearchParam.setPagination(pagination);
		
		List<Board> list = boardService.getBoardList(boardSearchParam);
		
		// 오늘 날짜 구하기
		String today = DateUtils.getToday(Const.DATETIME_FORMAT);
		
		model.addAttribute("today", today);
		model.addAttribute("list", list);
		model.addAttribute("pagination", pagination);
		model.addAttribute("boardSearchParam", boardSearchParam);
		model.addAttribute("totalCount", totalCount);
		
		boardContext.getBoardCfg().setBoardLayout("blank");
		return ViewUtils.getBoardView("board-list");
	}
	

	/**
	 * 글 보기
	 * @param boardContext
	 * @param boardSearchParam
	 * @param model
	 * @return
	 */
	@GetMapping("{boardCode}/{boardId}")
	public String view(RequestContext requestContext, BoardContext boardContext, BoardSearchParam searchParam, Model model){
		if (!boardContext.getBoardAuthority().isReadAuthority()) {
			FlashMapUtils.alert(MessageUtils.getMessage("board.error.authority.read"));
			return ViewUtils.redirectBoard();
		}
		
		// 댓글 조회
		searchParam.setBoardCode(boardContext.getBoardCode());
		searchParam.setBoardId(boardContext.getBoardId());
		searchParam.setItemsPerPage(boardContext.getBoardCfg().getPageSize());
		
		Pagination pagination = Pagination.getInstance(boardService.getBoardCommentCount(searchParam));
		pagination.setLink("javascript:getCommentList(" + Pagination.REPLACE_PAGE_PATTERN + ")");
		
		searchParam.setPagination(pagination);
		
		// 글 조회
		Board board = boardService.getBoardView(boardContext);
		
		if (ValidationUtils.isNull(board)) {
			FlashMapUtils.alert(MessageUtils.getMessage("board.error.not.exists.article"));
			return ViewUtils.redirectBoard();
		}
		
		// 승인 기능을 사용하고 게시 승인대기 인 경우.
		if (boardContext.getBoardCfg().getUseApproval().equals("1")
				&& board.getStatusCode().equals("")) {
			FlashMapUtils.alert(MessageUtils.getMessage("board.error.before.approval"));
			return ViewUtils.redirectBoard();			
		}
		
		// 수정/삭제 권한 SET
		boardContext.getBoardAuthority().setModifyAndDeleteAuthority(board);
		
		// 리스트 검색 정보를 boardSearchParam에 입력한다.
		BoardSearchParam boardSearchParam = (BoardSearchParam) ConvertUtils.mapToObject(StringUtils.paramToMap(requestContext.getPrevPageUrl()), BoardSearchParam.class);
		boardSearchParam.setBoardCode(board.getBoardCode());
		boardSearchParam.setStep(board.getStep());
		boardSearchParam.setSortField(StringUtils.nullToString(boardContext.getBoardCfg().getSortField(), ""));
		boardSearchParam.setBoardId(board.getBoardId());
				
		//model.addAttribute("boardFiles", boardService.getBoardFileList(board));
		model.addAttribute("board", board);
		model.addAttribute("boardFiles", boardService.getBoardFileList(board));
		model.addAttribute("boardComment", new BoardComment());
		model.addAttribute("list", boardService.getBoardCommentList(searchParam));
		model.addAttribute("pagination", pagination);
		model.addAttribute("requestContext", requestContext);
		model.addAttribute("boardPrev", boardService.getBoardPrev(boardSearchParam));
		model.addAttribute("boardNext", boardService.getBoardNext(boardSearchParam));
		model.addAttribute("url", Base64Utils.encode(requestContext.getPrevPageUrl()));
		return ViewUtils.getBoardView("view");
	}
	
	/**
	 * 글 내용
	 * @param boardContext
	 * @param model
	 * @return
	 */
	@GetMapping("{boardCode}/{boardId}/viewContent")
	@RequestProperty(layout="blank")
	public String viewContent(BoardContext boardContext, Model model){
		if (!boardContext.getBoardAuthority().isReadAuthority()) {
			FlashMapUtils.alert(MessageUtils.getMessage("board.error.authority.read"));
			return ViewUtils.redirectBoard();
		}
		
		Board board = boardService.getBoardByContext(boardContext);
		
		if (ValidationUtils.isNull(board)) {
			FlashMapUtils.alert(MessageUtils.getMessage("board.error.not.exists.article"));
			return ViewUtils.redirectBoard();
		}
				
		model.addAttribute("board", board);
		return ViewUtils.getBoardView("viewContent");
	}
	
	/**
	 * 댓글
	 * @param boardContext
	 * @param model
	 * @return
	 */
	@GetMapping("{boardCode}/{boardId}/comment")
	@RequestProperty(layout="blank")
	public String comment(RequestContext requestContext, BoardContext boardContext, BoardSearchParam boardSearchParam, Model model){
		boardSearchParam.setBoardCode(boardContext.getBoardCode());
		boardSearchParam.setBoardId(boardContext.getBoardId());
		boardSearchParam.setItemsPerPage(boardContext.getBoardCfg().getPageSize());
		
		int count = boardService.getBoardCommentCount(boardSearchParam);
		
		Pagination pagination = Pagination.getInstance(count);
		boardSearchParam.setPagination(pagination);
		
		int currentCnt = boardService.getBoardCommentList(boardSearchParam).size();
		
		/** 페이지에 댓글 개수가 0개일 경우, page-1 // 2016-10-25_LSI */
		if (currentCnt == 0 && boardSearchParam.getPage()>1) {
			boardSearchParam.setPage(boardSearchParam.getPage()-1);
			count = boardService.getBoardCommentCount(boardSearchParam);
			pagination = Pagination.getInstance(count);
		}
		
		pagination.setLink("javascript:getCommentList(" + Pagination.REPLACE_PAGE_PATTERN + ")");
		
		boardSearchParam.setPagination(pagination);
		
		model.addAttribute("list", boardService.getBoardCommentList(boardSearchParam));
		model.addAttribute("pagination", pagination);
		model.addAttribute("requestContext", requestContext);
		model.addAttribute("boardContext", boardContext);
		return ViewUtils.getBoardView2("comment", "blank"); 
	}
	
	
	/**
	 * 댓글 저장
	 * @param boardContext
	 * @param boardComment
	 * @return
	 */
	@PostMapping("{boardCode}/{boardId}/comment")
	public JsonView commentAction(BoardContext boardContext, BoardComment boardComment) { 
		if (!boardContext.getBoardAuthority().isCommentAuthority() && !SecurityUtils.isManager()) {
			return JsonViewUtils.exception(MessageUtils.getMessage("board.error.authority.comment"));
		}
		
		boardService.insertComment(boardComment);

		return JsonViewUtils.success();
	}
	
	/**
	 * 댓글 삭제
	 * @param boardContext
	 * @param boardComment
	 * @return
	 */
	@PostMapping("{boardCode}/{boardId}/comment/delete")
	public JsonView commentDelete(BoardContext boardContext, Board boardParam, BoardComment boardCommentParam) {
		
		BoardComment boardComment = boardService.getBoardComment(boardCommentParam);
		
		if (ValidationUtils.isNull(boardComment)) {
			return JsonViewUtils.exception(MessageUtils.getMessage("board.error.not.exists.board.comment"));
		}
		
		
		boardContext.getBoardAuthority().setCommentModifyAndDeleteAuthority(boardComment, boardCommentParam);

		if (!boardContext.getBoardAuthority().isCommentDeleteAuthority()) {
			return JsonViewUtils.exception(MessageUtils.getMessage("board.error.authority.comment.delete"));
		}

		
		boardService.deleteComment(boardCommentParam);

		return JsonViewUtils.success();
	}

	
	/**
	 * 글쓰기 폼
	 * @param boardContext
	 * @param model
	 * @return
	 */
	@GetMapping("{boardCode}/write")
	public String write(BoardContext boardContext, Model model) {
		if (!boardContext.getBoardAuthority().isWriteAuthority()) {
			FlashMapUtils.alert(MessageUtils.getMessage("board.error.authority.write"));
			return ViewUtils.redirectBoard();
		}
		
		BoardCfg boardCfg = boardService.getBoardCfgByBoardCode(boardContext.getBoardCode());
		
		Board board = new Board();
		model.addAttribute("board", board);
		model.addAttribute("boardCfg", boardCfg);
		model.addAttribute("action", boardContext.getBoardBaseUri() + "/writeAction");
		//model.addAttribute("categoryList", BoardUtils.getCategoryList(boardContext.getBoardCfg().getCategoryList()));
		return ViewUtils.getBoardView("form");
	}
	
	
	/**
	 * 글 등록 처리
	 * @param requestContext
	 * @param multipartFiles
	 * @param tempFileIds
	 * @param board
	 * @return
	 */
	@PostMapping("{boardCode}/write")
	public String writeAction(TempFiles tempFiles,
			@PathVariable("boardCode") String boardCode,
			@Valid Board board, BindingResult bindingResult, BoardContext boardContext, HttpServletRequest request) {
		if (bindingResult.hasErrors()) {
			return ViewUtils.getBoardView("form", bindingResult);
		}
		
		try {
			//boardService.save(board, "write", multipartFiles, tempFileIds);
			board.setStatusCode("9");
			boardService.insertBoard(board, tempFiles);
		} catch (BusinessException e) {
			FlashMapUtils.alert(e.getMessage());
			return ViewUtils.redirectBoard("form");
		}
		
		FlashMapUtils.alert(MessageUtils.getMessage("board.message.insert.complete"));
		
		if (DeviceUtils.isMobile(request)) {
			System.out.println("sysout ||" +DeviceUtils.isMobile(request));
			return "redirect:/m/board/"+boardCode;
		}
		return ViewUtils.redirectBoard();
	}
	

	/**
	 * 글 수정 폼
	 * @param boardContext
	 * @param model
	 * @return
	 */
	@GetMapping("{boardCode}/{boardId}/edit")
	public String modify(BoardContext boardContext, Model model) {
		
		BoardCfg boardCfg = boardService.getBoardCfgByBoardCode(boardContext.getBoardCode());
		
		Board board = boardService.getBoardByContext(boardContext);
		if (ValidationUtils.isNull(board)) {
			FlashMapUtils.alert(MessageUtils.getMessage("board.error.not.exists.article"));
			return ViewUtils.redirectBoard();
		}
		
		boardContext.getBoardAuthority().setModifyAndDeleteAuthority(board);

		
		if (!boardContext.getBoardAuthority().isModifyAuthority()) {
			FlashMapUtils.alert(MessageUtils.getMessage("board.error.authority.modify"));
			return ViewUtils.redirectBoard();
		}
		
		model.addAttribute("board", board);
		model.addAttribute("boardCfg", boardCfg);
		model.addAttribute("boardFiles", boardService.getBoardFileList(board));
		//model.addAttribute("categoryList", BoardUtils.getCategoryList(boardContext.getBoardCfg().getCategoryList()));
		//model.addAttribute("boardFiles", boardService.getBoardFileList(board));
		return ViewUtils.getBoardView("form");
	}
	
	
	/**
	 * 수정처리
	 * @param multipartFiles
	 * @param tempFileIds
	 * @param board
	 * @return
	 */
	@PostMapping("{boardCode}/{boardId}/edit")
	public String modifyAction(BoardContext boardContext, TempFiles tempFiles, HttpServletRequest request,
			@PathVariable("boardCode") String boardCode,
			@Valid Board board, BindingResult bindingResult) {
		
		try {
			//boardService.save(board, "modify", multipartFiles, tempFileIds);
			boardService.updateBoard(board, tempFiles);
		} catch (BusinessException e) {
			FlashMapUtils.alert(e.getMessage());
			return ViewUtils.redirectBoard(board.getBoardId() + "/edit");
		}
			
		FlashMapUtils.alert(MessageUtils.getMessage("board.message.modify.complete"));
		
		if (DeviceUtils.isMobile(request)) {
			System.out.println("sysout ||" +DeviceUtils.isMobile(request));
			return "redirect:/m/board/"+boardCode;
		}
		
		return ViewUtils.redirectBoard();
	}
	
	
	/**
	 * 게시판 답변 쓰기 폼
	 * @since 2011. 6. 5. - ARDEN
	 * @param boardContext
	 * @param model
	 * @return
	 */
	@GetMapping("{boardCode}/{boardId}/reply")
	public String reply(BoardContext boardContext, Model model) {
		
		if (boardContext.getBoardCfg().getUseReply().equals("0")) {
			FlashMapUtils.alert(MessageUtils.getMessage("board.error.not.support.reply"));
			return ViewUtils.redirectBoard();
		}
		
		Board orgBoard = boardService.getBoardByContext(boardContext);
		if (ValidationUtils.isNull(orgBoard)) {
			FlashMapUtils.alert(MessageUtils.getMessage("board.error.not.exists.article"));
			return ViewUtils.redirectBoard();
		}
		
		boardContext.getBoardAuthority().setModifyAndDeleteAuthority(orgBoard);

		if (!boardContext.getBoardAuthority().isReplyAuthority()) {
			FlashMapUtils.alert(MessageUtils.getMessage("board.error.authority.reply"));
			return ViewUtils.redirectBoard();
		}
		
		
		Board board = new Board();
		board.setBoardCode(orgBoard.getBoardCode());
		board.setBoardId(orgBoard.getBoardId());
		board.setCategory(orgBoard.getCategory());
		board.setSubject("[답변] " + orgBoard.getSubject());
		board.setContent("<br /><br /><br />---- 원본 글 ----<br /><br />" + orgBoard.getContent());
		
		model.addAttribute("board", board);
		//model.addAttribute("boardFiles", boardService.getBoardFileList(board));
		//model.addAttribute("categoryList", BoardUtils.getCategoryList(boardContext.getBoardCfg().getCategoryList()));
		return ViewUtils.getBoardView("write");
	}
	

	/**
	 * 게시판 답변 저장
	 * @since 2011. 6. 5. - ARDEN
	 * @param multipartFiles
	 * @param tempFileIds
	 * @param board
	 * @return
	 */
	@PostMapping("{boardCode}/{boardId}/reply")
	public String replyAction(BoardContext boardContext, @RequestParam(value="uploadfile[]", required=false) MultipartFile[] multipartFiles, 
			@RequestParam(value="uploadFileSeq[]", required=false) int[] tempFileIds, 
			@ModelAttribute Board board) {
		
		if (boardContext.getBoardCfg().getUseReply().equals("0")) {
			FlashMapUtils.alert(MessageUtils.getMessage("board.error.not.support.reply"));
			return ViewUtils.redirectBoard();
		}
		
		try {
			boardService.save(board, "reply", multipartFiles, tempFileIds);
		} catch (BusinessException e) {
			FlashMapUtils.alert(e.getMessage());
			return ViewUtils.redirectBoard(board.getBoardId() + "/reply");
		}
		
		FlashMapUtils.alert(MessageUtils.getMessage("board.message.reply.complete"));
		return ViewUtils.redirectBoard();
	}
	
	
	/**
	 * 글 삭제 처리
	 * @param boardContext
	 * @param model
	 * @return
	 */
	@PostMapping("{boardCode}/delete")
	public String delete(BoardContext boardContext, TempFiles tempFiles, HttpServletRequest request,
			@PathVariable("boardCode") String boardCode,
			@Valid Board board, BindingResult bindingResult) {
		
		try {
			boardService.deleteBoard(board);
		} catch (BusinessException e) {
			FlashMapUtils.alert(e.getMessage());
			
			return ViewUtils.redirectBoard(board.getBoardId() + "/view");
		}
		
		FlashMapUtils.alert(MessageUtils.getMessage("board.message.delete.complete"));
		
		if (DeviceUtils.isMobile(request)) {
			System.out.println("sysout ||" +DeviceUtils.isMobile(request));
			return "redirect:/m/board/"+boardCode;
		}
		
		return ViewUtils.redirectBoard();
	}
	
	
	/**
	 * 수정 페이지에서 첨부된 파일을 삭제한다.
	 * @param boardContext
	 * @param boardParam
	 * @param model
	 * @return
	 */
	// 파일 삭제기능 ajax로 구현 되어있어 리턴 형식을 ModelAndView -> JsonView로 변경 2017-09-14 seungil.lee
	@PostMapping("{boardCode}/{boardId}/deleteFile/{fileId}")
	public JsonView deleteFile(BoardContext boardContext, Board boardParam, 
			@PathVariable("fileId") int fileId, Model model) {
		Board board = boardService.getBoardByContext(boardContext);
		if (ValidationUtils.isNull(board)) {
			return JsonViewUtils.exception(MessageUtils.getMessage("board.error.not.exists.article"));
		}
		
		boardContext.getBoardAuthority().setModifyAndDeleteAuthority(board, boardParam);
		
		if (!boardContext.getBoardAuthority().isModifyAuthority()) {
			return JsonViewUtils.exception(MessageUtils.getMessage("board.error.authority.modify"));
		}
		
		boardService.deleteFile(board, fileId);
		
		return JsonViewUtils.success();
	}
	
	
	/**
	 * 파일 다운로드
	 * @param boardContext
	 * @param fileId
	 * @return
	 */
	@GetMapping("{boardCode}/{boardId}/download/{fileId}")
	public ModelAndView download(BoardContext boardContext, @PathVariable("fileId") int fileId) {
		
		Board board = boardService.getBoardByContext(boardContext);
		if (ValidationUtils.isNull(board)) {
			FlashMapUtils.alert(MessageUtils.getMessage("board.error.not.exists.article"));
			return ViewUtils.getModelAndViewRedirectBoard();
		}
		
		if (!boardContext.getBoardAuthority().isDownloadAuthority()) {
			FlashMapUtils.alert(MessageUtils.getMessage("board.error.authority.download"));
			return ViewUtils.getModelAndViewRedirectBoard();
		}
		
		UploadFile uploadFile = fileService.getUploadFileById(fileId);
		String uploadPath = FileUtils.getDefaultUploadPath() + File.separator + uploadFile.getRefCode() + File.separator
				+ uploadFile.getRefId() + File.separator + uploadFile.getFileId() + "." + uploadFile.getFileType();
		
		File file = new File(uploadPath);
		
		// 파일이 존재하지 않을 경우 이전 페이지로 이동
		if (!file.exists()) {
			return new ModelAndView(ViewUtils.redirect("/board/" + boardContext.getBoardCode() + "/" + boardContext.getBoardId(), "파일이 존재하지 않습니다."));
		}
		
		ModelAndView mav = new ModelAndView(new FileDownloadView());
		mav.addObject(FileDownloadView.MODELNAME, uploadFile);
		return mav;
	}
	
	
	/**
	 * 전체파일 다운로드
	 * @param boardContext
	 * @return
	 */
	@GetMapping("{boardCode}/{boardId}/downloadAll")
	public ModelAndView downloadAll(BoardContext boardContext) {
		Board board = boardService.getBoardByContext(boardContext);
		if (ValidationUtils.isNull(board)) {
			FlashMapUtils.alert(MessageUtils.getMessage("board.error.not.exists.article"));
			return ViewUtils.getModelAndViewRedirectBoard();
		}
		
		if (!boardContext.getBoardAuthority().isDownloadAuthority()) {
			FlashMapUtils.alert(MessageUtils.getMessage("board.error.authority.download"));
			return ViewUtils.getModelAndViewRedirectBoard();
		}
		
		List<UploadFile> uploadFiles = fileService.getUploadFileList(boardContext.getBoardCode(), boardContext.getBoardId());
		ModelAndView mav = new ModelAndView(new FileDownloadView());
		mav.addObject(FileDownloadView.MODELNAME, uploadFiles);
		return mav;
	}
	
	
	@InitBinder
    public void initBinder(WebDataBinder binder) {
        //binder.registerCustomEditor(int.class, "notice", new BoardPropertyEditor());
        //binder.registerCustomEditor(int.class, "secret", new BoardPropertyEditor());
    }

}
