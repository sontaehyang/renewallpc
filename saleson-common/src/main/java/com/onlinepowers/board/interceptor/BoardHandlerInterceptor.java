package com.onlinepowers.board.interceptor;

import com.onlinepowers.board.BoardService;
import com.onlinepowers.board.domain.BoardCfg;
import com.onlinepowers.board.support.BoardAuthority;
import com.onlinepowers.board.support.BoardContext;
import com.onlinepowers.framework.context.RequestContext;
import com.onlinepowers.framework.context.ThreadContext;
import com.onlinepowers.framework.context.util.RequestContextUtils;
import com.onlinepowers.framework.exception.UserException;
import com.onlinepowers.framework.security.service.SecurityService;
import com.onlinepowers.framework.util.CommonUtils;
import com.onlinepowers.framework.util.MessageUtils;
import com.onlinepowers.framework.util.StringUtils;
import com.onlinepowers.framework.util.ValidationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



public class BoardHandlerInterceptor extends HandlerInterceptorAdapter {
	private static final Logger log = LoggerFactory.getLogger(BoardHandlerInterceptor.class);
	private String boardPrefixUri;
	
	@Autowired
	private BoardService boardService;
	@Autowired 
	private SecurityService securityService;
	
	@Autowired
	Environment environment;
	
	public void setBoardPrefixUri(String boardPrefixUri) {
		this.boardPrefixUri = boardPrefixUri.startsWith("/") ? boardPrefixUri : "/" + boardPrefixUri;
	}

	public String getBoardPrefixUri() {
		return boardPrefixUri;
	}

	private boolean isBoardRequest(String requestUri) {
		if (requestUri.startsWith(this.boardPrefixUri) 
				|| requestUri.startsWith(environment.getProperty("mobile.prefix") + this.boardPrefixUri) 
				|| requestUri.startsWith("/opmanager" + this.boardPrefixUri)) {
			return true;
		}
		return false;
	}
	
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		
		if (CommonUtils.isResourceHandler(handler)) {
			return true;
		}
		
		boolean isLogin = securityService.isLogin();
		
		RequestContext requestContext = RequestContextUtils.getRequestContext();
    	String requestUri = requestContext.getRequestUri();
    	

        if (isBoardRequest(requestUri)) {
        	String boardUri = requestUri.replaceAll("/opmanager" + this.boardPrefixUri, "")
        						.replaceAll(environment.getProperty("mobile.prefix") + this.boardPrefixUri, "")
        						.replaceAll(this.boardPrefixUri, "");
        	String[] boardCodes = StringUtils.getStringArray(boardUri, "/");
        	String boardCode = boardCodes[0];
        	
        	BoardContext boardContext = BoardContext.getInstance(request);
        	
        	boardContext.setBoardCfgList(boardService.getBoardCfgList());
        	
        	// 관리자 설정.
        	if ("-cfg".equals(boardCode)) {
        		
        		ThreadContext.put(BoardContext.REQUEST_NAME, boardContext);
        		return true;
        	}
        	int boardId = 0;
        	try {
        		if (!"write".equals(boardCodes[1])) {
        			boardId = Integer.parseInt(boardCodes[1]);
        		}
			} catch (Exception e) {}
        	
        	if (boardCode.equals("community")) {
        		return true;
        	}
        	
        	BoardCfg boardCfg = boardService.getBoardCfg(boardCode);
        	
        	if (ValidationUtils.isNull(boardCfg)) {
        		log.info("존재하지 않는 게시판 코드 ({}), redirect : /",  boardCode);
        		throw new UserException(MessageUtils.getMessage("board.error.not.exists.board"), "/");
        	}
        	
        	BoardAuthority boardAuthority = new BoardAuthority(boardCfg);
        	if (!boardAuthority.isListAuthority()) {
        		log.info("게시판({}) 읽기 권한 없음, redirect : /",  boardCode);
        		throw new UserException(MessageUtils.getMessage("board.error.authority.read"));
        	}
        	
        	// 모바일 페이지인 경우
        	if (requestUri.startsWith(environment.getProperty("mobile.prefix"))) {
        		boardCfg.setBoardTemplate("mobile");
        	}
        	
        	
        	// 관리자 페이지인 경우.
        	if (requestContext.isOpmanagerPage()) {
        		boardCfg.setBoardHeader("<h3>" + boardCfg.getSubject() + "</h3>");
        		boardCfg.setBoardFooter("");
        	}
        	
        	requestContext.getRequestPropertyData().setLayout(boardCfg.getBoardLayout());
        	
        	boardContext.setBoardCfg(boardCfg);
        	boardContext.setBoardCode(boardCode);
        	boardContext.setBoardCategory(request.getParameter("category"));
        	boardContext.setBoardId(boardId);
        	boardContext.setBoardAuthority(boardAuthority);
        	boardContext.setBoardPrefixUri(requestContext.getContextPath() + requestContext.getOpmanagerUri() + this.boardPrefixUri);
        	boardContext.setBoardBaseUri(requestContext.getContextPath() + requestContext.getOpmanagerUri() + this.boardPrefixUri + "/" + boardCode);
    		
        	if (request.getParameter("url") == null || request.getParameter("url").equals("")) {
        		requestContext.setPrevPageUrl(boardContext.getBoardBaseUri());
        	}
        	
        	ThreadContext.put(BoardContext.REQUEST_NAME, boardContext);
        }
        return true;
    }
	
	public void postHandle(
			HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)
				throws Exception {
		if (!CommonUtils.isResourceHandler(handler)) {
			
			try{
				//if (!AjaxUtils.isAjaxRequest(request)) {
				if (modelAndView != null) {
					modelAndView.addObject("boardContext", ThreadContext.get(BoardContext.REQUEST_NAME));
				}
			} catch (Exception e) {
				log.error("[BoardHadlerInterceptor] postHandle ERROR!", e);
			}
		}
	}
	
}