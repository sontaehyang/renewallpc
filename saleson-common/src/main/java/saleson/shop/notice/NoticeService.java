package saleson.shop.notice;

import java.util.List;

import saleson.shop.notice.domain.Notice;
import saleson.shop.notice.domain.NoticeSeller;
import saleson.shop.notice.support.NoticeParam;

public interface NoticeService {
	
	/**
	 * 공지사항 카운트
	 * @param noticeParam
	 * @return
	 */
	public int getNoticeCount(NoticeParam noticeParam);
	
	/**
	 * 공지사항 리스트(사용자)
	 * @param noticeParam
	 * @return
	 */
	public List<Notice> getNoticeList(NoticeParam noticeParam);
	
	/**
	 * 공지사항 조회
	 * @param noticeId
	 * @return
	 */
	public Notice getNotice(int noticeId);
	public void insertNotice(Notice notice);
	public void updateNotice(Notice notice);
	public void deleteNotice(int noticeId);
	public List<Notice> getFrontNoticeList(NoticeParam noticeParam);
	
	public int getFrontNoticeListCount(NoticeParam noticeParam);
	
	/**
	 * 조회수 추가
	 * @param noticeId
	 */
	public void addHitCount(int noticeId);
	
	/**
	 * 카테고리 팀코드에 해당하는 공지사항 목록을 가져옴. (메인, 팀 용)
	 * @param noticeParam
	 * @return
	 */
	public List<Notice> getFrontNoticeListByTeamCodes(NoticeParam noticeParam);
	
	public void deleteNoticeSeller(int noticeSellerId);
	
	public List<NoticeSeller> getNoticeSellerList(int noticeId);
}
