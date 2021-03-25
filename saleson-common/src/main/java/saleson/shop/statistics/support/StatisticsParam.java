package saleson.shop.statistics.support;



import java.util.List;

import com.onlinepowers.framework.repository.CodeInfo;
import com.onlinepowers.framework.util.CodeUtils;
import com.onlinepowers.framework.util.DateUtils;
import com.onlinepowers.framework.util.StringUtils;
import com.onlinepowers.framework.web.domain.SearchParam;
import org.springframework.security.core.SpringSecurityCoreVersion;
import saleson.common.Const;
import saleson.common.utils.CommonUtils;

public class StatisticsParam extends SearchParam {
	
	private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;
	
	// [skc] 목록에서 항목별 소계 표시 여부 
	private String displaySubtotal = "N";
	
	
	public String getDisplaySubtotal() {
		return displaySubtotal;
	}

	public void setDisplaySubtotal(String displaySubtotal) {
		this.displaySubtotal = displaySubtotal;
	}

	private long sellerId;
	public long getSellerId() {
		return sellerId;
	}

	public void setSellerId(long sellerId) {
		this.sellerId = sellerId;
	}

	private String startDate;
	private String endDate;
	private String itemName;
	
	private String type;
	
	private String weekType;
	
	private String itemId;
	
	private String code;
	private String categoryGroupId;
	private String categoryId;
	private String categoryClass;
	private String categoryClass1;
	private String categoryClass2;
	private String categoryClass3;
	private String categoryClass4;
	
	private String dodobuhyun;
	
	private String searchDate;
	
	private String orderId;
	
	private long userId;
	private String customerCode;
	private String userName;
	
	private String startYear;
	private String startMonth;
	private String endYear;
	private String endMonth;
	
	private String sd;
	private String ed;

	private String guestFlag;
	
	private String salesCountFlag;
	private String salesCount;
	
	private String salesPriceFlag;
	private String salesPrice;
	
	private String brand;
	
	private int sidoMappingGroupKey;
	private String searchYear;
	
	private String[] approvalTypes;
	
	public String[] getApprovalTypes() {
		
		if (approvalTypes == null) {
			List<CodeInfo> list = CodeUtils.getCodeInfoList("ORDER_PAY_TYPE");
			String[] temp = new String[list.size()];
			
			int i = 0;
			for(CodeInfo info : list) {
				temp[i++] = info.getKey().getId();
			}
			
			return temp;
		}

		return CommonUtils.copy(approvalTypes);
	}

	public void setApprovalTypes(String[] approvalTypes) {
		this.approvalTypes = CommonUtils.copy(approvalTypes);
	}


	public String getSearchYear() {
		return searchYear;
	}

	public void setSearchYear(String searchYear) {
		this.searchYear = searchYear;
	}

	public int getSidoMappingGroupKey() {
		return sidoMappingGroupKey;
	}

	public void setSidoMappingGroupKey(int sidoMappingGroupKey) {
		this.sidoMappingGroupKey = sidoMappingGroupKey;
	}

	public String getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getGuestFlag() {
		return guestFlag;
	}

	public void setGuestFlag(String guestFlag) {
		this.guestFlag = guestFlag;
	}

	public String getSd() {
		String d = sd;
		
		if (StringUtils.isEmpty(d)) {
			return DateUtils.getToday("yyyyMM") + "01";
		}
		
		if (d.length() == 4) {
			return d + "0101";
		}
		
		if (d.length() == 6) {
			return d + "01";
		}
		
		if (d.length() != 8) {
			d = DateUtils.getToday("yyyyMM") + "01";
		}
		
		return d;
	}

	public void setSd(String sd) {
		
		this.sd = sd;
	}

	public String getEd() {
		String d = ed;
		
		if (StringUtils.isEmpty(d)) {
			return DateUtils.getToday(Const.DATE_FORMAT);
		}
		
		if (d.length() == 4) {
			return d + "1231";
		}
		
		if (d.length() == 6) {
			return d + "31";
		}
		
		if (d.length() != 8) {
			d = DateUtils.getToday(Const.DATE_FORMAT);
		}
		
		return d;
	}

	public void setEd(String ed) {
		this.ed = ed;
	}

	/**
	 * 카테고리 그룹 쿼리 처리용
	 * @return
	 */
	public String getCategorySearchMode() {
		
		if (StringUtils.isNotEmpty(this.getCategoryClass())) {
			return "category";
		} 
		
		if (StringUtils.isNotEmpty(this.getCategoryGroupId())) {
			return "group";
		}
		
		if (StringUtils.isNotEmpty(this.getCode())) {
			return "team";
		}
		
		return "all";
	}
	
	public String getStartYear() {
		
		if (StringUtils.isEmpty(startYear)) {
			startYear = DateUtils.getToday("yyyy");
		} else {
			if (startYear.length() != 4) {
				startYear = DateUtils.getToday("yyyy");
			}
		}
		
		return startYear;
	}
	public void setStartYear(String startYear) {
		this.startYear = startYear;
	}
	public String getStartMonth() {
		
		if (StringUtils.isEmpty(startMonth)) {
			startMonth = DateUtils.getToday("MM");
		} else {
			if (startMonth.length() != 2) {
				startMonth = DateUtils.getToday("MM");
			}
		}
		
		return startMonth;
	}
	public void setStartMonth(String startMonth) {
		this.startMonth = startMonth;
	}
	public String getEndYear() {
		
		if (StringUtils.isEmpty(endYear)) {
			endYear = DateUtils.getToday("yyyy");
		} else {
			if (endYear.length() != 4) {
				endYear = DateUtils.getToday("yyyy");
			}
		}
		
		return endYear;
	}
	public void setEndYear(String endYear) {
		this.endYear = endYear;
	}
	public String getEndMonth() {
		
		if (StringUtils.isEmpty(endMonth)) {
			endMonth = DateUtils.getToday("MM");
		} else {
			if (endMonth.length() != 2) {
				endMonth = DateUtils.getToday("MM");
			}
		}
		
		return endMonth;
	}
	public void setEndMonth(String endMonth) {
		this.endMonth = endMonth;
	}
	public String getStartDate() {
		
		if (StringUtils.isNotEmpty(sd)) {
			return this.getSd();
		}
		
		if ("month".equals(type)) {
			return this.getStartYear() + this.getStartMonth() + "01";
		} else if ("year".equals(type)) {
			return this.getStartYear() + "0101";
		} else {
			if (StringUtils.isEmpty(startDate)) {
				return DateUtils.getToday("yyyyMM") + "01";
			}
		}
		
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		
		if (StringUtils.isNotEmpty(ed)) {
			return this.getEd();
		}
		
		if ("month".equals(type)) {
			return this.getEndYear() + this.getEndMonth() + "31";
		} else if ("year".equals(type)) {
			return this.getEndYear() + "1231";
		} else {
			if (StringUtils.isEmpty(endDate)) {
				return DateUtils.getToday(Const.DATE_FORMAT);
			}
		}
		
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public String getCategoryGroupId() {
		return categoryGroupId;
	}
	public void setCategoryGroupId(String categoryGroupId) {
		this.categoryGroupId = categoryGroupId;
	}
	public String getCategoryClass1() {
		return categoryClass1;
	}
	public void setCategoryClass1(String categoryClass1) {
		this.categoryClass1 = categoryClass1;
	}
	public String getCategoryClass2() {
		return categoryClass2;
	}
	public void setCategoryClass2(String categoryClass2) {
		this.categoryClass2 = categoryClass2;
	}
	public String getCategoryClass3() {
		return categoryClass3;
	}
	public void setCategoryClass3(String categoryClass3) {
		this.categoryClass3 = categoryClass3;
	}
	public String getCategoryClass4() {
		return categoryClass4;
	}
	public void setCategoryClass4(String categoryClass4) {
		this.categoryClass4 = categoryClass4;
	}
	public String getCategoryClass() {
		
		if (StringUtils.isNotEmpty(this.categoryClass)) {
			return this.categoryClass;
		}
		
		if (StringUtils.isNotEmpty(this.categoryClass4)) {
			return this.categoryClass4;
		} else if (StringUtils.isNotEmpty(this.categoryClass3)) {
			return this.categoryClass3;
		} else if (StringUtils.isNotEmpty(this.categoryClass2)) {
			return this.categoryClass2;
		} else if (StringUtils.isNotEmpty(this.categoryClass1)) {
			return this.categoryClass1;
		}
		
		return "";
		
	}
	public void setCategoryClass(String categoryClass) {
		this.categoryClass = categoryClass;
	}
	public String getItemId() {
		return itemId;
	}
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
	public String getType() {
		
		if (StringUtils.isEmpty(type)) {
			return "1";
		}
		
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getSearchDate() {
		return searchDate;
	}
	public void setSearchDate(String searchDate) {
		this.searchDate = searchDate;
	}
	public String getDodobuhyun() {

		return dodobuhyun;
	}
	public void setDodobuhyun(String dodobuhyun) {
		this.dodobuhyun = dodobuhyun;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String username) {
		this.userName = username;
	}
	public String getWeekType() {
		return weekType;
	}
	public void setWeekType(String weekType) {
		this.weekType = weekType;
	}

	public String getSalesCountFlag() {
		return salesCountFlag;
	}

	public void setSalesCountFlag(String salesCountFlag) {
		this.salesCountFlag = salesCountFlag;
	}

	public String getSalesCount() {
		return salesCount;
	}

	public void setSalesCount(String salesCount) {
		this.salesCount = salesCount;
	}

	public String getSalesPriceFlag() {
		return salesPriceFlag;
	}

	public void setSalesPriceFlag(String salesPriceFlag) {
		this.salesPriceFlag = salesPriceFlag;
	}

	public String getSalesPrice() {
		return salesPrice;
	}

	public void setSalesPrice(String salesPrice) {
		this.salesPrice = salesPrice;
	}

}
