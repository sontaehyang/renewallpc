package saleson.shop.qna;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.onlinepowers.framework.file.service.FileService;
import com.onlinepowers.framework.util.FileUtils;
import com.onlinepowers.framework.util.StringUtils;
import com.onlinepowers.framework.util.ThumbnailUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.web.multipart.MultipartFile;
import saleson.common.utils.ShopUtils;
import saleson.common.utils.UserUtils;
import saleson.shop.item.ItemService;
import saleson.shop.mailconfig.MailConfigService;
import saleson.shop.qna.domain.Qna;
import saleson.shop.qna.domain.QnaAnswer;
import saleson.shop.qna.support.QnaParam;
import saleson.shop.sendmaillog.SendMailLogService;
import saleson.shop.user.UserService;

import com.onlinepowers.framework.sequence.service.SequenceService;
import com.onlinepowers.framework.web.domain.ListParam;
import com.onlinepowers.framework.web.pagination.Pagination;


@Service("qnaService")
public class QnaServiceImple implements QnaService{
	private static final Logger log = LoggerFactory.getLogger(QnaServiceImple.class);
	
	@Autowired 
	QnaMapper qnaMapper;
	
	@Autowired
	private SequenceService sequenceService;
	
	@Autowired
	private MailConfigService mailConfigService;
	
	@Autowired
	private SendMailLogService sendMailLogService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ItemService itemService;

	@Autowired
	private FileService fileService;

	@Override
	public void insertQna(Qna qna) {
		qna.setUserId(UserUtils.getUserId());
		qna.setQnaId(sequenceService.getId("OP_QNA"));

		// 1:1 문의 이미지가 존재한다면
		if(qna.getQnaImageFile() != null){
			if(qna.getQnaImageFile().getSize() > 0){
				MultipartFile multipartFile = qna.getQnaImageFile();

				String[] ITEM_DEFAULT_IMAGE_SAVE_SIZE = new String[] {"500x-1", "150x-1"};

				String saveFolderName = "inquiry";

				String fileExtension = FileUtils.getExtension(multipartFile.getOriginalFilename());
				String defaultFileName = qna.getQnaId() + "." + fileExtension;

				// 1. 업로드 경로설정
//				String uploadPath = FileUtils.getDefaultUploadPath() + File.separator + "item" + File.separator + qna.getItemId() + File.separator + saveFolderName;
				String uploadPath = FileUtils.getDefaultUploadPath() + File.separator + saveFolderName;
				fileService.makeUploadPath(uploadPath);

				// 2. 파일명 중복파일 삭제
				try {
					FileUtils.delete(uploadPath, ShopUtils.unescapeHtml(defaultFileName));
				} catch (IOException e1) {
					log.warn(e1.getMessage());
				}

				// 2-1. 새로운 파일명.
				defaultFileName = FileUtils.getNewFileName(uploadPath, defaultFileName);

				// 3. 저장될 파일
				File saveFile = new File(uploadPath + File.separator + defaultFileName);

				// 4. 섬네일 사이즈
				String[] thumbnailSize = StringUtils.delimitedListToStringArray(ITEM_DEFAULT_IMAGE_SAVE_SIZE[0], "x");

				// 생성.
				try {
					ThumbnailUtils.create(multipartFile, saveFile, Integer.parseInt(thumbnailSize[0]), Integer.parseInt(thumbnailSize[1]));
				} catch (IOException e) {
					log.error("ThumbnailUtils.create(... Exception : {}", e.getMessage(), e);
				}

				// 대표 이미지명
				qna.setQnaImage(defaultFileName);
			}
		}

		qnaMapper.insertQna(qna);
	}

	@Override
	public void updateQna(Qna qna) {
		qnaMapper.updateQna(qna);
	}

	@Override
	public void deleteQna(Qna qna) {
		qnaMapper.deleteQna(qna);
	}

	@Override
	public void insertQnaAnswer(QnaAnswer qnaAnswer) {
		qnaAnswer.setQnaAnswerId(sequenceService.getId("OP_QNA_ANSWER"));
		qnaMapper.insertQnaAnswer(qnaAnswer);
	}

	@Override
	public void updateQnaAnswer(QnaAnswer qnaAnswer) {
		qnaMapper.updateQnaAnswer(qnaAnswer);
	}

	@Override
	public void deleteQnaAnswer(QnaAnswer qnaAnswer) {
		qnaMapper.deleteQnaAnswer(qnaAnswer);
	}

	@Override
	public List<Qna> getQnaListByParam(QnaParam qnaParam) {
		return qnaMapper.getQnaListByParam(qnaParam);
	}

	@Override
	public Qna getQnaByQnaId(int qnaId) {
		return qnaMapper.getQnaByQnaId(qnaId);
	}
	
	@Override
	public int getQnaListCountByParam(QnaParam qnaParam) {
		return qnaMapper.getQnaListCountByParam(qnaParam);
	}

	@Override
	public List<QnaAnswer> getQnaAnswerListByQnaId(int qnaId) {
		return qnaMapper.getQnaAnswerListByQnaId(qnaId);
	}
	
	@Override
	public QnaAnswer getQnaAnswerByQnaId(int qnaId) {
		return qnaMapper.getQnaAnswerByQnaId(qnaId);
	}
	
	@Override
	public void setQnaListPagination(QnaParam qnaParam){
		
		// LIMIT 값이 존재하면 페이징 처리 안함
		if (qnaParam.getLimit() <= 0) {
		
			int count  = qnaMapper.getQnaListCountByParam(qnaParam);
			Pagination pagination = Pagination.getInstance(count, qnaParam.getItemsPerPage());
			
			ShopUtils.setPaginationInfo(pagination, qnaParam.getConditionType(), qnaParam.getPage());
			
			qnaParam.setPagination(pagination);
			
		}
		
	}	
	
	@Override
	public void deleteQnaData(ListParam listparam) {
		
		if (listparam.getId() != null) {

			for (String qnaId : listparam.getId()) {
				Qna qna = new Qna();
				qna.setQnaId(Integer.parseInt(qnaId));
				qnaMapper.deleteQna(qna);
			}	
		}
	}
	
	@Override
	public void updateQnaAnswerCount(int qnaId) {
		qnaMapper.updateQnaAnswerCount(qnaId);
	}

}
