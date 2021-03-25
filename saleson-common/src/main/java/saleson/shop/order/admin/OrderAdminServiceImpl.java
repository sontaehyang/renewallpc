package saleson.shop.order.admin;

import com.fasterxml.jackson.core.type.TypeReference;
import com.onlinepowers.framework.exception.UserException;
import com.onlinepowers.framework.security.userdetails.User;
import com.onlinepowers.framework.sequence.service.SequenceService;
import com.onlinepowers.framework.util.*;
import com.onlinepowers.framework.web.pagination.Pagination;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import saleson.common.Const;
import saleson.common.utils.ShopUtils;
import saleson.common.utils.UserUtils;
import saleson.seller.main.SellerMapper;
import saleson.seller.main.domain.Seller;
import saleson.shop.categories.CategoriesMapper;
import saleson.shop.config.domain.Config;
import saleson.shop.item.ItemService;
import saleson.shop.item.domain.Item;
import saleson.shop.item.domain.ItemOption;
import saleson.shop.order.OrderMapper;
import saleson.shop.order.admin.domain.*;
import saleson.shop.order.admin.support.ExcelTemplateV01;
import saleson.shop.order.admin.support.OrderAdminData;
import saleson.shop.order.admin.support.OrderAdminException;
import saleson.shop.order.admin.support.OrderAdminParam;
import saleson.shop.order.domain.Buyer;
import saleson.shop.order.domain.Receiver;
import saleson.shop.point.PointService;
import saleson.shop.user.UserMapper;
import saleson.shop.user.domain.UserDetail;
import saleson.shop.userlevel.UserLevelMapper;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service("orderAdminService")
public class OrderAdminServiceImpl implements OrderAdminService {
	private static final Logger log = LoggerFactory.getLogger(OrderAdminServiceImpl.class);

	@Autowired
	private OrderAdminMapper orderAdminMapper;
	
	@Autowired
	private ItemService itemService;
	
	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private SequenceService sequenceService;
	
	@Autowired
	private OrderMapper orderMapper;
	
	@Autowired
	private SellerMapper sellerMapper;
	
	@Autowired
	private PointService pointService;
	
	@Autowired
	private UserLevelMapper userLevelMapper;
	
	@Autowired
	private CategoriesMapper categoriesMapper;
	
	@Override
	public List<OrderAdminDetail> getOrderAdminListByParam(OrderAdminParam orderAdminParam) {
		
		int totalCount = orderAdminMapper.getOrderAdminCountByParam(orderAdminParam);
		
		if (orderAdminParam.getItemsPerPage() == 10) {
			orderAdminParam.setItemsPerPage(50);
		}
		
		Pagination pagination = Pagination.getInstance(totalCount, orderAdminParam.getItemsPerPage());
		orderAdminParam.setPagination(pagination);
		orderAdminParam.setLanguage(CommonUtils.getLanguage());
		
		List<OrderAdminDetail> list = orderAdminMapper.getOrderAdminListByParam(orderAdminParam);
		if (list != null) {
			for(OrderAdminDetail detail : list) {
				
				if ("v01".equals(detail.getTemplateVersion())) {
					
					try {
						
						ExcelTemplateV01 template = (ExcelTemplateV01) JsonViewUtils.jsonToObject(detail.getExcelData(), new TypeReference<ExcelTemplateV01>(){});
						
						Item item = itemService.getItemByItemUserCode(template.getItemUserCode());
						if (item == null) {
							
							detail.setError(true);
							detail.setErrorMessage("상품정보를 찾을수 없습니다. ["+ template.getItemUserCode() +"]");
							continue;
						} 
						
						if (StringUtils.isEmpty(template.getLoginId()) == false) {
							User user = userMapper.getUserByLoginId(template.getLoginId());
							if (user == null) {
								detail.setError(true);
								detail.setErrorMessage("회원정보를 찾을수 없습니다. ["+ template.getLoginId() +"]");
								
								continue;
							}
							
							Buyer buyer = new Buyer(user);
							buyer.setEmail(template.getBuyerEmail());
							buyer.setPhone(template.getBuyerMobile());
							buyer.setMobile(template.getBuyerMobile());
							buyer.setUserName(template.getBuyerName());
							
							detail.setBuyer(buyer);
						} else {
							
							Buyer buyer = new Buyer();
							
							buyer.setEmail(template.getBuyerEmail());
							buyer.setPhone(template.getBuyerMobile());
							buyer.setMobile(template.getBuyerMobile());
							buyer.setUserName(template.getBuyerName());
							
							detail.setBuyer(buyer);
							
						}
						
						detail.setItem(item);

						Receiver receiver = new Receiver();
						receiver.setReceiveName(template.getReceiveName());
						receiver.setReceivePhone(template.getReceivePhone());
						receiver.setReceiveMobile(template.getReceiveMobile());
						receiver.setReceiveZipcode(template.getReceiveZipcode());
						receiver.setReceiveAddress(template.getReceiveAddress());
						receiver.setReceiveAddressDetail(template.getReceiveAddressDetail());
						receiver.setContent(template.getMemo());
						
						detail.setReceiver(receiver);
						detail.setQuantity(template.getQuantity());

		            } catch (RuntimeException e) {
						log.error("ERROR: {}", e.getMessage(), e);
		            	detail.setError(true);
		            	detail.setErrorMessage(e.getMessage());
		            }
				} else {
					detail.setError(true);
	            	detail.setErrorMessage("정의되지 않은 Template.");
				}
			} 
		}
		
		return list;
	}
	
	@Override
	public String insertExcelData(String templateVersion, MultipartFile multipartFile) {

		if (multipartFile == null) {
			throw new UserException(MessageUtils.getMessage("M01532")); // 파일을 선택해 주세요. 
		}
		
		String fileName = multipartFile.getOriginalFilename();
		String fileExtension = FileUtils.getExtension(fileName);
		
		// 확장자 체크
		if (!(fileExtension.equalsIgnoreCase("xlsx"))) {
			throw new UserException(MessageUtils.getMessage("M01533"));	// 엑셀 파일(.xlsx)만 업로드가 가능합니다. 
		}
		
		// 용량체크 
		String maxUploadFileSize = "20";
		Long maxUploadSize = Long.parseLong(maxUploadFileSize) * 1000 * 1000;
		
		if (multipartFile.getSize() > maxUploadSize) {
			throw new UserException("Maximum upload file Size : " + maxUploadFileSize + "MB");
		}
		
		XSSFWorkbook wb = null;
		
		List<Integer> requireds = new ArrayList<>();
		List<HashMap<String, String>> cellReferences = new ArrayList<>();
		StringBuffer executionLog = new StringBuffer();
		String excelUploadReport = "";
		try {
			
			wb = new XSSFWorkbook(multipartFile.getInputStream());
			XSSFSheet sheet = wb.getSheetAt(0);
			if (sheet == null) {
				return "";
			}
			
			int rowDataCount = 0;			// 데이터 수 (타이틀, 헤더 제외)
			int rowErrorCount = 0;
			int insertCount = 0;
			
			List<Row> insertRows = new ArrayList<>();
			for (Row row : sheet) {
				
				int rowIndex = row.getRowNum() + 1;
		    	
		    	// 해당 로우의 셀 값이 전부 비어있는 경우는 SKIP
		    	if (PoiUtils.isEmptyAllCell(row)) {
		    		continue;
		    	}
		    	
		    	// 필수값 체크해야 되는 항목들 
		    	if (rowIndex == 1) {
		    		for (Cell cell : row) {
		    			if (ShopUtils.getString(cell).endsWith("(*)")) {
		    				requireds.add(cell.getColumnIndex());
		    			}
		    			
		    			CellReference cellReference = new CellReference(cell);
		    			
		    			HashMap<String, String> cellInfo = new HashMap<>();
		    			cellInfo.put("title", ShopUtils.getString(cell).replace("(*)", ""));
		    			cellInfo.put("colString", cellReference.convertNumToColString(cell.getColumnIndex()));
		    			
		    			cellReferences.add(cellInfo);
		    		}
		    		
		    		continue;
		    	}
				
		    	rowDataCount++;
		    	
		    	if ("v01".equals(templateVersion)) {
		    		
		    		ExcelTemplateV01 template = new ExcelTemplateV01(row);
		    		boolean isError = false;
		    		for (Cell cell : row) {
		    			boolean isRequired = false;
		    			for(int index : requireds) {
		    				if (index == cell.getColumnIndex()) {
		    					isRequired = true;
		    				}
		    			}
		    			
		    			if (isRequired) {
		    				if (StringUtils.isEmpty(ShopUtils.getString(cell))) {
		    					
		    					HashMap<String, String> cellReference = cellReferences.get(cell.getColumnIndex());
		    					cellReference.put("rowIndex", Integer.toString(rowIndex));
		    					executionLog.append(PoiUtils.log(cellReference, "필수값 입력 누락"));
		    					rowErrorCount++;
		    					isError = true;
		    				}
		    			}
		    			
		    		}
			    	
		    		if (isError) {
		    			continue;
		    		}
		    		
		    		try {
		    			
		    			Item item = itemService.getItemByItemUserCode(template.getItemUserCode());
						if (item == null) {
							HashMap<String, String> cellReference = cellReferences.get(12);
	    					cellReference.put("rowIndex", Integer.toString(rowIndex));
	    					executionLog.append(PoiUtils.log(cellReference, "상품코드가 존재하지 않거나 판매중인 상품이 아닙니다. (" + template.getItemUserCode() + ")"));

			    			rowErrorCount++;
			    			continue;
						}
		    			
		    		} catch (Exception e) {
						log.error("ERROR: {}", e.getMessage(), e);
		    			HashMap<String, String> cellReference = cellReferences.get(12);
    					cellReference.put("rowIndex", Integer.toString(rowIndex));
    					executionLog.append(PoiUtils.log(cellReference, "상품코드가 존재하지 않거나 판매중인 상품이 아닙니다. (" + template.getItemUserCode() + ")"));

		    			rowErrorCount++;
		    			continue;
		    		}
		    		
		    		if (StringUtils.isEmpty(template.getLoginId()) == false) {
			    		User user = userMapper.getUserByLoginId(template.getLoginId());
			    		if (user == null) {
			    			HashMap<String, String> cellReference = cellReferences.get(2);
	    					cellReference.put("rowIndex", Integer.toString(rowIndex));
	    					executionLog.append(PoiUtils.log(cellReference, "회원정보가 존재하지 않습니다. (" + template.getLoginId() + ")"));
	
			    			rowErrorCount++;
			    			continue;
			    		}
		    		}
		    	} else {
		    		rowErrorCount++;
		    		continue;
		    	}
		    	
		    	insertRows.add(row);
				
				
			}
			
			if (!insertRows.isEmpty()) {
				
				OrderAdmin orderAdmin = new OrderAdmin();
				orderAdmin.setWorkDate(DateUtils.getToday(Const.DATE_FORMAT));
				orderAdmin.setInsertManagerName(UserUtils.getManagerName());
				
				orderAdminMapper.insertOrderAdmin(orderAdmin);
				int itemSequence = 0;
				
				
				for (Row row : insertRows) {
			    	if (row.getRowNum() < 1) {
			    		continue;
			    	}
			    	
			    	// 해당 로우의 셀 값이 전부 비어있는 경우는 SKIP
			    	if (PoiUtils.isEmptyAllCell(row)) {
			    		continue;
			    	}
			    	
			    	try {
				    	OrderAdminDetail detail = new OrderAdminDetail();
				    	detail.setWorkDate(orderAdmin.getWorkDate());
				    	detail.setWorkSequence(orderAdmin.getWorkSequence());
				    	detail.setItemSequence(itemSequence++);
				    	detail.setTemplateVersion(templateVersion);
				    	
				    	if ("v01".equals(templateVersion)) {
				    		
				    		ExcelTemplateV01 template = new ExcelTemplateV01(row);
				    		detail.setExcelData(JsonViewUtils.objectToJson(template));
				    		detail.setOrderGroupCode(template.getOrderGroupCode());
				    	} else {
				    		continue;
				    	}
				    	
				    	orderAdminMapper.insertOrderAdminDetail(detail);
				    	insertCount++;
			    	} catch (Exception e) {
						log.error("ERROR: {}", e.getMessage(), e);
			    		executionLog.append(e.getMessage());
			    		rowErrorCount++;
			    	}
				}
			}
			
			// 처리결과
			excelUploadReport = "\n<p class=\"sheet\"> Total:" + rowDataCount 
					+ ", Process:" + insertCount
					+ ", Error:" + rowErrorCount + "</p>\n";
			
			if (rowErrorCount > 0) {
				excelUploadReport += "<p class=\"line\">----------------------------------------------------------------------------</p>\n";
				excelUploadReport += executionLog.toString();
				excelUploadReport += "\n";
			}
			
		} catch (IOException e) {
			log.error("ERROR: {}", e.getMessage(), e);
			throw new UserException(MessageUtils.getMessage("M01534") + "(" + e.getMessage() + ")"); // 엑셀 파일 로드 시 오류가 발생하였습니다. 
		} catch (Exception e) {
			log.error("ERROR: {}", e.getMessage(), e);
			throw new UserException(MessageUtils.getMessage("M01534") + "(" + e.getMessage() + ")"); // 엑셀 파일 로드 시 오류가 발생하였습니다. 
		}
		
		
		return excelUploadReport;
	}
	
	@Override
	public void insertOrderAdmin(OrderAdminParam orderAdminParam, HttpServletRequest request) {
		
		if (orderAdminParam.getId() == null) {
			return;
		}
		
		List<String> orderGroupCodes = new ArrayList<>();
		List<String> newKeys = new ArrayList<>();
		for(String key : orderAdminParam.getId()) {
			
			String[] temp = StringUtils.delimitedListToStringArray(key, "-");
			if (temp.length != 3) {
				throw new OrderAdminException();
			}
			
			OrderAdminData data = orderAdminParam.getOrderAdminMap().get(key);
			if (data == null) {
				throw new OrderAdminException();
			}
			
			data.setWorkDate(temp[0]);
			data.setWorkSequence(Integer.parseInt(temp[1]));
			data.setItemSequence(Integer.parseInt(temp[2]));
			
			// 작업 완료 되었다면 무시..
			if (orderAdminMapper.getOrderAdminDetailCheckedStatus(data) == 0) {
				continue;
			}
			
			boolean isNew = true;
			for(String code : orderGroupCodes) {
				if (data.getOrderGroupCode().equals(code)) {
					isNew = false;
					break;
				}
			}
			
			if (isNew) {
				orderGroupCodes.add(data.getOrderGroupCode());
			}
			
			newKeys.add(key);
		}
		
		if (orderGroupCodes.isEmpty() || newKeys.isEmpty()) {
			throw new OrderAdminException("주문서 생성에 실패 하였습니다.");
		}
		
		//같은주문 등록가능한지 검증
		adminOrderValidator(orderGroupCodes, orderAdminParam, newKeys);
		
		
		// 같은 주문으로 등록하는 것들을 주문서 기준으로 묶음
		List<BuyAdmin> orders = new ArrayList<>();
		for(String code : orderGroupCodes) {
			for(String key : newKeys) {
				
				OrderAdminData data = orderAdminParam.getOrderAdminMap().get(key);
				
				if (!code.equals(data.getOrderGroupCode())) {
					continue;
				}
				
				boolean isNew = true;
				for(BuyAdmin order : orders) {
					if (order.getOrderGroupCode().equals(data.getOrderGroupCode())) {
						isNew = false;
					}
				}
				
				if (isNew) {
					
					BuyAdmin order = new BuyAdmin(data);
					order.setBuyer(new BuyAdminBuyer(data));
					List<OrderAdminData> datas = new ArrayList<>();
					datas.add(data);
					order.setDatas(datas);
					orders.add(order);
					
				} else {
					
					for(BuyAdmin order : orders) {
						if (order.getOrderGroupCode().equals(data.getOrderGroupCode())) {
							
							List<OrderAdminData> datas = order.getDatas();
							datas.add(data);
							
							break;
						}
					}
					
				}
			}
		}
		
		if (orders.size() != orderGroupCodes.size()) {
			throw new OrderAdminException("주문서 생성도중 에러가 발생하였습니다.");
		}
		
		
		for(BuyAdmin buy : orders) {
			
			if (buy.getDatas() == null) {
				throw new OrderAdminException("주문서 생성도중 에러가 발생하였습니다.");
			}
			
			for(OrderAdminData data : buy.getDatas()) {
				
				boolean isNew = true;
				if (buy.getReceivers() != null) {
					for(BuyAdminReceiver receiver : buy.getReceivers()) {
	
						StringBuffer checkBuffer = new StringBuffer();
						checkBuffer.append(receiver.getReceiveZipcode().trim());
						checkBuffer.append(receiver.getReceiveAddress().trim());
						checkBuffer.append(receiver.getReceiveAddressDetail().trim());
							
						StringBuffer sb = new StringBuffer();
						sb.append(data.getReceiveZipcode().trim());
						sb.append(data.getReceiveAddress().trim());
						sb.append(data.getReceiveAddressDetail().trim());
						
						if (sb.toString().equals(checkBuffer.toString())) {
							isNew = false;
							
							List<BuyAdminItem> items = receiver.getItems();
							
							BuyAdminItem item = new BuyAdminItem(data);
							item.setItemPrice(new BuyAdminItemPrice(item));
							
							items.add(item);
							
							break;
						}
					}
				}
				
				if (isNew) {
					
					List<BuyAdminReceiver> receivers = buy.getReceivers();
					if (receivers == null) {
						receivers = new ArrayList<>();
					}
					
					List<BuyAdminItem> items = new ArrayList<>();
					BuyAdminReceiver receiver = new BuyAdminReceiver(data);
					
					BuyAdminItem item = new BuyAdminItem(data);
					item.setItemPrice(new BuyAdminItemPrice(item));
					
					items.add(item);
					receiver.setItems(items);
					
					receivers.add(receiver);
					buy.setReceivers(receivers);
					
				}
			}
		
		}
		
		Config shopConfig = ShopUtils.getConfig();
		for(BuyAdmin buy : orders) {

			buy.setOrderCode(getNewOrderCode("A"));
			buy.setIp(saleson.common.utils.CommonUtils.getClientIp(request));
						
			int shippingInfoSequence = 0;
			for(BuyAdminReceiver receiver : buy.getReceivers()) {
				
				receiver.setOrderCode(buy.getOrderCode());
				receiver.setOrderSequence(buy.getOrderSequence());
				receiver.setShippingInfoSequence(shippingInfoSequence++);
				
				// 배송지 정보 등록
				orderAdminMapper.insertOrderShippingInfo(receiver);
				
				receiver.setShipping(orderMapper.getIslandTypeByZipcode(receiver.getReceiveZipcode()), orderAdminParam.getShippingPaymentType());

				int shippingSequence = 0;
				int itemSequence = 0;
				for(BuyAdminShipping shipping : receiver.getItemGroups()) {
					
					shipping.setOrderCode(buy.getOrderCode());
					shipping.setOrderSequence(buy.getOrderSequence());
					shipping.setShippingSequence(shippingSequence++);
					
					// 배송비 정보 등록
					orderAdminMapper.insertOrderShipping(shipping);
					
					if (shipping.isSingleShipping()) {

						BuyAdminItem item = shipping.getBuyItem();
						
						bindOrderItem(buy, item, shopConfig);
						
						item.setItemSequence(itemSequence++);
						item.setShippingSequence(shipping.getShippingSequence());
						item.setShippingInfoSequence(receiver.getShippingInfoSequence());
						orderAdminMapper.insertOrderItem(item);
						
					} else {
						for(BuyAdminItem item : shipping.getBuyItems()) {

							bindOrderItem(buy, item, shopConfig);
							
							item.setItemSequence(itemSequence++);
							item.setShippingSequence(shipping.getShippingSequence());
							item.setShippingInfoSequence(receiver.getShippingInfoSequence());
							
							orderAdminMapper.insertOrderItem(item);
						}
					}
				}
				
			}
			
			buy.setOrderPrice(new BuyAdminOrderPrice(buy.getReceivers()));
			
			// 주문 마스터 등록
			orderAdminMapper.insertOrder(buy);
			orderAdminMapper.insertOrderPayment(new BuyAdminPayment(buy));
		}
		
		// 주문 처리 완료
		for(String key : newKeys) {
			OrderAdminData data = orderAdminParam.getOrderAdminMap().get(key);
			data.setUpdateManagerName(UserUtils.getManagerName());
			orderAdminMapper.updateOrderAdminDetailStatus(data);
		}
	}
	
	/**
	 * 상품정보 셋팅
	 * @param buy
	 * @param item
	 * @param shopConfig
	 */
	private void bindOrderItem(BuyAdmin buy, BuyAdminItem item, Config shopConfig) {
		item.setOrderCode(buy.getOrderCode());
		item.setOrderSequence(buy.getOrderSequence());
		
		// 매출 기준일을 등록
		item.setRevenueSalesStatus(shopConfig.getRevenueSalesStatus());
		if ("0".equals(shopConfig.getRevenueSalesStatus())) { // 주문등록시 매출?
			item.setSalesDate(DateUtils.getToday(Const.DATETIME_FORMAT));
		}
	}
	
	/**
	 * 같은주문 등록가능한지 검증
	 * @param codes
	 * @param param
	 */
	private void adminOrderValidator(List<String> codes, OrderAdminParam param, List<String> newKeys) {
		for(String code : codes) {
			
			boolean isSuccess = true;
			StringBuffer checkBuffer = null;
			for(String key : newKeys) {
				
				OrderAdminData data = param.getOrderAdminMap().get(key);
				if (data == null) {
					throw new OrderAdminException();
				}
				
				if (!code.equals(data.getOrderGroupCode())) {
					continue;
				}
				
				Item item = itemService.getItemById(data.getItemId());
				if (item == null) {
					throw new OrderAdminException("상품 정보를 찾을수 없습니다.");
				}

				// 판매 카테고리 정보를 기록
				data.setProductsRepCategories(categoriesMapper.getProductsRepCategoriesByItemId(item.getItemId()));

				// 입점 업체 수수료 일때 판매자 정보에 설정된 수수료를 조회해서 셋팅
				if ("1".equals(item.getCommissionType())) {
					Seller seller = sellerMapper.getSellerById(item.getSellerId());
					if (seller != null) {
						item.setCommissionRate(seller.getCommissionRate());
					}
				}

				// 옵션 사용 여부 검증
				if ("Y".equals(item.getItemOptionFlag())) {

					if (data.getOptionIds() == null) {
						throw new OrderAdminException("옵션을 선택해 주세요.");
					}
					
					String options = "";
					for(String optionId : data.getOptionIds()) {
						
						if (StringUtils.isEmpty(optionId)) {
							throw new OrderAdminException("옵션을 선택해 주세요.");
						}
						
						for(ItemOption itemOption : item.getItemOptions()) {
							if (itemOption.getItemOptionId() == Integer.parseInt(optionId)) {
								
								if (StringUtils.isNotEmpty(options)) {
									options += "^^^";
								}
								
								options += ShopUtils.makeOptionText(item, itemOption, "");
								break;
							}
							
						}
					}
					
					List<ItemOption> itemOptions = ShopUtils.getRequiredItemOptions(item, options);
					if (itemOptions != null && itemOptions.size() != data.getOptionIds().length) {
						throw new OrderAdminException("옵션의 구성이 변경 되었습니다.");
					}
				}
				
				data.setItem(item);
				
				if (data.getUserId() == 0) {
					
					// 동일주문 체크할 데이터가 없으면 새로 생성하고 다음껄로 이동
					if (checkBuffer == null) {
						checkBuffer = new StringBuffer();
						checkBuffer.append(data.getUserName().trim());
						checkBuffer.append(data.getMobile().trim());
						
						continue;
					} else {
						
						StringBuffer sb = new StringBuffer();
						sb.append(data.getUserName().trim());
						sb.append(data.getMobile().trim());
						
						if (!sb.toString().equals(checkBuffer.toString())) {
							isSuccess = false;
						}
						
					}
					
				} else {
					
				
					User user = userMapper.getUserByUserId(data.getUserId());
					if (user == null) {
						throw new OrderAdminException("회원정보를 찾을수 없습니다.");
					}

					// 포인트 정책 설정
					data.setPointPolicy(pointService.getPointPolicyByItemId(item.getItemId()));
					
					// 회원 등급 정보 설정
					UserDetail userDetail = (UserDetail) user.getUserDetail();
					if (userDetail.getLevelId() > 0) {
						data.setUserLevel(userLevelMapper.getUserLevelById(userDetail.getLevelId()));
					}
					
					data.setUser(user);
					
					// 동일주문 체크할 데이터가 없으면 새로 생성하고 다음로 이동
					if (checkBuffer == null) {
						checkBuffer = new StringBuffer();
						checkBuffer.append(data.getUserId());
						
						continue;
					} else {
						
						StringBuffer sb = new StringBuffer();
						sb.append(data.getUserId());
						
						if (!sb.toString().equals(checkBuffer.toString())) {
							isSuccess = false;
							break;
						}
						
					}
				}
				
				if (!isSuccess) {
					throw new OrderAdminException("같은 주문으로 등록하실수 없는 타입의 주문이 있습니다. \n주문 그룹번호를 확인해 주세요.");
				}
				
			}
		}
	}
	
	/**
	 * 주문번호 생성
	 * @param prefix
	 * @return
	 */
	private String getNewOrderCode(String prefix) {
		return prefix + sequenceService.getId("OP_ORDER_ADMIN_CODE");
	}
}
