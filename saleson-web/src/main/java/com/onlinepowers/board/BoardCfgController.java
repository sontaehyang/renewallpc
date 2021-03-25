package com.onlinepowers.board;

import com.onlinepowers.board.domain.BoardCfg;
import com.onlinepowers.board.domain.BoardSearchParam;
import com.onlinepowers.framework.context.RequestContext;
import com.onlinepowers.framework.exception.NotAjaxRequestException;
import com.onlinepowers.framework.util.DateUtils;
import com.onlinepowers.framework.util.JsonViewUtils;
import com.onlinepowers.framework.util.ViewUtils;
import com.onlinepowers.framework.web.bind.annotation.RequestProperty;
import com.onlinepowers.framework.web.pagination.Pagination;
import com.onlinepowers.framework.web.servlet.view.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import saleson.common.Const;

@Controller
@RequestMapping("/opmanager/board-cfg")
@RequestProperty(layout="default", template="opmanager")
public class BoardCfgController {
	
	@Autowired
	private BoardService boardService;
	
	/**
	 * 게시판 설정 목록
	 * @param model
	 * @param searchParam
	 * @return
	 */
	@GetMapping("list")
	public String list(Model model, BoardSearchParam searchParam) {
		
		int boardCfgCount = boardService.getBoardCfgCount(searchParam);
		
		// select box 선택에 따른 페이징 처리, 기본값 10
		if (searchParam.getItemsPerPage() == 0) {
			searchParam.setItemsPerPage(10);
		}
		
		Pagination pagination = Pagination.getInstance(boardCfgCount, searchParam.getItemsPerPage());
		searchParam.setPagination(pagination);
		
		// 날짜 검색을 위한 세팅
		String today = DateUtils.getToday(Const.DATE_FORMAT);
		
		model.addAttribute("today", today);
		model.addAttribute("week", DateUtils.addYearMonthDay(today, 0, 0, -7));
		model.addAttribute("month1", DateUtils.addYearMonthDay(today, 0, -1, 0));
		model.addAttribute("month2", DateUtils.addYearMonthDay(today, 0, -2, 0));
		
		model.addAttribute("searchParam", searchParam);
		model.addAttribute("list", boardService.getBoardCfgListByParam(searchParam));
		model.addAttribute("pagination", pagination);
		model.addAttribute("totalCount", boardCfgCount);
		
		return ViewUtils.view();
	}
	
	/**
	 * 게시판 설정 작성 폼
	 * @param model
	 * @param boardCfg
	 * @return
	 */
	@GetMapping("create")
	public String create(Model model, BoardCfg boardCfg) {
		
		model.addAttribute("boardCfg", boardCfg);
		return ViewUtils.view();
	}
	
	/**
	 * 게시판 설정 작성 처리
	 * @param model
	 * @param boardCfg
	 * @return
	 */
	@PostMapping("create")
	public String createProcess(Model model, BoardCfg boardCfg) {
		
		// 중복 여부와 값이 비었는지 체크
		int checkBoardCode = boardService.getBoardCfgCountByBoardCode(boardCfg.getBoardCode());
		if (checkBoardCode == 1 || StringUtils.isEmpty(boardCfg.getBoardCode())) {
			boardCfg.setBoardCode("");
			model.addAttribute("boardCfg", boardCfg);
			return ViewUtils.view("유효하지 않은 접근입니다.");
		}
		
		boardCfg.setStatusCode("1");
		boardCfg.setBoardHeader("<h3>" + boardCfg.getSubject() + "</h3>");
		
		
		boardService.insertBoardCfg(boardCfg);
		
		return ViewUtils.redirect("/opmanager/board-cfg/list", "등록되었습니다.");
	}
	
	/**
	 * 게시판 설정 수정 폼
	 * @param boardCode
	 * @param model
	 * @param boardCfg
	 * @return
	 */
	@GetMapping("edit/{boardCode}")
	public String edit(@PathVariable("boardCode") String boardCode, Model model) {
		
		BoardCfg boardCfg = boardService.getBoardCfgByBoardCode(boardCode);
		
		model.addAttribute("boardCfg", boardCfg == null ? new BoardCfg() : boardCfg);
		return ViewUtils.getView("/board-cfg/form");
	}
	
	/**
	 * 게시판 설정 수정 처리
	 * @param boardCode
	 * @param model
	 * @param boardCfg
	 * @return
	 */
	@PostMapping("edit/{boardCode}")
	public String editProcess(@PathVariable("boardCode") String boardCode, Model model, BoardCfg boardCfg) {
		
		boardCfg.setBoardHeader("<h3>" + boardCfg.getSubject() + "</h3>");
		boardService.updateBoardCfg(boardCfg);
		return ViewUtils.redirect("/opmanager/board-cfg/list", "수정되었습니다.");
	}
	
	/**
	 * 게시판 설정 삭제
	 * @param boardCode
	 * @param model
	 * @param boardCfg
	 * @return
	 */
	@GetMapping(value="delete/{boardCode}")
	public String delete(@PathVariable("boardCode") String boardCode, Model model) {
		
		boardService.updateBoardCfgStatusCode(boardCode);
		return ViewUtils.redirect("/opmanager/board-cfg/list", "삭제되었습니다.");
	}
	
	/**
	 * 게시판 코드 중복 검사
	 * @param requestContext
	 * @param model
	 * @param boardCfg
	 * @param value
	 * @return
	 */
	@GetMapping("user-availability-check-boardCode")
	public JsonView userAvailabilityCheckJoin(RequestContext requestContext, Model model, BoardCfg boardCfg,
			@RequestParam("value") String value, @RequestParam("type") String type, BindingResult bindingResult) {
		
		if (!requestContext.isAjaxRequest()) { 
		    throw new NotAjaxRequestException();
		}
		
		// 수정일 경우 중복 검사가 필요없음.
		if ("edit".equals(type)) {
			return JsonViewUtils.success();
		}
		
		// 게시판 코드 중복 여부 검사
		int checkBoardCode = boardService.getBoardCfgCountByBoardCode(value);
		if (checkBoardCode > 0) {
			return JsonViewUtils.failure("이미 사용중인 게시판 코드입니다.");
		}
		
		return JsonViewUtils.success();
	}
}
