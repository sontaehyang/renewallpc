package saleson.shop.display;

import com.onlinepowers.framework.exception.UserException;
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
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import saleson.common.Const;
import saleson.common.utils.ItemUtils;
import saleson.common.utils.ShopUtils;
import saleson.common.utils.UserUtils;
import saleson.shop.display.domain.*;
import saleson.shop.display.support.DisplayItemParam;
import saleson.shop.display.support.DisplayParam;
import saleson.shop.display.support.DisplaySnsParam;
import saleson.shop.group.domain.Group;
import saleson.shop.item.domain.Item;
import saleson.shop.item.domain.ItemSpot;
import saleson.shop.item.support.ItemListParam;
import saleson.shop.item.support.ItemParam;

import java.io.File;
import java.io.IOException;
import java.util.*;

@Service("displayService")
public class DisplayServiceImpl implements DisplayService {
	private static final Logger log = LoggerFactory.getLogger(DisplayServiceImpl.class);

	@Autowired
	private DisplayMapper displayMapper;
	
	@Autowired
	private FileService fileService;

	@Autowired
	private SequenceService sequenceService;

	// 전시 상품 조회 - 관리자용
	@Override
	public List<DisplayItem> getDisplayItemListByParamForManager(DisplayItemParam param) {
		return displayMapper.getDisplayItemListByParam(param);
	}

	// 전시 상품 조회
	@Override
	public List<DisplayItem> getDisplayItemListByParam(DisplayItemParam param){
		param.setConditionType("FRONT");
		return displayMapper.getDisplayItemListByParam(param);
	}

	// 전시 상품 조회 Count
	@Override
	public int getDisplayItemListCountByParam(DisplayItemParam param) {
		param.setConditionType("FRONT");
		return displayMapper.getDisplayItemListCountByParam(param);
	}

	// 그룹 코드별 전시 상품 조회
	@Override
	public List<DisplayItem> getDisplayItemList(String displayGroupCode) {
		return this.getDisplayItemList(displayGroupCode, "");
	}

	// 그룹 코드별 전시 상품 조회 ()
	@Override
	public List<DisplayItem> getDisplayItemList(String displayGroupCode, String displaySubCode) {
		DisplayItemParam param = new DisplayItemParam();
		param.setConditionType("FRONT");
		param.setDisplayGroupCode(displayGroupCode);
		param.setDisplaySubCode(displaySubCode);

		if (!UserUtils.isManagerLogin()) {
			// 전용상품 조회
			param.setPrivateTypes(ItemUtils.getPrivateTypes());
		}

		return displayMapper.getDisplayItemListByParam(param);
	}

	// 그룹코드에 존재하는 하위 코드 개수
	@Override
	public int getDisplayItemSubCodeCountByGroupCode(String displayGroupCode) {
		//                    그룹코드에 존재하는 하위 코드 개수
		return displayMapper.getDisplayItemSubCodeCountByGroupCode(displayGroupCode);
	}

	// 전시 상품 등록
	@Override
	public void insertDisplayItem(DisplayItemParam param) {
		
		displayMapper.deleteDisplayItemByParam(param);
		if (param.getDisplayItemIds() != null) {
			
			int ordering = 0; 
			for(String id : param.getDisplayItemIds()) {
				
				DisplayItem item = new DisplayItem(param);
				item.setItemId(Integer.parseInt(id));
				item.setOrdering(ordering++);
				displayMapper.insertDisplayItem(item);
			}
			
		}
		
	}

	// 스팟 상품 카운트
	@Override
	public int getItemCountForSpot(ItemParam itemParam) {
		return displayMapper.getItemCountForSpot(itemParam);
	}

	public List<Group> getSpotApplyGroup(){
		return displayMapper.getSpotApplyGroup();
	}

	// 메인 스팟 상품 목록
	@Override
	public List<Item> getMainSpotItems(int limit) {
		ItemParam spotParam = new ItemParam();

		spotParam.setOrderBy("SPOT_END_DATE");
		spotParam.setSort("ASC");
		spotParam.setSpotStatus("1");
		spotParam.setDataStatusCode("1");
		spotParam.setLimit(limit);

		if (!UserUtils.isManagerLogin()) {
			// 전용상품 조회
			spotParam.setPrivateTypes(ItemUtils.getPrivateTypes());
		}

		return getItemListForSpot(spotParam);
	}

	// 스팟 상품 목록
	@Override
	public List<Item> getItemListForSpot(ItemParam itemParam) {
		return displayMapper.getItemListForSpot(itemParam);
	}

	// 스팟 상품 업데이트
	@Override
	public void updateItemSpot(Item item, ItemSpot itemSpot) {
		if (itemSpot.getSpotItemIds() != null) {
			for (int i = 0; i < itemSpot.getSpotItemIds().length; i++) {
				item.setItemId(Integer.parseInt(itemSpot.getSpotItemIds()[i]));
				item.setSpotFlag("Y");
				item.setSpotDiscountAmount(Integer.parseInt(itemSpot.getSpotDiscountAmounts()[i]));
				displayMapper.updateItemSpot(item);
			}
		}
	}

	// 스팟 상품 선택 삭제
	@Override
	public void deleteItemSpotFromListData(ItemListParam itemListParam) {
		if (itemListParam.getId() != null) {
			for (int i = 0; i < itemListParam.getId().length; i++) {
				Item item = new Item();
				item.setItemId(Integer.parseInt(itemListParam.getId()[i]));
				item.setSpotDiscountAmount(0);
				item.setSpotStartDate("");
				item.setSpotEndDate("");
				item.setSpotStartTime("");
				item.setSpotEndTime("");
				item.setSpotWeekDay("");
				item.setSpotFlag("N");
				displayMapper.updateItemSpot(item);
			}
		}
	}

	// 스팟 전체 일괄 할인률 변경
	@Override
	public void updateAllSpotDiscount(ItemSpot itemSpot){
		
		if (itemSpot.getId() == null) {
			return;
		}
		
		if ("per".equals(itemSpot.getDiscountType())) {
			if (itemSpot.getDiscountAmount() > 100) {
				throw new UserException("할인률은 100% 이하로 설정 가능합니다.");
			}
		}
		
		displayMapper.updateAllSpotDiscount(itemSpot);
	}

	// 스팟 한 항목 일괄 할인률 변경
	@Override
	public void updateOneSpotDiscount(ItemSpot itemSpot){
		displayMapper.updateOneSpotDiscount(itemSpot);
	}

	// 해당 코드에 따른 화면관리 정보 목록 조회
	@Override
	public DisplayTemplate getDisplayTemplateByGroupCode(String displayGroupCode) {
		return displayMapper.getDisplayTemplateByGroupCode(displayGroupCode);
	}

	// 해당 코드 정보 조회
	@Override
	public DisplayGroupCode getDisplayGroupCodeByGroupCode(String displayGroupCode) {
		return displayMapper.getDisplayGroupCodeByGroupCode(displayGroupCode);
	}

	// 전시 에디터 조회
	@Override
	public List<DisplayEditor> getDisplayEditorListByParam(DisplayParam displayParam) {
		return displayMapper.getDisplayEditorListByParam(displayParam);
	}

	// 전시 이미지 조회
	@Override
	public List<DisplayImage> getDisplayImageListByParam(DisplayParam displayParam) {
		return displayMapper.getDisplayImageListByParam(displayParam);
	}

	// 해당 코드에 따른 화면 전시 정보 데이터 업데이트
	@Override
	public void updateDisplayDataByGroupCode(DisplaySendData sendData) {
		
		String groupCode = sendData.getDisplayGroupCode();
		String viewTarget = sendData.getViewTarget();
		
		DisplayTemplate template = displayMapper.getDisplayTemplateByGroupCode(groupCode);
		
		List<DisplaySettingValue> settingValues = template.getDisplaySettingValueList();

		if (settingValues == null) {
			settingValues = new ArrayList<>();
		}
		
		for (int index=0 ; index<settingValues.size(); index++) {
			
			DisplaySettingValue settingValue = settingValues.get(index);

			if (settingValue == null) {
				continue;
			}
			
			try {
				if (settingValue.getType() == 1) {
					updateDisplayImageByGroupCode(groupCode, String.valueOf(index), viewTarget, sendData, index);
				} else if (settingValue.getType() == 2) {
					// 에디터
					String displayEditorContent = sendData.getDisplayEditorContents().get(index);
					if (!StringUtils.isEmpty(displayEditorContent)) {
						updateDisplayEditorByGroupCode(groupCode, String.valueOf(index), viewTarget, displayEditorContent);
					}
				} else if (settingValue.getType() == 3) {
					// 상품
					List<String> displayItemIds = sendData.getDisplayItemIds().get(index);
					if (ValidationUtils.isNotNull(displayItemIds) && !displayItemIds.isEmpty()) {
						updateDisplayItemByGroupCode(groupCode, String.valueOf(index), viewTarget, displayItemIds);
					}
				}
			} catch (Exception e) {
				log.warn("[Exception] updateDisplayDataByGroupCode : {}", e.getMessage(), e);
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			}
		}
		
	}
	
	/**
	 * 전시관리 이미지 등록
	 * @param groupCode
	 * @param subCode
	 * @param viewTarget,
	 * @param sendData
	 * @param dataIndex
	 * @throws Exception
	 */
	private void updateDisplayImageByGroupCode(String groupCode, String subCode, String viewTarget,
											   DisplaySendData sendData, int dataIndex) throws Exception {

		List<MultipartFile> displayImages = sendData.getDisplayImages().get(dataIndex);
		List<String> displayUrls = sendData.getDisplayUrls().get(dataIndex);
		List<String> fileNames = sendData.getFileNames().get(dataIndex);
		List<String> displayContents = sendData.getDisplayContents().get(dataIndex);
		List<String> displayColors = sendData.getDisplayColors().get(dataIndex);

		if (!StringUtils.isEmpty(displayImages) && !StringUtils.isEmpty(displayUrls) && !StringUtils.isEmpty(fileNames)
				&& !StringUtils.isEmpty(displayContents) && !StringUtils.isEmpty(displayColors)) {

			// 이미지 정보 삭제 (파일 삭제는 안함) 파일 삭제 이미지 수정이 있을경우만
			DisplayParam param = new DisplayParam();
			param.setDisplayGroupCode(groupCode);
			param.setDisplaySubCode(subCode);
			param.setViewTarget(viewTarget);
			displayMapper.deleteDisplayImageByParam(param);

			// MultipartFile 값이 있든 없든 리스트로 존재 하기에 사용함

			int ordering = 0;

			int fileIndex= 0;
			for (MultipartFile file : displayImages) {

				String displayUrl = displayUrls.isEmpty() ? "" : displayUrls.get(fileIndex);
				String fileName = fileNames.isEmpty() ? "" : fileNames.get(fileIndex);
				String displayContent = displayContents.isEmpty() ? "" : displayContents.get(fileIndex);
				String displayColor = displayColors.isEmpty() ? "" : displayColors.get(fileIndex);
				String displayImageName = "";

				if (StringUtils.isEmpty(fileName)) {
					displayImageName = imageSave(groupCode, subCode, file);
				} else {

					String tempFileName = imageSave(groupCode, subCode, file);

					if (!StringUtils.isEmpty(tempFileName)) {
						displayImageName = tempFileName;
						deleteImage(groupCode, subCode, fileName);
					} else {
						displayImageName = fileName;
					}

				}

				// 정보 1개 이상 입력시에만 INSERT (쓸데 없는 로우 증가 막기)
				if (!StringUtils.isEmpty(displayUrl) || !StringUtils.isEmpty(displayImageName) || !StringUtils.isEmpty(displayContent) || !StringUtils.isEmpty(displayColor)) {

					DisplayImage displayImage = new DisplayImage();

					displayImage.setDisplayGroupCode(groupCode);
					displayImage.setDisplaySubCode(subCode);
					displayImage.setViewTarget(viewTarget);
					displayImage.setOrdering(ordering);
					displayImage.setDisplayUrl(displayUrl);
					displayImage.setDisplayImage(displayImageName);
					displayImage.setDisplayContent(displayContent);
					displayImage.setDisplayColor(displayColor);

					displayMapper.insertDisplayImage(displayImage);

					ordering++;
				}

				fileIndex++;
			}
		}
	}
	
	/**
	 * 전시관리 상품 등록
	 * @param groupCode
	 * @param subCode
	 * @param viewTarget
	 * @param displayItemIds
	 */
	private void updateDisplayItemByGroupCode(String groupCode, String subCode, String viewTarget,
											  List<String> displayItemIds) throws Exception {
		
		if (ValidationUtils.isNotNull(displayItemIds) && !displayItemIds.isEmpty()) {
			
			//  itemId가 undefined 또는 값이 안들어 오는것 정체 처리
			
			List<String> ids = new ArrayList<>();
			
			for (String temp : displayItemIds) {
				if (!StringUtils.isEmpty(temp) && !"undefined".equals(temp)) {
					ids.add(temp);
				}
			}
			
			DisplayItemParam itemParam = new DisplayItemParam();
			
			itemParam.setDisplayGroupCode(groupCode);
			itemParam.setDisplaySubCode(subCode);
			itemParam.setViewTarget(viewTarget);
			itemParam.setDisplayItemIds(ids.toArray(new String[ids.size()]));
			
			this.insertDisplayItem(itemParam);
			
		}
		
	}
	
	/**
	 * 전시관리 에디터 등록
	 * @param groupCode
	 * @param subCode
	 * @param viewTarget
	 * @param displayEditorContent
	 */
	private void updateDisplayEditorByGroupCode(String groupCode, String subCode, String viewTarget,
												String displayEditorContent) throws Exception {
		
		DisplayParam param = new DisplayParam();
		param.setDisplayGroupCode(groupCode);
		param.setDisplaySubCode(subCode);
		param.setViewTarget(viewTarget);
		
		displayMapper.deleteDisplayEditorByParam(param);
		
		DisplayEditor displayEditor = new DisplayEditor();
		
		int ordering = 0; 
		displayEditor.setOrdering(ordering);
		displayEditor.setDisplayGroupCode(groupCode);
		displayEditor.setDisplaySubCode(subCode);
		displayEditor.setViewTarget(viewTarget);
		displayEditor.setDisplayEditorContent(displayEditorContent);
		
		displayMapper.insertDisplayEditor(displayEditor);
		
	}
	
	private String imageSave(String groupCode, String subCode, MultipartFile multipartFile){
		
		String newFileName = "";
		
		if(!multipartFile.isEmpty()){
			// 1. 업로드 경로설정
			String uploadPath = FileUtils.getDefaultUploadPath() +File.separator + "display" +File.separator + groupCode + File.separator + subCode;
			fileService.makeUploadPath(uploadPath);
	    	
			// 2. 파일명 
			String defaultFileName = DateUtils.getToday(Const.DATETIME_FORMAT) + "." + FileUtils.getExtension(multipartFile.getOriginalFilename());
			
			
			String uploadPathDefault = uploadPath + File.separator + "default";
			
			fileService.makeUploadPath(uploadPathDefault);
			newFileName = FileUtils.getNewFileName(uploadPathDefault, defaultFileName);
			
			// 3. 저장될 파일 
			File saveFile = new File(uploadPathDefault + File.separator + newFileName);
			
			// 4. 섬네일 사이즈 
			
			try {
	            //FileCopyUtils.copy(multipartFile.getBytes(), saveFile);
				//if( categoriesEdit.getEditKind().equals("1") ){
					// 원본 이미지 
				//if(categoriesEdit.getCode().equals("featured")) {
					// thumbnail 생성
					//ThumbnailUtils.create(multipartFile, saveFile, 840, 150);
				//} else {
					FileCopyUtils.copy(multipartFile.getBytes(), saveFile);
					//ThumbnailUtils.create(multipartFile, saveFile, 670, 320);
				//}  
				
				String uploadPathThumb = uploadPath + File.separator + "thumb";
				fileService.makeUploadPath(uploadPathThumb);
				
				File thumbnailFile = new File(uploadPathThumb + File.separator + newFileName);
				FileCopyUtils.copy(multipartFile.getBytes(), thumbnailFile);
				 
		        //ThumbnailUtils.create(multipartFile, thumbnailFile, 167, 80);
		        
			 	/*} else {
					
					ThumbnailUtils.create(multipartFile, saveFile, 840, 320);
					// thumbnail 생성
		            File thumbnailFile = new File(uploadPath + File.separator + "thumb" + File.separator + newFileName);
		            ThumbnailUtils.create(multipartFile, thumbnailFile, 210, 80);
					
				}*/
            
	        } catch (IOException e) {
				log.error("ERROR: {}", e.getMessage(), e);
	            throw new RuntimeException(e);
	        }
			
		}
		
		return newFileName;
	}
	
	
	private  void deleteImage (String groupCode, String subCode, String fileName) {
		try {
			
			FileUtils.delete( "/" + "display" + "/" + groupCode+File.separator +subCode+File.separator + "default" , ShopUtils.unescapeHtml(fileName));
			FileUtils.delete( "/"+ "display" + "/" + groupCode+File.separator +subCode+File.separator + "thumb" , ShopUtils.unescapeHtml(fileName));
			
		} catch (IOException e) {
			log.error("FileUtils.delete(..  : {}", e.getMessage(), e);
		}
	}

	// 전시 이미지 파일 삭제
	@Override
	public void deleteDisplayImageFile(DisplayImage displayImage) {
		
		DisplayParam param = new DisplayParam();
		param.setConditionType("DISPLAY_IMAGE_DELETE");
		param.setDisplayGroupCode(displayImage.getDisplayGroupCode());
		param.setDisplaySubCode(displayImage.getDisplaySubCode());
		param.setViewTarget(displayImage.getViewTarget());
		param.setOrdering(displayImage.getOrdering());
		
		DisplayImage tempDisplayImage = displayMapper.getDisplayImageByParam(param);

		displayImage.setDisplayImage(tempDisplayImage.getDisplayImage());
		
		displayMapper.deleteDisplayImageFile(displayImage);

		// 모든 데이터가 빈값일 경우, row 삭제
		if (StringUtils.isEmpty(tempDisplayImage.getDisplayUrl()) && StringUtils.isEmpty(tempDisplayImage.getDisplayContent()) && StringUtils.isEmpty(tempDisplayImage.getDisplayColor())) {
			displayMapper.deleteDisplayImageByParam(param);
		}

		deleteImage(displayImage.getDisplayGroupCode(), displayImage.getDisplaySubCode(), displayImage.getDisplayImage());
		
	}

	// 그룹코드별 전시정보 가져오기
	@Override
	public Display getDisplayByGroupCode(String displayGroupCode, String viewTarget) {
		
		Display display = new Display();
		
		display.setDisplayGroupCode(displayGroupCode);
		
		DisplayItemParam displayItemParam = new DisplayItemParam();
		displayItemParam.setDisplayGroupCode(displayGroupCode);
		displayItemParam.setViewTarget(viewTarget);
		if (!UserUtils.isManagerLogin()) {
			// 전용상품 조회
			displayItemParam.setPrivateTypes(ItemUtils.getPrivateTypes());
		}
		
		DisplayParam displayParam = new DisplayParam();
		
		displayParam.setDisplayGroupCode(displayGroupCode);
		displayParam.setViewTarget(viewTarget);
		
		if(!UserUtils.isManagerLogin()) {
			displayItemParam.setConditionType("FRONT");
		}
		
		display.setDisplayItemList(displayMapper.getDisplayItemListByParam(displayItemParam));
		display.setDisplayImageList(displayMapper.getDisplayImageListByParam(displayParam));
		display.setDisplayEditorList(displayMapper.getDisplayEditorListByParam(displayParam));
		

		return display;
	}

	// 등록된 상품중 미전시 상품수 조회
	@Override
	public HashMap<String, String> getNotDisplayDisplayItemCountForSubCode(String groupCode, String subCode) {
		DisplayItemParam displayParam = new DisplayItemParam();
		displayParam.setDisplayGroupCode(groupCode);
		displayParam.setDisplaySubCode(subCode);
		displayParam.setConditionType("CATEGORIES_NOT_DISPLAY_FLAG");
		return getDisplayItemInfo(displayMapper.getDisplayItemInfoForSubCodeByParam(displayParam));
	}

	// 등록된 상품중 품절 상품수 조회
	@Override
	public HashMap<String, String> getSoldOutDisplayItemCountForSubCode(String groupCode, String subCode) {
		DisplayItemParam displayParam = new DisplayItemParam();
		displayParam.setDisplayGroupCode(groupCode);
		displayParam.setDisplaySubCode(subCode);
		displayParam.setConditionType("CATEGORIES_SOLD_OUT");
		return getDisplayItemInfo(displayMapper.getDisplayItemInfoForSubCodeByParam(displayParam));
	}

	// 등록된 상품수 조회
	@Override
	public HashMap<String, String> getTotalDisplayItemCountForSubCode(String groupCode, String subCode) {
		DisplayItemParam displayParam = new DisplayItemParam();
		displayParam.setDisplayGroupCode(groupCode);
		displayParam.setDisplaySubCode(subCode);
		return getDisplayItemInfo(displayMapper.getDisplayItemInfoForSubCodeByParam(displayParam));
	}
	
	private HashMap<String, String> getDisplayItemInfo (List<HashMap<String, String>> list){
		
		HashMap<String, String> map = new HashMap<>();
		
		for (HashMap<String, String> info : list) {
			map.put(info.get("DISPLAY_SUB_CODE"), String.valueOf(info.get("COUNT")));
		}
		
		return map;
		
	}

	// 전시용 SNS 등록
	@Override
	public void insertDisplaySns(DisplaySns displaySns) {
		displaySns.setOrdering(1);
		displaySns.setSnsId(sequenceService.getId("OP_DISPLAY_SNS"));

		displayMapper.insertDisplaySns(displaySns);
	}

	// 전시용 SNS 수정
	@Override
	public void updateDisplaySns(DisplaySns displaySns) {
		displayMapper.updateDisplaySns(displaySns);
	}

	// 전시용 SNS 선택 삭제
	@Override
	public void deleteDisplaySnsByIds(DisplaySnsParam displaySnsParam) {
		displayMapper.deleteDisplaySnsByIds(displaySnsParam);
	}

	// 전시용 SNS 카운트
	@Override
	public int getDisplaySnsCount(DisplaySnsParam displaySnsParam) {
		return displayMapper.getDisplaySnsCount(displaySnsParam);
	}

	// 전시용 SNS 목록 조회
	@Override
	public List<DisplaySns> getDisplaySnsList(DisplaySnsParam displaySnsParam) {
		return displayMapper.getDisplaySnsList(displaySnsParam);
	}

	// 전시용 SNS 조회
	@Override
	public DisplaySns getDisplaySnsById(int snsId) {
		return displayMapper.getDisplaySnsById(snsId);
	}

	// 전시용 SNS 노출 순서
	@Override
	public void updateDisplaySnsOrdering(DisplaySnsParam displaySnsParam) {

		if (displaySnsParam.getSnsIds() != null && displaySnsParam.getStartOrdering() > 0) {
			int ordering = displaySnsParam.getStartOrdering();

			DisplaySns displaySns = null;
			for (String snsId : displaySnsParam.getSnsIds()) {
				displaySns = new DisplaySns(Integer.parseInt(snsId), ordering);
				displayMapper.updateDisplaySnsOrdering(displaySns);

				ordering++;
			}

		}
	}

	@Override
	public void setMainDisplayByGroupCode(Model model, String... groupCodes) {
		Map<String, Display> map = new HashMap<>();

		if (groupCodes != null && groupCodes.length > 0) {

			List<String> displayGroupCodes = Arrays.asList(groupCodes);

			DisplayParam displayParam = new DisplayParam();
			displayParam.setDisplayGroupCodes(displayGroupCodes);
			displayParam.setViewTarget("ALL");

			List<DisplayImage> imageList = getDisplayImageListByParam(displayParam);

			DisplayItemParam displayItemParam = new DisplayItemParam();
			displayItemParam.setDisplayGroupCodes(displayGroupCodes);
			displayItemParam.setViewTarget("ALL");
			if (!UserUtils.isManagerLogin()) {
				// 전용상품 조회
				displayItemParam.setPrivateTypes(ItemUtils.getPrivateTypes());
			}

			List<DisplayItem> itemList = getDisplayItemListByParam(displayItemParam);

			for (String groupCode : displayGroupCodes) {

				if (StringUtils.isEmpty(groupCode)) {
					continue;
				}

				Display display = new Display();
				boolean contentFlag = false;


				if (imageList != null && !imageList.isEmpty()) {

					List<DisplayImage> tempImageList = new ArrayList<>();
					for (DisplayImage image : imageList) {
						if (groupCode.equals(image.getDisplayGroupCode())) {
							tempImageList.add(image);
						}
					}

					if (!tempImageList.isEmpty()) {
						contentFlag = true;
						display.setDisplayImageList(tempImageList);
					}
				}

				if (itemList != null && !itemList.isEmpty()) {

					List<DisplayItem> tempitemList = new ArrayList<>();
					for (DisplayItem item : itemList) {
						if (groupCode.equals(item.getDisplayGroupCode())) {
							tempitemList.add(item);
						}
					}

					if (!tempitemList.isEmpty()) {
						contentFlag = true;
						display.setDisplayItemList(tempitemList);
					}
				}

				if (contentFlag) {
					map.put(groupCode, display);
				}

			}
		}

		model.addAttribute("displayContent", map);
	}

	@Override
	public List<DisplayItem> getFrontDisplayItemListByParam(DisplayItemParam param) {
		return displayMapper.getFrontDisplayItemListByParam(param);
	}
}
