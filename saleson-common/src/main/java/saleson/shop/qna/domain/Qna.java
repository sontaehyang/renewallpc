package saleson.shop.qna.domain;

import java.util.ArrayList;
import java.util.List;


import org.springframework.web.multipart.MultipartFile;
import saleson.common.utils.ShopUtils;

import com.onlinepowers.framework.util.StringUtils;
import com.onlinepowers.framework.web.domain.SearchParam;
import com.onlinepowers.framework.web.pagination.Pagination;

import javax.validation.constraints.NotEmpty;

@SuppressWarnings("serial")
public class Qna extends SearchParam {
	
	/**
	 * 상품문의 구분 1
	 */
	public static final String QNA_GROUP_TYPE_ITEM = "1";
	
	/**
	 * 1:1 문의 구분 0
	 */
	public static final String QNA_GROUP_TYPE_INDIVIDUAL = "0";
	
	private int qnaId;
	/**
	 * 2020.06.03
	 * qnaGroup 검증
	 */
	@NotEmpty
	private String qnaGroup;
	private String qnaType;

	private long userId;
	private String userName;
	private String email;

	private int itemId;
	private String itemName;
	private String itemUserCode;
	private String orderCode;
	private int answerCount;
	private String itemImage;
	/**
	 * 2015.1.8 비밀글 여부 컬럼 추가
	 */
	private String secretFlag;
	private String createdDate;
	/**
	 * 2014.12.26
	 * subject 컬럼 추가 및 검증
	 */
	@NotEmpty
	private String subject; //제목
	/**
	 * 2014.12.26
	 * 문의자 내용 검증 
	 */
	@NotEmpty
	private String question; 
	private String displayFlag;
	
	QnaAnswer qnaAnswer; 
	
	private String loginId;
	
	private String where;
	private String query;
	private String orderBy;
	private String sort;
	private String language;
	
	private Pagination pagination;
	
	private String searchStartDate;
	private String searchEndDate;
	
	
	private long sellerId;
	private String sellerName; 
	
	// 답변 목록
	List<QnaAnswer> qnaAnswers = new ArrayList<>();
	
	private String userCode;
	private String answer;

	private String qnaImage = "";

	// 문의 이미지
	private MultipartFile qnaImageFile;
	
	public String getItemImage() {
		return itemImage;
	}
	public void setItemImage(String itemImage) {
		this.itemImage = itemImage;
	}
	public String getUserCode() {
		return userCode;
	}
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public String getItemUserCode() {
		return itemUserCode;
	}
	public void setItemUserCode(String itemUserCode) {
		this.itemUserCode = itemUserCode;
	}
	public QnaAnswer getQnaAnswer() {
		return qnaAnswer;
	}
	public void setQnaAnswer(QnaAnswer qnaAnswer) {
		this.qnaAnswer = qnaAnswer;
	}
	public String getLoginId() {
		return loginId;
	}
	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}
	public int getAnswerCount() {
		return answerCount;
	}
	public void setAnswerCount(int answerCount) {
		this.answerCount = answerCount;
	}
	public String getSecretFlag() {
		return secretFlag;
	}
	public void setSecretFlag(String secretFlag) {
		this.secretFlag = secretFlag;
	}
	public int getQnaId() {
		return qnaId;
	}
	public void setQnaId(int qnaId) {
		this.qnaId = qnaId;
	}
	public String getQnaGroup() {
		return qnaGroup;
	}
	public void setQnaGroup(String qnaGroup) {
		this.qnaGroup = qnaGroup;
	}
	public String getQnaType() {
		return qnaType;
	}
	public void setQnaType(String qnaType) {
		this.qnaType = qnaType;
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
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getItemId() {
		return itemId;
	}
	public void setItemId(int itemId) {
		this.itemId = itemId;
	}
	public String getOrderCode() {
		return orderCode;
	}
	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}
	public String getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public String getDisplayFlag() {
		return displayFlag;
	}
	public void setDisplayFlag(String displayFlag) {
		this.displayFlag = displayFlag;
	}
	
	public long getSellerId() {
		return sellerId;
	}
	public void setSellerId(long sellerId) {
		this.sellerId = sellerId;
	}
	
	public String getSellerName() {
		return sellerName;
	}
	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
	}
	public List<QnaAnswer> getQnaAnswers() {
		return qnaAnswers;
	}
	public void setQnaAnswers(List<QnaAnswer> qnaAnswers) {
		this.qnaAnswers = qnaAnswers;
	}
	public String getMaskUsername() {
		
		try {
			if (StringUtils.isNotEmpty(this.userName)) {
				return this.userName.substring(0, this.userName.length() - 2) + "**";
			}
		} catch(Exception e) {
			return "";
		}
		
		return "";
	}
	
	/**
	 * 목록 이미지 경로
	 * @return
	 */
	public String getImageSrc() {
		return ShopUtils.listImage(this.itemUserCode, this.itemImage);
	}
	public String getWhere() {
		return where;
	}
	public void setWhere(String where) {
		this.where = where;
	}
	public String getQuery() {
		return query;
	}
	public void setQuery(String query) {
		this.query = query;
	}
	public String getOrderBy() {
		return orderBy;
	}
	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}
	public String getSort() {
		return sort;
	}
	public void setSort(String sort) {
		this.sort = sort;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public Pagination getPagination() {
		return pagination;
	}
	public void setPagination(Pagination pagination) {
		this.pagination = pagination;
	}
	public String getSearchStartDate() {
		return searchStartDate;
	}
	public void setSearchStartDate(String searchStartDate) {
		this.searchStartDate = searchStartDate;
	}
	public String getSearchEndDate() {
		return searchEndDate;
	}
	public void setSearchEndDate(String searchEndDate) {
		this.searchEndDate = searchEndDate;
	}

	public String getQnaImage() {
		return qnaImage;
	}

	public void setQnaImage(String qnaImage) {
		this.qnaImage = qnaImage;
	}

	public MultipartFile getQnaImageFile() {
		return qnaImageFile;
	}

	public void setQnaImageFile(MultipartFile qnaImageFile) {
		this.qnaImageFile = qnaImageFile;
	}


}
