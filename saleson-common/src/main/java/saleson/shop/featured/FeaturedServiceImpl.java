package saleson.shop.featured;

import com.onlinepowers.framework.file.service.FileService;
import com.onlinepowers.framework.sequence.service.SequenceService;
import com.onlinepowers.framework.util.DateUtils;
import com.onlinepowers.framework.util.FileUtils;
import com.onlinepowers.framework.util.StringUtils;
import com.onlinepowers.framework.util.ValidationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import saleson.common.Const;
import saleson.common.utils.ItemUtils;
import saleson.common.utils.ShopUtils;
import saleson.shop.eventcode.EventCodeService;
import saleson.shop.featured.domain.Featured;
import saleson.shop.featured.domain.FeaturedReply;
import saleson.shop.featured.support.*;

import java.io.File;
import java.io.IOException;
import java.util.*;

@Service("featuredService")
public class FeaturedServiceImpl implements FeaturedService {
	private static final Logger log = LoggerFactory.getLogger(FeaturedServiceImpl.class);
	
	@Autowired
	private FeaturedMapper featuredMapper;
	
	@Autowired
	private FeaturedMapperBatch featuredMapperBatch;
	
	
	@Autowired
	private SequenceService sequenceService;
	
	@Autowired
	private FileService fileService;

	@Autowired
	private EventCodeService eventCodeService;

	@Override
	public int getFeaturedCountByParam(FeaturedParam featuredParam) {
		return featuredMapper.getFeaturedCountByParam(featuredParam);
	}

	@Override
	public List<Featured> getFeaturedListByParam(FeaturedParam featuredParam) {
		return featuredMapper.getFeaturedListByParam(featuredParam);
	}

	@Override
	public Featured getFeaturedById(FeaturedParam featuredParam) {
		return featuredMapper.getFeaturedById(featuredParam);
	}

	@Override
	public void deleteFeaturedsById(FeaturedParam featuredParam) {
		featuredMapper.deleteFeaturedsById(featuredParam);
	}

	@Override
	public int insertFeatured(Featured featured, FeaturedItemParam featuredItemParam) {
		
		// ordering 업데이트
		featuredMapper.updateFeaturedOrderingAll();

		// 등록.
		featured.setOrdering(1);
		featured.setFeaturedId(sequenceService.getId("OP_FEATURED"));
		featured.setFeaturedCode(featuredArrayToString(featured.getFeaturedCodeChecked()));
		featuredFileSave(featured);
		featuredMapper.insertFeatured(featured);

		int featuredId = featured.getFeaturedId();
		FeaturedItem featuredItem = new FeaturedItem();

		/* 생성된featuredId를 FeaturedItem에 설정 */
		featuredItem.setFeaturedId(featuredId);
		if (!"".equals(featuredItemParam.getProdString()) && featuredItemParam.getProdString()!=null) {
			saveFeaturedItem(featuredItem, featuredItemParam);
		}

		return featured.getFeaturedId();
	}

	@Override
	public void updateFeaturedById(Featured featured, FeaturedItemParam featuredItemParam) {
		featured.setFeaturedCode(featuredArrayToString(featured.getFeaturedCodeChecked()));
		featuredFileSave(featured);
		featuredMapper.updateFeaturedById(featured);
		
		/* 기존 featuredItem을 삭제 */
		featuredMapper.deleteFeaturedItemById(featured);
		FeaturedItem featuredItem = new FeaturedItem();
		
		/* 기존featuredId를 FeaturedItem에 설정 */
		featuredItem.setFeaturedId(featured.getFeaturedId());
		
		if (!"".equals(featuredItemParam.getProdString())) {
			saveFeaturedItem(featuredItem, featuredItemParam);
		}
	}
	
	
	@Override
	@Transactional
	public void mergeEvent(Featured featured, FeaturedItemParam featuredItemParam) {
		featured.setFeaturedCode(featuredArrayToString(featured.getFeaturedCodeChecked()));
		featured.setOrdering(1);
		
		if (featured.getFeaturedId() == 0) {
			// ordering 업데이트
			featuredMapper.updateFeaturedOrderingAll();
			
			// getSequence
			featured.setFeaturedId(sequenceService.getId("OP_FEATURED"));
		}
		featuredFileSave(featured);
		
		featuredMapper.mergeEvent(featured);
		
		/* 기존 featuredItem을 삭제 */
		featuredMapper.deleteFeaturedItemById(featured);
		FeaturedItem featuredItem = new FeaturedItem();
		
		/* 기존featuredId를 FeaturedItem에 설정 */
		featuredItem.setFeaturedId(featured.getFeaturedId());
		saveFeaturedItem(featuredItem, featuredItemParam);
	}
	
	@Override
	public void deleteImageByItemId(FeaturedParam featuredParam) {
		
		Featured featured = getFeaturedById(featuredParam);
		int fileType = featuredParam.getFileType();
		String detailsUploadBase = "/featured/" + featured.getFeaturedId();
		
		String featuredImage = detailsUploadBase + "/featured/" + ShopUtils.unescapeHtml(featured.getFeaturedImage());
		if(fileType==1) {
			featuredImage = detailsUploadBase + "/featured/" + ShopUtils.unescapeHtml(featured.getThumbnailImage());
		}
		else if(fileType==2) {
			featuredImage = detailsUploadBase + "/featured/" + ShopUtils.unescapeHtml(featured.getFeaturedImage());
		}
		else if(fileType==3) {
			featuredImage = detailsUploadBase + "/featured/" + ShopUtils.unescapeHtml(featured.getFeaturedImageMobile());
		}
		else if(fileType==4) {
			featuredImage = detailsUploadBase + "/featured/" + ShopUtils.unescapeHtml(featured.getThumbnailImageMobile());
		}
		
		FileUtils.delete(featuredImage);
		featured.setFileType(featuredParam.getFileType());
		featuredMapper.updateFeaturedImageById(featured);
		
		// 2. 상품 이미지 정보 업데이트.
		//itemMapper.updateItemImageOfItemByItemId(itemId);
		
	}

	@Override
	public int getFeaturedCountByUrl(String featuredUrl) {
		return featuredMapper.getFeaturedCountByUrl(featuredUrl);
	}

	@Override
	public void updateFeaturedOrdering(FeaturedListParam featuredListParam) {
		
		if (featuredListParam.getFeaturedIds() != null && featuredListParam.getStartOrdering() > 0) {
			int ordering = featuredListParam.getStartOrdering();
			
			Featured featured = null;
			for (String featuredId : featuredListParam.getFeaturedIds()) {
				featured = new Featured(Integer.parseInt(featuredId), ordering);
				featured.setFeaturedCode(featuredListParam.getFeaturedCode());
				featuredMapperBatch.updateFeaturedOrdering(featured);
				
				ordering++;
			}
		
		}
	}
	
	@Override
	public List<String> getUserDefGroupById(FeaturedParam featuredParam) {
		return featuredMapper.getUserDefGroupById(featuredParam);
	}
	
	/* featuredItemList 받아오기 */
	@Override
	public List<FeaturedItem> getFeaturedItemListByParam(FeaturedParam featuredParam) {
		return featuredMapper.getFeaturedItemListByParam(featuredParam);
	}

	/* 기존의 FeaturedItem을 id로 기준으로 삭제 */
	@Override
	public void deleteFeaturedItemById(Featured featured) {
		featuredMapper.deleteFeaturedItemById(featured);
	}
	
	@Override
	public List<Featured> getThemeList(){
		return featuredMapper.getThemeList();
	}
	
	/* 출력될 Item의 Category 정보 가져오기 */
	@Override
	public List<FeaturedItem> getItemCategoriesByParam(FeaturedParam featuredParam) {
		return featuredMapper.getItemCategoriesByParam(featuredParam);
	}
	
	
	/** Not Overrides */
	public String featuredArrayToString(String[] featuredCodeChecked){
		String value = "";
		
		if(featuredCodeChecked != null){
			for(int i = 0; i < featuredCodeChecked.length;i++){
				if( i == 0 ){
					value += featuredCodeChecked[i];
				} else {
					value += "|"+featuredCodeChecked[i];
				}
			}
		}
		
		return value;
	}
	
	/* FeaturedItem을 저장 */
	public void saveFeaturedItem(FeaturedItem featuredItem, FeaturedItemParam featuredItemParam) {
		String[] prodStrTmp = featuredItemParam.getProdString().split(",");
		int len = prodStrTmp.length;
		int displayOrder = 0;
		if (featuredItemParam.getProdString()!=null) {
			if(featuredItemParam.getUserGroup()==null) {
				featuredItem.setUserDefGroup("");
				for (int i=0; i<len;  i++) {
					displayOrder = 0;
					for (String itemId : prodStrTmp[i].split("~")) {
						try {
							Integer.parseInt(itemId);
						} catch (Exception e) {
							continue;
						}
						featuredItem.setItemId(Integer.parseInt(itemId));
						featuredItem.setDisplayOrder(displayOrder++);
						featuredMapper.insertFeaturedItem(featuredItem);
					}
				}
			}
			else if(featuredItemParam.getUserGroup() != null && featuredItemParam.getUserGroup().length>0) {
				for(int i=0; i<len; i++) {
					String groupId = featuredItemParam.getUserGroup()[i];
					featuredItem.setUserDefGroup(groupId);
					for (int j=0; j<len;  j++) {
						displayOrder = 0;
						if (i == j) {
							if(!"".equals(prodStrTmp[j]) && prodStrTmp[j] != null) {
								for (String itemId : prodStrTmp[j].split("~")) {
									try {
										Integer.parseInt(itemId);
									} catch (Exception e) {
										continue;
									}
									featuredItem.setItemId(Integer.parseInt(itemId));
									featuredItem.setDisplayOrder(displayOrder++);
									featuredMapper.insertFeaturedItem(featuredItem);
								}
							}
						}
					}
				}
			}
		}
	}
	
	public void featuredFileSave(Featured featured){
		if(featured.getThumbnailFile() != null) {
			if (featured.getThumbnailFile().getSize() > 0) {
				fileSave(featured,1);
			}
		}
		if(featured.getFeaturedFile() != null) {
			if (featured.getFeaturedFile().getSize() > 0) {
				fileSave(featured,2);
			}
		}
		if(featured.getFeaturedFileMobile() != null) {
			if (featured.getFeaturedFileMobile().getSize() > 0) {
				fileSave(featured,3);
			}
		}
		if(featured.getThumbnailFileMobile() != null) {
			if (featured.getThumbnailFileMobile().getSize() > 0) {
				fileSave(featured,4);
			}
		}
	}
	
	/* fileType = 1,섬네일 / 2,대표이미지 / 3,모바일 대표이미지 / 4,모바일 섬네일이미지 */
	public void fileSave(Featured featured, int fileType) {
		MultipartFile multipartFile = null;
		if(fileType==1)	{
			multipartFile = featured.getThumbnailFile();
		}
		else if(fileType==2)	{
			multipartFile = featured.getFeaturedFile();
		}
		else if(fileType==3)	{
			multipartFile = featured.getFeaturedFileMobile();
		}
		else if(fileType==4)	{
			multipartFile = featured.getThumbnailFileMobile();
		}

		if (multipartFile != null) {


			String ITEM_DEFAULT_IMAGE_SAVE_FOLDER = "featured";
			String ITEM_DEFAULT_IMAGE_SAVE_SIZE = "250x250";


			String defaultFileName = "";
			if (fileType == 1) {
				defaultFileName = DateUtils.getToday(Const.DATETIME_FORMAT) + "_thumb." + FileUtils.getExtension(multipartFile.getOriginalFilename());
			} else if (fileType == 2) {
				defaultFileName = DateUtils.getToday(Const.DATETIME_FORMAT) + "." + FileUtils.getExtension(multipartFile.getOriginalFilename());
			} else if (fileType == 3) {
				defaultFileName = DateUtils.getToday(Const.DATETIME_FORMAT) + "_mobile." + FileUtils.getExtension(multipartFile.getOriginalFilename());
			} else if (fileType == 4) {
				defaultFileName = DateUtils.getToday(Const.DATETIME_FORMAT) + "_thumb_mobile." + FileUtils.getExtension(multipartFile.getOriginalFilename());
			}
			//defaultFileName = item.getItemUserCode() + "." + FileUtils.getExtension(multipartFile.getOriginalFilename()); // 7esthe

			String saveFolderName = ITEM_DEFAULT_IMAGE_SAVE_FOLDER;

			// 1. 업로드 경로설정
			String uploadPath = FileUtils.getDefaultUploadPath() + File.separator + "featured" + File.separator + featured.getFeaturedId() + File.separator + saveFolderName;
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
			//String[] thumbnailSize = StringUtils.delimitedListToStringArray(ITEM_DEFAULT_IMAGE_SAVE_SIZE, "x");


			// 생성.
			try {
				FileCopyUtils.copy(multipartFile.getBytes(), saveFile);
				//ThumbnailUtils.create(multipartFile, saveFile, Integer.parseInt(thumbnailSize[0]), Integer.parseInt(thumbnailSize[1]));
			} catch (IOException e) {
				log.error("FileCopyUtils.copy(multipartFile.getBytes(), saveFile) : {}", e.getMessage(), e);
			}
			//fileService.createThumbnail(saveFile, uploadPath, newFileName, thumbnailSize, "0");

			if (fileType == 1) {
				// 섬네일 파일명
				featured.setThumbnailImage(defaultFileName);
			} else if (fileType == 2) {
				// 대표이미지 파일명
				featured.setFeaturedImage(defaultFileName);
			} else if (fileType == 3) {
				// 모바일 대표이미지 파일명
				featured.setFeaturedImageMobile(defaultFileName);
			} else if (fileType == 4) {
				// 모바일 섬네일이미지 파일명
				featured.setThumbnailImageMobile(defaultFileName);
			}
		}
		//item.setItemImage(defaultFileName);
	}
	
	@Override
	public List<Featured> getFeaturedListByParamForFront(FeaturedParam featuredParam) {
		featuredParam.setPrivateTypes(ItemUtils.getPrivateTypes());
		return featuredMapper.getFeaturedListByParamForFront(featuredParam);
	}
	
	@Override
	public int getFeaturedCountByParamForFront(FeaturedParam featuredParam) {
		featuredParam.setPrivateTypes(ItemUtils.getPrivateTypes());
		return featuredMapper.getFeaturedCountByParamForFront(featuredParam);
	}
	
	@Override
	public List<HashMap<String, String>> getItemTypeList(String prodState, FeaturedParam featuredParam) {
		
		List<HashMap<String, String>> itemTypeList = new ArrayList<>();
		
		if ("1".equals(prodState)) { // 기본선택
			
			this.setItemTypeList(itemTypeList, "-", "-");
			
		} else if ("2".equals(prodState)) { // 제품카테고리
			
			List<FeaturedItem> categoriesList = this.getItemCategoriesByParam(featuredParam);
			
			for (FeaturedItem featuredItem : categoriesList) {
				
				this.setItemTypeList(itemTypeList, featuredItem.getCategoryId(), featuredItem.getCategoryName());
				
			}
			
		} else if ("3".equals(prodState)) { // 사용자 정렬
			
			List<String> userDefGroupList = this.getUserDefGroupById(featuredParam);
			
			for (String userDefGroup : userDefGroupList) {
				
				this.setItemTypeList(itemTypeList, userDefGroup, userDefGroup);
				
			}
			
		}
		
		return itemTypeList;
	}
	
	private void setItemTypeList(List<HashMap<String, String>> itemTypeList, String id, String name) {
		HashMap<String, String> map = new HashMap<>();
		
		map.put(Featured.ITEM_TYPE_ID_KEY, id);
		map.put(Featured.ITEM_TYPE_NAME_KEY, name);
		
		itemTypeList.add(map);
	}
	
	@Override
	public HashMap<String, List<FeaturedItem>> getItemListMap(String prodState, FeaturedParam featuredParam) {
		List<FeaturedItem> itemList = this.getFeaturedItemListByParam(featuredParam);
		
		HashMap<String, List<FeaturedItem>> itemListMap = null;
		
		if (ValidationUtils.isNotNull(itemList) && !itemList.isEmpty()) {
			
			List<HashMap<String, String>> itemTypeList = this.getItemTypeList(prodState, featuredParam);
			
			itemListMap = new HashMap<String, List<FeaturedItem>>();
			
			for (HashMap<String, String> itemTypeMap : itemTypeList) {
				
				String itemType = itemTypeMap.get(Featured.ITEM_TYPE_ID_KEY);
						
				List<FeaturedItem> tempItemList = new ArrayList<>();
				
				for (FeaturedItem item: itemList) {
					 if ("2".equals(prodState)) {  // 제품카테고리
						 if (itemType.equals(item.getCategoryId()==null ? "-1" : item.getCategoryId())) {
							 tempItemList.add(item);
						 }
						 
					 } else if ("3".equals(prodState)) { // 사용자 정렬
						 
						 if (itemType.equals(item.getUserDefGroup())) {
							 tempItemList.add(item);
						 }
						 
					 } else {
						 tempItemList.add(item);
					 }
				}
				
				itemListMap.put(itemType, tempItemList);
				
			}
			
		}
		
		return itemListMap;
	}

	@Override
	public List<String> getItemUserCodesByItemListMap(HashMap<String, List<FeaturedItem>> itemListMap) {

		Set<String> itemUserCodes = new HashSet<>();
		if (itemListMap != null && !itemListMap.isEmpty()) {

			for (String key : itemListMap.keySet()) {

				List<FeaturedItem> list = itemListMap.get(key);

				if (list != null && !list.isEmpty()) {
					list.forEach(i-> {
						itemUserCodes.add(i.getItemUserCode());
					});
				}
			}
		}

		return new ArrayList<>(itemUserCodes);
	}

	@Override
	public int getFeaturedReplyCountByParam(FeaturedReplyParam featuredReplyParam) {
		return featuredMapper.getFeaturedReplyCountByParam(featuredReplyParam);
	}

	@Override
	public List<FeaturedReply> getFeaturedReplyByParam(FeaturedReplyParam featuredReplyParam) {
		return featuredMapper.getFeaturedReplyByParam(featuredReplyParam);
	}

	@Override
	public void insertFeaturedReply(FeaturedReply featuredReply) {
		featuredMapper.insertFeaturedReply(featuredReply);
	}

	@Override
	public void updateDisplayReply(FeaturedReply featuredReply) {
		featuredMapper.updateDisplayReply(featuredReply);
	}

	@Override
	public int updateEventCode(int featuredId) {

		int count = 0;
		FeaturedParam featuredParam = new FeaturedParam();
		featuredParam.setFeaturedId(featuredId);
		Featured featured = getFeaturedById(featuredParam);

		if (featured != null) {

			String code = eventCodeService.saveEventCode(featured,"featured","view");

			if (!StringUtils.isEmpty(code)) {
				featured.setEventCode(code);
				count = featuredMapper.updateEventCode(featured);
			}
		}

		return count;
	}
}
