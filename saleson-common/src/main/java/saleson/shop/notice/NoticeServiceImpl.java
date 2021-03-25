package saleson.shop.notice;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import saleson.shop.notice.domain.Notice;
import saleson.shop.notice.domain.NoticeSeller;
import saleson.shop.notice.support.NoticeParam;

import com.onlinepowers.framework.sequence.service.SequenceService;


@Service("noticeService")
public class NoticeServiceImpl implements NoticeService{

	@Autowired 
	NoticeMapper noticeMapper;
	
	@Autowired SequenceService sequenceService;

	@Override
	public void insertNotice(Notice notice) {

		
		int noticeId = sequenceService.getId("OP_NOTICE");
		notice.setNoticeId(noticeId);
		
		//notice.setCategoryTeam(notice.getCategoryTeam().replaceAll(",", "|"));
		noticeMapper.insertNotice(notice);
		if(notice.getSellerIds() != null){
			NoticeSeller noticeSeller = new NoticeSeller();
			
			for (long sellerId : notice.getSellerIds()) {
				int noticeSellerId = sequenceService.getId("OP_NOTICE_SELLER");
				noticeSeller.setNoticeSellerId(noticeSellerId);
				noticeSeller.setNoticeId(noticeId);
				noticeSeller.setSellerId(sellerId);
				
				noticeMapper.insertNoticeSeller(noticeSeller);
			}
		}
	}

	@Override
	public int getNoticeCount(NoticeParam noticeParam) {

		return noticeMapper.getNoticeCount(noticeParam);
	}

	@Override
	public List<Notice> getNoticeList(NoticeParam noticeParam) {

		return noticeMapper.getNoticeList(noticeParam);
	}

	@Override
	public Notice getNotice(int noticeId) {

		return noticeMapper.getNotice(noticeId);
	}

	@Override
	public void updateNotice(Notice notice) {

		//notice.setCategoryTeam(notice.getCategoryTeam().replaceAll(",", "|"));
		noticeMapper.updateNotice(notice);
		
		if(notice.getSellerIds() != null){
			NoticeSeller noticeSeller = new NoticeSeller();
			for (long sellerId : notice.getSellerIds()) {
				int noticeSellerId = sequenceService.getId("OP_NOTICE_SELLER");
				noticeSeller.setNoticeSellerId(noticeSellerId);
				noticeSeller.setNoticeId(notice.getNoticeId());
				noticeSeller.setSellerId(sellerId);
				
				noticeMapper.insertNoticeSeller(noticeSeller);
			}
		}
		
	}

	@Override
	public void deleteNotice(int noticeId) {

		noticeMapper.deleteNotice(noticeId);
	}

	@Override
	public List<Notice> getFrontNoticeList(NoticeParam noticeParam) {

		return noticeMapper.getFrontNoticeList(noticeParam);
	}

	@Override
	public List<Notice> getFrontNoticeListByTeamCodes(NoticeParam noticeParam) {
		return noticeMapper.getFrontNoticeListByTeamCodes(noticeParam);
	}
	
	@Override
	public int getFrontNoticeListCount(NoticeParam noticeParam) {
		return noticeMapper.getFrontNoticeListCount(noticeParam);
	}

	@Override
	public void addHitCount(int noticeId) {

		noticeMapper.addHitCount(noticeId);
	}
	
	@Override
	public void deleteNoticeSeller(int noticeSellerId){
		noticeMapper.deleteNoticeSeller(noticeSellerId);
	}

	public List<NoticeSeller> getNoticeSellerList(int noticeId){
		return noticeMapper.getNoticeSellerList(noticeId);
	}
	
}
