package saleson.shop.qna;

import java.util.List;

import com.onlinepowers.framework.web.domain.ListParam;

import saleson.shop.qna.domain.Qna;
import saleson.shop.qna.domain.QnaAnswer;
import saleson.shop.qna.support.QnaParam;


public interface QnaService {
	
	/**
	 * 문의 등록
	 * @param qna
	 */
	public void insertQna(Qna qna);
	
	/**
	 * 문의 수정
	 * @param qna
	 */
	public void updateQna(Qna qna);
	
	/**
	 * 문의 삭제
	 * @param qna
	 */
	public void deleteQna(Qna qna);
	
	/**
	 * 문의 답변 등록
	 * @param qnaAnswer
	 */
	public void insertQnaAnswer(QnaAnswer qnaAnswer);
	
	/**
	 * 문의 답변 수정
	 * @param qnaAnswer
	 */
	public void updateQnaAnswer(QnaAnswer qnaAnswer);
	
	/**
	 * 문의 답변 삭제
	 * @param qnaAnswer
	 */
	public void deleteQnaAnswer(QnaAnswer qnaAnswer);
	
	/**
	 * 문의 목록 조회
	 * @param qnaParam
	 * @return
	 */
	public List<Qna> getQnaListByParam(QnaParam qnaParam);
	
	/**
	 * 문의 조회
	 * @param qnaParam
	 * @return
	 */
	public Qna getQnaByQnaId(int qnaId);
	
	/**
	 * 문의 목록 카운트
	 * @param qnaParam
	 * @return
	 */
	public int getQnaListCountByParam(QnaParam qnaParam);
	
	/**
	 * 문의별 답변 조회
	 * @param qnaId
	 * @return
	 */
	public List<QnaAnswer> getQnaAnswerListByQnaId(int qnaId);

	/**
	 * 문의별 답변 조회
	 * @param qnaId
	 * @return
	 */
	public QnaAnswer getQnaAnswerByQnaId(int qnaId);
	
	/**
	 * 문의 페이징 정보 셋팅
	 * @param qnaParam
	 */
	public void setQnaListPagination(QnaParam qnaParam);
	
	/**
	 * 문의 목록형식으로 삭제
	 * @param listparam
	 */
	public void deleteQnaData(ListParam listparam);
	
	/**
	 * 해당 문의 답변수 업데이트
	 * @param qnaId
	 */
	public void updateQnaAnswerCount(int qnaId);
}
