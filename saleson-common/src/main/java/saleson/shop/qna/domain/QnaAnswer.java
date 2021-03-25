package saleson.shop.qna.domain;

public class QnaAnswer {
	
	private int qnaAnswerId;
	private int qnaId;
	private String answer;
	private long userId;
	
	//2014.1.8 답변자 제목 컬럼 추가
	//public - > private으로 변경
	private String title; 
	//2014.1.14 SMS 추가
	private String sendSmsFlag;
	//2014.1.13 MAIL 추가
	private String sendMailFlag;
	private String answerDate;
	
	private String userNm;
	
	public String getAnswerDate() {
		return answerDate;
	}
	public void setAnswerDate(String answerDate) {
		this.answerDate = answerDate;
	}
	public int getQnaAnswerId() {
		return qnaAnswerId;
	}
	public void setQnaAnswerId(int qnaAnswerId) {
		this.qnaAnswerId = qnaAnswerId;
	}
	public int getQnaId() {
		return qnaId;
	}
	public void setQnaId(int qnaId) {
		this.qnaId = qnaId;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSendSmsFlag() {
		return sendSmsFlag;
	}
	public void setSendSmsFlag(String sendSmsFlag) {
		this.sendSmsFlag = sendSmsFlag;
	}
	public String getSendMailFlag() {
		return sendMailFlag;
	}
	public void setSendMailFlag(String sendMailFlag) {
		this.sendMailFlag = sendMailFlag;
	}
	public String getUserNm() {
		return userNm;
	}
	public void setUserNm(String userNm) {
		this.userNm = userNm;
	}
	
}
