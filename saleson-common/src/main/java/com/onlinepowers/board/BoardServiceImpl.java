package com.onlinepowers.board;

import com.onlinepowers.board.domain.*;
import com.onlinepowers.board.support.BoardContext;
import com.onlinepowers.framework.context.RequestContext;
import com.onlinepowers.framework.context.util.RequestContextUtils;
import com.onlinepowers.framework.exception.BusinessException;
import com.onlinepowers.framework.exception.UserException;
import com.onlinepowers.framework.file.domain.TempFiles;
import com.onlinepowers.framework.file.domain.UploadFile;
import com.onlinepowers.framework.file.service.FileService;
import com.onlinepowers.framework.security.service.SecurityService;
import com.onlinepowers.framework.sequence.service.SequenceService;
import com.onlinepowers.framework.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service("boardService")
public class BoardServiceImpl implements BoardService {
	
	@Autowired 
	protected BoardMapper boardMapper;
	
	@Autowired 
	protected SecurityService securityService;
	
	@Autowired 
	protected SequenceService sequenceService;
	
	@Autowired 
	protected FileService fileService;

	@Override
	public BoardCfg getBoardCfg(String boardCode) {
		return boardMapper.getBoardCfg(boardCode);
	}
	

	@Override
	public List<BoardCfg> getBoardCfgList() {
		return boardMapper.getBoardCfgList();
	}

	@Override
	public int getBoardCount(BoardSearchParam boardSearchParam) {
		return boardMapper.getBoardCount(boardSearchParam);
	}

	@Override
	public List<Board> getBoardList(BoardSearchParam boardSearchParam) {
		return boardMapper.getBoardList(boardSearchParam);
	}

	@Override
	public Board getBoardByContext(BoardContext boardContext) {
		return boardMapper.getBoardByContext(boardContext);
	}

	@Override
	public Board getBoardView(BoardContext boardContext) {
		boardMapper.addHitCount(boardContext);
		return boardMapper.getBoardByContext(boardContext);
	}

	@Override
	public void save(Board board, String mode, MultipartFile[] multipartFiles, int[] tempFileIds) {
		RequestContext requestContext =  ThreadContextUtils.getRequestContext();
		BoardContext boardContext = ThreadContextUtils.getBoardContext();
		BoardCfg boardCfg = boardContext.getBoardCfg();
		
		if (requestContext.isLogin()) {
			board.setUserName(SecurityUtils.getCurrentUser().getUserName());
			board.setEmail(SecurityUtils.getCurrentUser().getEmail());
			board.setUserId(SecurityUtils.getCurrentUser().getUserId());
		}
		

		// IP
		// board.setRemoteAddr(requestContext.getRemoteAddr());
		board.setRemoteAddr(saleson.common.utils.CommonUtils.getClientIp());
	
		if (ValidationUtils.isNull(board.getNotice())) {
			board.setNotice("0");
		}
		
		if (ValidationUtils.isNull(board.getSecret())) {
			board.setSecret("0");
		}
		
		if (StringUtils.isNotEmpty(board.getTag())) {
			board.setTag(StringUtils.splitWithoutSpaceToString(board.getTag(), ","));
		}
		
		
		if (!SecurityUtils.isLogin()) {
			// ????????? ??????
		}
		
		if ("write".equals(mode)) {
			int groupId = boardMapper.getNewGroupId(board);
			
			board.setBoardCode(board.getBoardCode());
			board.setGroupId(groupId);
			board.setStep(groupId);
			board.setDepth(0);
			board.setUserId(securityService.getCurrentUserId());
			
			int boardId = sequenceService.getId("OP_BOARD");
			board.setBoardId(boardId);
			
			// ????????? ???????????? ?????? ????????? ????????? ?????? ??????
			updateContentImagePath(requestContext, board);
			
			
			// ???????????? ??????
			//int fileCount = fileService.uploadSwf(tempFileIds, refCode, refId);
			int fileCount = fileService.uploadWithThumbnail(tempFileIds, board.getBoardCode(), boardId, boardCfg.getUploadThumbnail(), boardCfg.getUploadEncrypt());
			
			board.setFileCount(fileCount);

			// ???????????? ??? ??????
			boardMapper.insertBoard(board);
			
			// ????????? ???????????? ??????
			insertBoardNew(board);
			
			
		} else if ("modify".equals(mode)) {
			Board boardData = boardMapper.getBoard(board);
			if (!SecurityUtils.isLogin()) {
				if (!boardData.getPassword().equals(board.getEncryptPassword()))
				throw new UserException(MessageUtils.getMessage("board.exception.password.not.match"));
			}
			
			// ????????? ???????????? ?????? ????????? ????????? ?????? ??????
			updateContentImagePath(requestContext, board);
			
			
			// ???????????? ??????
			//int fileCount = fileService.uploadSwf(tempFileIds, refCode, refId);
			int fileCount = fileService.uploadWithThumbnail(tempFileIds, board.getBoardCode(), board.getBoardId(), boardCfg.getUploadThumbnail(), boardCfg.getUploadEncrypt());
			
			board.setFileCount(fileCount);
			
			// ??? ??????
			boardMapper.updateBoard(board);
			
			// ?????????????????? ??????
			boardMapper.updateBoardNew(board);
			
		} else if ("reply".equals(mode)) {
			Board boardData = boardMapper.getBoard(board);
			
			boardMapper.updateBoardStep(boardData);
			
			board.setGroupId(boardData.getGroupId());
			board.setStep(boardData.getStep() + 1);
			board.setDepth(boardData.getDepth() + 1);
			board.setParentId(boardData.getBoardId());
		
			int boardId = sequenceService.getId("OP_BOARD");
			board.setBoardId(boardId);
			
			// ????????? ???????????? ?????? ????????? ????????? ?????? ??????
			updateContentImagePath(requestContext, board);
			
			
			// ???????????? ??????
			//int fileCount = fileService.uploadSwf(tempFileIds, refCode, refId);
			int fileCount = fileService.uploadWithThumbnail(tempFileIds, board.getBoardCode(), boardId, boardCfg.getUploadThumbnail(), boardCfg.getUploadEncrypt());

			board.setFileCount(fileCount);

			// ???????????? ??? ??????
			boardMapper.insertBoard(board);
			
			// ????????? ???????????? ??????
			insertBoardNew(board);
			
		} else {
			throw new UserException(MessageUtils.getMessage("board.exception.unknown.mode.type"));
		}
		
		
		// ????????????
		// 1. ????????? ????????????
		if (boardContext.getBoardCfg().getUseNotice().equals("1")) {
			List<String> noticeBoardIds = boardMapper.getNoticeBoardIdList(board);
			String noticeIds = "";
			int i = 0;
			for (String boardId : noticeBoardIds) {
				if (i == 0) noticeIds = boardId;
				else noticeIds += "," + boardId;
				i++;
			}
			boardCfg.setNoticeBoardId(noticeIds);
			boardMapper.updateBoardCfgNoticeBoardId(boardCfg);
		}
	}
	

	@Override
	public void save(Board board, String mode, TempFiles tempFiles) {
		RequestContext requestContext =  ThreadContextUtils.getRequestContext();
		BoardContext boardContext = ThreadContextUtils.getBoardContext();
		BoardCfg boardCfg = boardContext.getBoardCfg();
		
		if (requestContext.isLogin()) {
			board.setUserName(SecurityUtils.getCurrentUser().getUserName());
			
			if (SecurityUtils.getCurrentUser().getEmail() != null) {
				board.setEmail(SecurityUtils.getCurrentUser().getEmail());
			}
			board.setUserId(SecurityUtils.getCurrentUser().getUserId());
		}
		
			
		// IP
		// board.setRemoteAddr(requestContext.getRemoteAddr());
		board.setRemoteAddr(saleson.common.utils.CommonUtils.getClientIp());
		
	
		if (ValidationUtils.isNull(board.getNotice())) {
			board.setNotice("0");
		}
		
		if (ValidationUtils.isNull(board.getSecret())) {
			board.setSecret("0");
		}
		
		if (StringUtils.isNotEmpty(board.getTag())) {
			board.setTag(StringUtils.splitWithoutSpaceToString(board.getTag(), ","));
		}
		
		
		if (!SecurityUtils.isLogin()) {
			// ????????? ??????
		}
		
		if ("write".equals(mode)) {
			int groupId = boardMapper.getNewGroupId(board);
			
			board.setBoardCode(board.getBoardCode());
			board.setGroupId(groupId);
			board.setStep(groupId);
			board.setDepth(0);
			board.setUserId(securityService.getCurrentUserId());
			board.setUpdatedUserId(SecurityUtils.getCurrentUserId());
			board.setUpdatedUsername(SecurityUtils.getCurrentUser().getUserName());
			
			int boardId = sequenceService.getId("OP_BOARD");
			board.setBoardId(boardId);
			
			// ????????? ???????????? ?????? ????????? ????????? ?????? ??????
			updateContentImagePath(requestContext, board);
			
			
			// ???????????? ??????
			tempFiles.setRefCode(board.getBoardCode());
			tempFiles.setRefId(boardId);
			tempFiles.setThumbnailSize(boardCfg.getUploadThumbnail());
	
			int fileCount = fileService.upload(tempFiles);
			
			
			board.setFileCount(fileCount);

			// ???????????? ??? ??????
			boardMapper.insertBoard(board);
			
			// ????????? ???????????? ??????
			insertBoardNew(board);
			
			
		} else if ("modify".equals(mode)) {
			Board boardData = boardMapper.getBoard(board);
			if (!SecurityUtils.isLogin()) {
				if (!boardData.getPassword().equals(board.getEncryptPassword()))
				throw new BusinessException(MessageUtils.getMessage("board.exception.password.not.match"));
			}
			
			// ????????? ???????????? ?????? ????????? ????????? ?????? ??????
			updateContentImagePath(requestContext, board);
			

			// ???????????? ??????
			tempFiles.setRefCode(board.getBoardCode());
			tempFiles.setRefId(board.getBoardId());
			tempFiles.setThumbnailSize(boardCfg.getUploadThumbnail());
	
			int fileCount = fileService.upload(tempFiles);
			
			board.setFileCount(fileCount);
			
			board.setUpdatedUserId(SecurityUtils.getCurrentUserId());
			board.setUpdatedUsername(SecurityUtils.getCurrentUser().getUserName());
			
			// ??? ??????
			boardMapper.updateBoard(board);
			
			// ?????????????????? ??????
			boardMapper.updateBoardNew(board);
			
		} else if ("reply".equals(mode)) {
			Board boardData = boardMapper.getBoard(board);
			
			boardMapper.updateBoardStep(boardData);
			
			board.setGroupId(boardData.getGroupId());
			board.setStep(boardData.getStep() + 1);
			board.setDepth(boardData.getDepth() + 1);
			board.setParentId(boardData.getBoardId());
		
			int boardId = sequenceService.getId("OP_BOARD");
			board.setBoardId(boardId);
			
			// ????????? ???????????? ?????? ????????? ????????? ?????? ??????
			updateContentImagePath(requestContext, board);
			
			
			// ???????????? ??????
			tempFiles.setRefCode(board.getBoardCode());
			tempFiles.setRefId(boardId);
			tempFiles.setThumbnailSize(boardCfg.getUploadThumbnail());
	
			int fileCount = fileService.upload(tempFiles);
			
			board.setFileCount(fileCount);

			// ???????????? ??? ??????
			boardMapper.insertBoard(board);
			
			// ????????? ???????????? ??????
			insertBoardNew(board);
			
		} else {
			throw new UserException(MessageUtils.getMessage("board.exception.unknown.mode.type"));
		}
		
		
		// ????????????
		// 1. ????????? ????????????
		if (boardContext.getBoardCfg().getUseNotice().equals("1")) {
			List<String> noticeBoardIds = boardMapper.getNoticeBoardIdList(board);
			String noticeIds = "";
			int i = 0;
			for (String boardId : noticeBoardIds) {
				if (i == 0) noticeIds = boardId;
				else noticeIds += "," + boardId;
				i++;
			}
			boardCfg.setNoticeBoardId(noticeIds);
			boardMapper.updateBoardCfgNoticeBoardId(boardCfg);
		}
		
	}

	
	private void updateContentImagePath(RequestContext requestContext, Board board) {
		// ?????? ?????? - skc120618 : ??????????????? ????????? ???????????? /upload/editor/... ???????????? ????????? ?????????.
	}
	
	private void insertBoardNew(Board board) {
		// ????????? ???????????? ??????
		BoardNew boardNew = new BoardNew();
		boardNew.setBoardNewId(sequenceService.getId("OP_BOARD_NEW"));
		boardNew.setBoardId(board.getBoardId());
		boardNew.setBoardCode(board.getBoardCode());
		boardNew.setSubject(board.getSubject());
		boardNew.setContent(board.getContent());
		boardNew.setUserId(board.getUserId());
		boardNew.setGroupCode(ThreadContextUtils.getBoardContext().getBoardCfg().getGroupCode());
		boardNew.setTag(board.getTag());
		boardNew.setSecret(board.getSecret());		
		boardMapper.insertBoardNew(boardNew);
	}

	@Override
	public List<UploadFile> getBoardFileList(Board board) {
		return fileService.getUploadFileList(board.getBoardCode(), board.getBoardId());
	}

	@Override
	public void deleteBoard(Board board) {
		Board boardData = boardMapper.getBoard(board);
		if (SecurityUtils.isGuest()) {
			if (!boardData.getPassword().equals(board.getEncryptPassword()))
				throw new UserException(MessageUtils.getMessage("board.exception.password.not.match"));
		}
		
		if (SecurityUtils.isLogin() && !ThreadContextUtils.getBoardContext().getBoardAuthority().isBoardAdmin()) {
			if (boardData.getUserId() != SecurityUtils.getCurrentUserId()) {
				throw new UserException(MessageUtils.getMessage("board.exception.delete.only.your.article"));
			}
		}
		
		// ???????????? ?????? ?????? ?????? ??????
		int replyCount = boardMapper.getBoardCountByParentId(board);
		
		if (replyCount > 0)
			throw new UserException(MessageUtils.getMessage("board.exception.delete.only.reply.count.zero"));
		
		
		
	
		boardMapper.deleteBoardRecommend(board);
		boardMapper.deleteBoardComment(board);
		fileService.delete(board.getBoardCode(), board.getBoardId());
		boardMapper.deleteBoardNew(board);
		boardMapper.deleteBoardTag(board);
		boardMapper.deleteBoard(board);
		
	}

	@Override
	public void deleteFile(Board board, int fileId) {
		// 1. ????????????
		fileService.deleteById(fileId);
		
		// 2. ?????? ?????? UPDATE
		UploadFile file = new UploadFile();
		file.setRefCode(board.getBoardCode());
		file.setRefId(board.getBoardId());
		board.setFileCount(fileService.getFileCountByUploadFile(file) == 1 ? 0 : fileService.getFileCountByUploadFile(file) - 1);
		
		// 3. ??? ??????
		boardMapper.updateBoard(board);
	}
	
	@Override
	public Board getBoard(Board boardParam) {
		
		return boardMapper.getBoard(boardParam);
	}

	@Override
	public int getBoardCommentCount(BoardSearchParam boardSearchParam) {
		return boardMapper.getBoardCommentCount(boardSearchParam);
	}

	@Override
	public List<BoardComment> getBoardCommentList(
			BoardSearchParam boardSearchParam) {
		return boardMapper.getBoardCommentList(boardSearchParam);
	}

	@Override
	public void insertComment(BoardComment boardComment) {
		boardComment.setBoardCommentId(sequenceService.getId("OP_BOARD_COMMENT"));
		
		if (SecurityUtils.isLogin()) {
			boardComment.setUserId(SecurityUtils.getCurrentUserId());
			//boardComment.setUserName(SecurityUtils.getCurrentUser().getUserName());
		}
		// boardComment.setRemoteAddr(RequestContextUtils.getRemoteAddr());
		boardComment.setRemoteAddr(saleson.common.utils.CommonUtils.getClientIp());
		
		boardMapper.insertComment(boardComment);
		
		boardMapper.updateCommentCount(boardComment);
	}

	@Override
	public void deleteComment(BoardComment boardComment) {
		boardMapper.deleteComment(boardComment);
		boardMapper.updateCommentCount(boardComment);
	}

	@Override
	public BoardComment getBoardComment(BoardComment boardComment) {
		return boardMapper.getBoardComment(boardComment);
	}

	@Override
	public Board getBoardPrev(BoardSearchParam boardSearchParam) {
		
		// OP_BOARD_CFG ??? SORT_FIELD ?????? ?????? ????????? ?????? ?????? / ?????? ??? ???????????? ????????? ?????????
		if (boardSearchParam.getSortField().equals("")) {
			return boardMapper.getBoardPrev(boardSearchParam);
		} else {
			return boardMapper.getBoardPrevBySortField(boardSearchParam);
		}
	}

	@Override
	public Board getBoardNext(BoardSearchParam boardSearchParam) {
		// OP_BOARD_CFG ??? SORT_FIELD ?????? ?????? ????????? ?????? ?????? / ?????? ??? ???????????? ????????? ?????????
		if (boardSearchParam.getSortField().equals("")) {
			return boardMapper.getBoardNext(boardSearchParam);
		} else {
			return boardMapper.getBoardNextBySortField(boardSearchParam);
		}
		
	}

	@Override
	public List<Board> getLatestList(BoardSearchParam boardSearchParam) {
		return boardMapper.getLatestList(boardSearchParam);
	}

	@Override
	public List<Board> getNoticeList(BoardCfg boardCfg) {
		return boardMapper.getNoticeList(boardCfg);
	}


	@Override
	public void insertBoard(Board board, TempFiles tempFiles) {
		// ????????? ??????.
		save(board, "write", tempFiles);
	}


	@Override
	public void updateBoard(Board board, TempFiles tempFiles) {
		// ????????? ??????.
		save(board, "modify", tempFiles);
		
		
		// ?????? ?????? ?????? (????????? ??????)
		if (tempFiles.getFileIds() != null) {
			for (int i = 0; i < tempFiles.getFileIds().length; i++) {
				UploadFile uploadFile = new UploadFile(tempFiles.getFileIds()[i]);
				uploadFile.setFileDescription(tempFiles.getFileDescriptions()[i]);
				
				fileService.updateUploadFile(uploadFile);
				
			}
		}
	}
	
	@Override
	public int getBoardCfgCountByBoardCode(String boardCode) {
		return boardMapper.getBoardCfgCountByBoardCode(boardCode);
	}
	
	@Override
	public void insertBoardCfg(BoardCfg boardCfg) {
		boardMapper.insertBoardCfg(boardCfg);
	}
	
	@Override
	public int getBoardCfgCount(BoardSearchParam searchParam) {
		return boardMapper.getBoardCfgCount(searchParam);
	}
	
	@Override
	public List<BoardCfg> getBoardCfgListByParam(BoardSearchParam searchParam) {
		return boardMapper.getBoardCfgListByParam(searchParam);
	}
	
	@Override
	public BoardCfg getBoardCfgByBoardCode(String boardCode) {
		return boardMapper.getBoardCfgByBoardCode(boardCode);
	}
	
	@Override
	public void updateBoardCfg(BoardCfg boardCfg) {
		boardMapper.updateBoardCfg(boardCfg);
	}
	
	@Override
	public void updateBoardCfgStatusCode(String boardCode) {
		boardMapper.updateBoardCfgStatusCode(boardCode);
	}
}
