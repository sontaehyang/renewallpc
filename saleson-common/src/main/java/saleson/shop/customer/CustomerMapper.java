package saleson.shop.customer;

import java.util.List;

import saleson.shop.customer.domain.Customer;
import saleson.shop.customer.support.CustomerParam;

import com.onlinepowers.framework.orm.mybatis.annotation.Mapper;
import com.onlinepowers.framework.security.userdetails.User;

@Mapper("customerMapper")
public interface CustomerMapper {

	/**
	 * 전화주문 회원 검색
	 * @param customerParam
	 * @return
	 */
	List<Customer> getCustomerListByParamForCall(CustomerParam customerParam);
	
	/**
	 * 거래처 정보 - 전화번호 로 회원 정보 검색
	 * @param customer
	 * @return
	 */
	User getUserByParam(CustomerParam customerParam);
		
	/**
	 * 거래처 아이디 검색
	 * @param customer
	 * @return
	 */
	String getCustomerId(Customer customer);
	
	/**
	 * 거래처 일괄 등록
	 * @param list
	 */
	void insertCustomerForExcel(List<Customer> list);
	
	/**
	 * 거래처 신규 등록
	 * @param customer
	 */
	void insertCustomer(Customer customer);
	
	
	/**
	 * 거래처 수정
	 * @param customer
	 */
	void updateCustomer(Customer customer);
	
	/**
	 * 
	 * @param customer
	 * @return
	 */
	int getCustomerCountByParam(CustomerParam customerParam);
	
	List<Customer> getCustomerList(CustomerParam customerParam);

	Customer getCustomerById(String customerCode);
	
	void updateCustomerDefaultInfo(Customer customer);
}
