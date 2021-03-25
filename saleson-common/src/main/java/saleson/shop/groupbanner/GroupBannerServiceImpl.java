package saleson.shop.groupbanner;

import com.onlinepowers.framework.file.service.FileService;
import com.onlinepowers.framework.sequence.service.SequenceService;
import com.onlinepowers.framework.util.DateUtils;
import com.onlinepowers.framework.util.FileUtils;
import com.onlinepowers.framework.util.StringUtils;
import com.onlinepowers.framework.util.ThumbnailUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import saleson.common.Const;
import saleson.shop.groupbanner.domain.GroupBanner;
import saleson.shop.groupbanner.support.GroupBannerManagerException;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service("groupBannerService")
public class GroupBannerServiceImpl implements GroupBannerService {

	@Autowired
	private GroupBannerMapper groupBannerMapper;
	
	@Autowired
	private SequenceService sequenceServices;
	
	@Autowired
	private FileService fileService;
	
	@Override
	public List<GroupBanner> getGroupBannerByCategoryGroupId(int categoryGroupId) {
		return groupBannerMapper.getGroupBannerByCategoryGroupId(categoryGroupId);
	}
	
	@Override
	public void insertGroupBanner(GroupBanner groupBanner) {
		groupBanner.setCategoryGroupBannerId(sequenceServices.getId("OP_CATEGORY_GROUP_BANNER"));
		groupBannerMapper.insertGroupBanner(groupBanner);
	}

	@Override
	public void updateGroupBanner(GroupBanner groupBanner) {
		groupBannerMapper.updateGroupBanner(groupBanner);
	}

	@Override
	public void deleteGroupBannerById(int categoryGroupBannerId) {
		groupBannerMapper.deleteGroupBannerById(categoryGroupBannerId);
	}

	@Override
	public void editGroupBanner(GroupBanner groupBanner) {
		
		if (groupBanner.getCategoryGroupId() == 0) {
			throw new GroupBannerManagerException();
		}
		
		if (groupBanner.getWriteDatas() == null) {
			throw new GroupBannerManagerException();
		}
		
		// 파일 업로드수 체크
		int fileCount = 0;
		for(GroupBanner banner : groupBanner.getWriteDatas()) {
			if(banner.getUploadFile() != null) {
				fileCount++;
			} else {
				if (banner.getCategoryGroupBannerId() > 0 && StringUtils.isNotEmpty(banner.getFileName())) {
					fileCount++;
				}
			}
		}
		
		// 최소 업로드 필요수 검증
		String redirectUrl = "/opmanager/group-banner/form/" + groupBanner.getCategoryGroupId();
		if (fileCount < groupBanner.getUploadFileMinSize()) {
			String message = "최소 " + groupBanner.getUploadFileMinSize() + "개의 이미지를 등록하셔야 합니다.";
			throw new GroupBannerManagerException(message, redirectUrl);
		}
		
		int displayOrder = 0;
		for(GroupBanner banner : groupBanner.getWriteDatas()) {
			
			// 노출 순서 적용
			banner.setDisplayOrder(displayOrder++);
			banner.setCategoryGroupId(groupBanner.getCategoryGroupId());
			
			// 신규 등록
			if (banner.getCategoryGroupBannerId() == 0) {
				
				if (StringUtils.isNotEmpty(banner.getUploadFile().getOriginalFilename())) {
					String fileName = this.uploadFile(banner);
					if (StringUtils.isEmpty(fileName)) {
						throw new GroupBannerManagerException("파일 업로드에 실패 하였습니다.", redirectUrl);
					}
					
					banner.setFileName(fileName);
					this.insertGroupBanner(banner);
				}
				
			} else {
				
				// 파일 수정인 경우
				if (StringUtils.isNotEmpty(banner.getUploadFile().getOriginalFilename())) {
					String fileName = this.uploadFile(banner);
					if (StringUtils.isEmpty(fileName)) {
						throw new GroupBannerManagerException("파일 업로드에 실패 하였습니다.", redirectUrl);
					}
					
					banner.setFileName(fileName);
					this.updateGroupBanner(banner);
				} else {
					
					if ("Y".equals(banner.getDeleteFlag())) {
						groupBannerMapper.deleteGroupBannerById(banner.getCategoryGroupBannerId());
					} else {
						this.updateGroupBanner(banner);
					}
					
				}
				
			}
		}
	}

	private String uploadFile(GroupBanner banner) {
		String fileName = "";
		
		if (banner.getUploadFile() == null) {
			return "";
		}
		
		if (banner.getUploadFile().getSize() == 0) {
			return "";
		}
		
		String fileExtension = FileUtils.getExtension(banner.getUploadFile().getOriginalFilename());
		String defaultFileName = DateUtils.getToday(Const.DATETIME_FORMAT) + "_" + banner.getDisplayOrder() + "." + fileExtension;
				
		// 1. 업로드 경로설정
		String uploadPath = banner.getDefaultFilePath();
		fileService.makeUploadPath(uploadPath);
		
		// 2. 새로운 파일명.
		defaultFileName = FileUtils.getNewFileName(uploadPath, defaultFileName);

		// 3. 저장될 파일 
		File saveFile = new File(uploadPath + File.separator + defaultFileName);
	
		// 생성.
		try {
			ThumbnailUtils.create(banner.getUploadFile(), saveFile, banner.getThumbnailWidth(), banner.getThumbnailHeight());
			
			fileName = defaultFileName;
		} catch (IOException e) {
			throw new GroupBannerManagerException("파일 업로드에 실패 하였습니다.", e);
		}
		
		return fileName;
	}
}
