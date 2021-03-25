package saleson.shop.customer;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import saleson.shop.customer.domain.Customer;
import saleson.shop.customer.support.CustomerParam;
import saleson.shop.order.domain.OrderShippingInfo;

import com.onlinepowers.framework.security.userdetails.User;


public interface CustomerService {

	/**
	 * 전화주문 회원 검색
	 * @param customerParam
	 * @return
	 */
	public List<Customer> getCustomerListByParamForCall(CustomerParam customerParam);
	
	/**
	 * Excel 업로드
	 * @param multipartFile
	 * @return
	 */
	public String insertExcelData(MultipartFile multipartFile);
	
	/**
	 * 거래처 신규 등록
	 * @param customer
	 */
	public void insertCustomer(Customer customer);
	
	
	/**
	 * 거래처 수정
	 * @param customer
	 */
	public void updateCustomer(Customer customer);
	
	/**
	 * 오픈마켓 주문 생성시 거래처 등록
	 * @param order
	 * @param customerType - 오픈 마켓 이름을 String 으로 넘김(Ex. 11번가), 일반 사이트 주문일경우에는 "매출거래처"
	 */
	public void newCustomerForOrder(OrderShippingInfo order, String customerType);

	/**
	 * 신규 회원 등록시 거래처 등록
	 * @param user
	 */
	public void newCustomerForUser(User user);

	public int getCustomerCountByParam(CustomerParam customerParam);
	
	/**
	 * 2015.1.23 거래처 목록
	 * @param customerParam
	 * @return
	 */
	public List<Customer> getCustomerList(CustomerParam customerParam);
	
	/**
	 * 2015.1.23 거래처 검색
	 * @param customerCode
	 * @return
	 */
	public Customer getCustomerById(String customerCode);
	
	/**
	 * 거래처 기본정보를 수정
	 * @param customer
	 */
	public void updateCustomerDefaultInfo(Customer customer);
	
}
