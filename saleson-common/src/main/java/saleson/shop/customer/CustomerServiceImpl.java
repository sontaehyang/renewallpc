package saleson.shop.customer;

import com.onlinepowers.framework.exception.UserException;
import com.onlinepowers.framework.security.userdetails.User;
import com.onlinepowers.framework.sequence.service.SequenceService;
import com.onlinepowers.framework.util.FileUtils;
import com.onlinepowers.framework.util.MessageUtils;
import com.onlinepowers.framework.util.PoiUtils;
import com.onlinepowers.framework.util.StringUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import saleson.common.utils.ShopUtils;
import saleson.shop.businesscode.BusinessCodeService;
import saleson.shop.businesscode.domain.BusinessCode;
import saleson.shop.customer.domain.Customer;
import saleson.shop.customer.support.CustomerParam;
import saleson.shop.order.domain.OrderShippingInfo;
import saleson.shop.user.UserService;
import saleson.shop.user.domain.UserDetail;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

@Service("customerService")
public class CustomerServiceImpl implements CustomerService {
	private static final Logger log = LoggerFactory.getLogger(CustomerServiceImpl.class);

	@Autowired
	private CustomerMapper customerMapper;
	
	@Autowired
	private SequenceService sequenceService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private BusinessCodeService businessCodeService;
	
	@Override
	public List<Customer> getCustomerListByParamForCall(CustomerParam customerParam) {
		List<Customer> list = customerMapper.getCustomerListByParamForCall(customerParam);
		if (list != null) {
			List<BusinessCode> businessCodes = businessCodeService.getBusinessCodeList(new BusinessCode());
			for(Customer c : list) {
				String businessTypeTexts = "";
				if (StringUtils.isNotEmpty(c.getBusinessType())) {
					
					String[] businessTypes = StringUtils.tokenizeToStringArray(c.getBusinessType(), ",");
					
					int j = 0;
					for (String businessType : businessTypes) {
						for (BusinessCode businessCode : businessCodes) {
							if (businessType.equals(Integer.toString(businessCode.getBusinessCodeId()))) {
								businessTypeTexts += j == 0 ? "" : ", ";
								businessTypeTexts += businessCode.getId();
								j++;
							}
						}
					}
					
					if (StringUtils.isNotEmpty(businessTypeTexts)) {
						c.setCustomerType(businessTypeTexts);
					}
				}
			}
		}
		
		return list;
	}
	
	@Override
	public String insertExcelData(MultipartFile multipartFile) {
		
		if (multipartFile == null) {
			throw new UserException(MessageUtils.getMessage("M01532")); // ????????? ????????? ?????????. 
		}
		
		String fileName = multipartFile.getOriginalFilename();
		String fileExtension = FileUtils.getExtension(fileName);
		
		// ????????? ??????
		if (!(fileExtension.equalsIgnoreCase("xlsx"))) {
			throw new UserException(MessageUtils.getMessage("M01533"));	// ?????? ??????(.xlsx)??? ???????????? ???????????????. 
		}
		
		// ???????????? 
		String maxUploadFileSize = "20";
		Long maxUploadSize = Long.parseLong(maxUploadFileSize) * 1000 * 1000;
		
		if (multipartFile.getSize() > maxUploadSize) {
			throw new UserException("Maximum upload file Size : " + maxUploadFileSize + "MB");
		}
		
		XSSFWorkbook wb = null;
		
		String excelUploadReport = "";
		try {
			wb = new XSSFWorkbook(multipartFile.getInputStream());
			
			excelUploadReport = this.processExcelSheet(wb.getSheetAt(0));

		} catch (IOException e) {
			log.error("insertExcelData : {}", e.getMessage(), e);
			throw new UserException(MessageUtils.getMessage("M01534") + "(" + e.getMessage() + ")"); // ?????? ?????? ?????? ??? ????????? ?????????????????????.
		} catch (Exception e) {
			log.error("insertExcelData : {}", e.getMessage(), e);
			throw new UserException(MessageUtils.getMessage("M01534") + "(" + e.getMessage() + ")"); // ?????? ?????? ?????? ??? ????????? ?????????????????????. 
		}
		
		return excelUploadReport;
	}
	
	private String processExcelSheet(XSSFSheet sheet) {
		String result = "";
		if (sheet == null) {
			return result;
		}
		
		StringBuffer executionLog = new StringBuffer();
		
		// ????????? ?????? INSER VALUE (), () ????????? ?????? ??????.
		// ??????, ?????? ??? ?????? Batch??? ?????? 
		List<Customer> insertCustomer = new ArrayList<>();
		List<Customer> updateCustomer = new ArrayList<>();
		
		int rowDataCount = 0;			// ????????? ??? (?????????, ?????? ??????)
		for (Row row : sheet) {
			
	    	if (row.getRowNum() < 4) {
	    		continue;
	    	}
	    	
	    	// ?????? ????????? ??? ?????? ?????? ???????????? ????????? SKIP
	    	if (PoiUtils.isEmptyAllCell(row)) {
	    		continue;
	    	}
	    	
	    	String customerCode = ShopUtils.getString(row.getCell(1));
	    	Customer customer = customerMapper.getCustomerById(customerCode);
	    	Customer excelData = this.rowToCustomer(row);
	    	
	    	if (customer == null) {
	    		insertCustomer.add(excelData);
	    	} else {
	    		if (isUpdate(excelData, customer) == true) {
	    			updateCustomer.add(excelData);
	    		}
	    	}
	    	
	    	rowDataCount++;
		}
		
		if (rowDataCount > 0) {
			
			// ?????? ?????? ?????????
			if (!insertCustomer.isEmpty()) {
				
				int insertErrorCount = 0;
				
				
				List<Customer> temp = new ArrayList<>();
				
				int index = 1;
				int count = 1;
				for(Customer data : insertCustomer) {
					
					try {

						CustomerParam param = new CustomerParam();
						param.setEmail(data.getCustomerStaffEmail());
						param.setTelNumber(data.getTelNumber());
						param.setUserName(data.getCustomerName());
						
						if (!StringUtils.isEmpty(param.getEmail()) || !StringUtils.isEmpty(param.getTelNumber())) {
							User user = customerMapper.getUserByParam(param);
							if (user != null) {
								data.setUserId(user.getUserId());
								data.setCustomerStaffEmail(user.getEmail());
							}
						}
					} catch (Exception e) {
						log.error("ERROR: {}", e.getMessage(), e);
						insertErrorCount++;
					}
					
					temp.add(data);
					if (index >= 100) {
						customerMapper.insertCustomerForExcel(temp);
						temp = new ArrayList<>();
						
						index = 0;
					} else if (count >= insertCustomer.size()) {
						
						if (!temp.isEmpty()) {
							customerMapper.insertCustomerForExcel(temp);
						}
						
					}
					
					index++;
					count++;
				}
				
				if (insertErrorCount > 0) {
					executionLog.append(insertErrorCount + "??? ???????????? ?????? ??????!!\n");
				}
				
				
			}
			
			// ?????? ?????????
			if (!updateCustomer.isEmpty()) {
				
				int updateErrorCount = 0;
				for(Customer data : updateCustomer) {
					try {
						customerMapper.updateCustomer(data);
					} catch (Exception e) {
						log.error("ERROR: {}", e.getMessage(), e);
						updateErrorCount++;
					}
				}
				
				if (updateErrorCount > 0) {
					executionLog.append(updateErrorCount + "??? ?????? ?????? ??????!!\n");
				}
				
			}
		}
		
		return executionLog.toString();
	}
	
	/**
	 * ?????? Row??? Customer Domain?????? ??????
	 * @param row
	 * @return
	 */
	private Customer rowToCustomer(Row row) {
		
		Customer customer = new Customer();
		customer.setCustomerCode(ShopUtils.getString(row.getCell(1)));
		customer.setCustomerName(ShopUtils.getString(row.getCell(2)));
		customer.setCustomerGroup(ShopUtils.getString(row.getCell(3)));
		customer.setCustomerType(ShopUtils.getString(row.getCell(4)));
		customer.setBossName(ShopUtils.getString(row.getCell(5)));
		customer.setBusinessNumber(ShopUtils.getString(row.getCell(6)));
		customer.setTelNumber(ShopUtils.getString(row.getCell(7)));
		
		customer.setFaxGroup(ShopUtils.getString(row.getCell(8)));
		customer.setFaxNumber(ShopUtils.getString(row.getCell(9)));
		customer.setHomepage(ShopUtils.getString(row.getCell(10)));
		
		
		customer.setCategory(ShopUtils.getString(row.getCell(11))); // ??????
		customer.setEvent(ShopUtils.getString(row.getCell(12))); // ??????

		
		
		customer.setZipcode(ShopUtils.getString(row.getCell(13)));
		customer.setAddress(ShopUtils.getString(row.getCell(14)));
		customer.setAddressDetail(ShopUtils.getString(row.getCell(15)));
		customer.setMemo(ShopUtils.getString(row.getCell(16)));
		
		customer.setStaffName(ShopUtils.getString(row.getCell(17)));
		customer.setStaffDepartment(ShopUtils.getString(row.getCell(18)));
		customer.setStaffTelNumber(ShopUtils.getString(row.getCell(19)));
		customer.setStaffPhoneNumber(ShopUtils.getString(row.getCell(20)));
		
		customer.setBankName(ShopUtils.getString(row.getCell(21)));
		customer.setBankNumber(ShopUtils.getString(row.getCell(22)));
		customer.setBankInName(ShopUtils.getString(row.getCell(23)));
		customer.setBankCmsCode(ShopUtils.getString(row.getCell(24)));
		
		customer.setCustomerStaffName(ShopUtils.getString(row.getCell(25)));
		customer.setCustomerStaffPosition(ShopUtils.getString(row.getCell(26)));
		customer.setCustomerStaffTelNumber(ShopUtils.getString(row.getCell(27)));
		customer.setCustomerStaffPhoneNumber(ShopUtils.getString(row.getCell(28)));
		customer.setCustomerStaffEmail(ShopUtils.getString(row.getCell(29)));

		customer.setDmZipcode(ShopUtils.getString(row.getCell(30)));
		customer.setDmAddress(ShopUtils.getString(row.getCell(31)));
		customer.setDmAddressDetail(ShopUtils.getString(row.getCell(32)));
		customer.setBusinessNumberCode(ShopUtils.getString(row.getCell(33)));
		
		return customer;
	}
	
	/**
	 * ???????????? ??????????????? ????????? ??????
	 * @param excelData
	 * @param customer
	 * @return
	 */
	private boolean isUpdate(Customer excelData, Customer customer) {

		Class<?> clazz = customer.getClass();
		Class<?> clazz1 = excelData.getClass(); 
		for(Method method : clazz.getMethods()) {
			
			try {
				String methodName = method.getName();
				if (methodName.startsWith("get")) {
					Method invokeMethod = clazz1.getMethod(method.getName());
					String methodValue = (String) invokeMethod.invoke(excelData);
					String methodValue1 = (String) invokeMethod.invoke(customer);
					if (!methodValue.equals(methodValue1)) {
						return true;
					}
					
				}
			} catch (Exception e) {
				log.warn("CustomerServiceImpl isUpdate() : {}", e.getMessage(), e);
			}
			
		}
		
		return false;
	}
	
	private String newCustomerCode(int id) {
		
		String s = ""+id;
		if (s.length() > 10) {
			return s;
		}
		
		int loopCount = 10 - s.length();
		for(int i = 0; i < loopCount; i++) {
			s = "0"+s;
		}
		
		return s;
	}
	
	@Override
	public void insertCustomer(Customer customer) {

		customerMapper.insertCustomer(customer);
		
	}

	/**
	 * 2015.1.23 ????????? ??????
	 */
	@Override
	public void updateCustomer(Customer customer) {
		customerMapper.updateCustomer(customer);
	}
	
	@Override
	public void newCustomerForOrder(OrderShippingInfo order, String customerType) {
		/*
		if (order == null) {
			return;
		}
			
		Customer customer = new Customer();
		customer.setCustomerName(order.getReceiveName());
		customer.setTelNumber(order.getFullReceiveMobile());
		customer.setCustomerGroup(customerType);
		customer.setZipcode(order.getFullReceiveZipcode());
		customer.setAddress(order.getReceiveAddress());
		customer.setAddressDetail(order.getReceiveAddressDetail());
		customer.setCustomerStaffEmail(order.getEmail());
		customer.setUserId(order.getUserId());
		
		if (order.getUserId() > 0) {
			User user = userService.getUserByUserId(order.getUserId());
			
			if (user == null) {
				customer.setUserId(0);
			} else {
				UserDetail userDetail = (UserDetail) user.getUserDetail();
				
				String businessNumber =  userDetail.getBusinessNumber();
				
				if (StringUtils.isEmpty(businessNumber)) {
					businessNumber = "000-00-00000";
				}
				
				customer.setCustomerStaffEmail(user.getEmail());
				customer.setBusinessNumber(businessNumber);
			}
		} else {
			customer.setBusinessNumber("000-00-00000");
		}
		
		String customerCode = customerMapper.getCustomerId(customer);
		
		// ????????? DB????????? ????????? ????????? ???????????? ?????? ??????
		if (StringUtils.isEmpty(customerCode)) {
			customerCode = this.newCustomerCode(sequenceService.getId("OP_CUSTOMER_CODE"));
			
			customer.setCustomerCode(customerCode);
			customerMapper.insertCustomer(customer);
		}
		
		order.setCustomerCode(customerCode);
		*/
	}

	@Override
	public void newCustomerForUser(User user) {
		
		UserDetail userDetail = (UserDetail) user.getUserDetail();

		Customer customer = new Customer();
		customer.setUserId(user.getUserId());
		customer.setCustomerName(user.getUserName());
		customer.setTelNumber(userDetail.getTelNumber());
		customer.setCustomerType("???????????????");
		customer.setZipcode(userDetail.getPost());
		customer.setAddress(userDetail.getAddress());
		customer.setAddressDetail(userDetail.getAddressDetail());
		customer.setCustomerStaffEmail(user.getEmail());
		customer.setCustomerCode(this.newCustomerCode(sequenceService.getId("OP_CUSTOMER_CODE")));
		customerMapper.insertCustomer(customer);
		
	}

	@Override
	public int getCustomerCountByParam(CustomerParam customerParam) {
		return customerMapper.getCustomerCountByParam(customerParam);
	}

	@Override
	public List<Customer> getCustomerList(CustomerParam customerParam) {
		
		List<Customer> list = customerMapper.getCustomerList(customerParam);
		
		if (list != null) {
			
			List<BusinessCode> businessCodes = businessCodeService.getBusinessCodeList(new BusinessCode());
			for(Customer c : list) {
				String businessTypeTexts = "";
				if (StringUtils.isNotEmpty(c.getBusinessType())) {
					
					String[] businessTypes = StringUtils.tokenizeToStringArray(c.getBusinessType(), ",");
					
					int j = 0;
					for (String businessType : businessTypes) {
						for (BusinessCode businessCode : businessCodes) {
							if (businessType.equals(Integer.toString(businessCode.getBusinessCodeId()))) {
								businessTypeTexts += j == 0 ? "" : ", ";
								businessTypeTexts += businessCode.getId();
								j++;
							}
						}
					}
					
					if (StringUtils.isNotEmpty(businessTypeTexts)) {
						c.setCustomerType(businessTypeTexts);
					}
				}
			}
		}
		
		return list;
	}

	@Override
	public Customer getCustomerById(String customerCode) {
		return customerMapper.getCustomerById(customerCode);
	}

	@Override
	public void updateCustomerDefaultInfo(Customer customer) {
		if (StringUtils.isNotEmpty(customer.getCustomerName())) {
			customerMapper.updateCustomerDefaultInfo(customer);
		}
	}
	
}
