package saleson.shop.inquiry;


import java.util.List;

import saleson.shop.inquiry.domain.Inquiry;
import saleson.shop.inquiry.support.InquiryParam;

public interface InquiryService {


    /**
     * 문의 등록.
     *
     * @param inquiry
     * @return
     */
    public void insertInquiry(Inquiry inquiry);

    /**
     * 문의 리스트 카운트.
     *
     * @param inquiryParam
     * @return
     */
    public int getInquiryCount(InquiryParam inquiryParam);


    /**
     * 문의 리스트 불러오기.
     *
     * @param inquiryParam
     * @return
     */
    public List<Inquiry> getInquiryList(InquiryParam inquiryParam);

    /**
     * 체크된 문의 삭제
     *
     * @param inquiryParam
     */
    public void deleteSelectInquiry(InquiryParam inquiryParam);

    /**
     * 문의 아이디로 해당 문의 불러옴. (상세 보기)
     *
     * @param inqueryId
     * @return
     */
    public Inquiry getInquiryViewById(int inqueryId);

    /**
     * 문의 답변 여부 수정.
     *
     * @param inquiry
     */
    public void editAnswerFlag(Inquiry inquiry);

    /**
     * 문의에대한 SMS 답변 하기.
     *
     * @param inquiry
     */
    public void answerSendSms(Inquiry inquiry);
}