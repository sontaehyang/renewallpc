package saleson.shop.user.support;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import saleson.common.Const;
import saleson.shop.user.domain.UserDetail;

import com.onlinepowers.framework.security.userdetails.User;
import com.onlinepowers.framework.util.DateUtils;
import com.onlinepowers.framework.util.MessageUtils;
import com.onlinepowers.framework.util.NumberUtils;
import com.onlinepowers.framework.web.servlet.view.AbstractSXSSFExcelView;
import com.onlinepowers.framework.web.servlet.view.support.CellIndex;
import com.onlinepowers.framework.web.servlet.view.support.HeaderCell;

public class UserExcelView extends AbstractSXSSFExcelView{

	private UserSearchParam userSearchParam;
	private List<User> userList;

	public UserExcelView() {

		setFileName("USER_" + DateUtils.getToday(Const.DATETIME_FORMAT) + ".xlsx");
	}


	@Override
	public void buildExcelDocument(Map<String, Object> model,
									  SXSSFWorkbook workbook, HttpServletRequest request,
									  HttpServletResponse response) throws Exception {

		// 1.데이터 가져오기
		userSearchParam = (UserSearchParam)model.get("reOpmanagerUserSearchParam");
		userList = (List<User>)model.get("userList");

		// 2. 데이터 생성(시트생성)
		buildItemSheet(workbook, userList);

	}

	private void buildItemSheet(SXSSFWorkbook workbook, List<User> list) {

		if(list == null){
			return;
		}

		// 3. 시트생성
		String sheetTitle = "user_main";
		String title = "회원정보";

		// 4. Cell (컬럼) 설정
		HeaderCell[] headerCells = new HeaderCell[]{

				new HeaderCell(700, 	"No."),
				new HeaderCell(2000, 	"ID(Email)"),
				new HeaderCell(1000, 	"이름"),
				//new HeaderCell(1500, 	"닉네임"),
				new HeaderCell(4000, 	"우편번호"),
				new HeaderCell(8000, 	"주소"),
				new HeaderCell(2000, 	"전화번호"),
				new HeaderCell(2000, 	"핸드폰"),
				new HeaderCell(2000, 	MessageUtils.getMessage("M00246")),
				new HeaderCell(1000, 	"방문횟수"),
				/*new HeaderCell(1000, 	"총 구매건수"),
				new HeaderCell(2500, 	"총 상품구매 금액"),*/
				new HeaderCell(1100, 	"가입일"),
				new HeaderCell(1500, 	"Email수신동의"),
				new HeaderCell(1500, 	"SMS수신동의"),
				//new HeaderCell(1000, 	"회원구분"),
				new HeaderCell(1500, 	"회원가입경로")

		};

		/**
		 * 상단 타이틀 및 테이블 헤더 생성.
		 */
		Sheet sheet = workbook.createSheet(sheetTitle);
		Row row = sheet.createRow((short) 0);

		createSheetHeader(sheet, row, headerCells, title);

		// Table Body
		int rowIndex = 2;
		int index = 0;
		for(User user : list){

			UserDetail userDetail = new UserDetail();

			userDetail = (UserDetail)user.getUserDetail();

			// 행 높이 설정
			row = sheet.createRow(rowIndex);
			row.setHeight((short) 400);

			CellIndex cellIndex = new CellIndex(-1);

			setText(sheet, 			rowIndex, cellIndex, String.valueOf(++index)); //순번
			setText(sheet, 			rowIndex, cellIndex, String.valueOf(user.getEmail())); //ID(Email)
			setText(sheet, 			rowIndex, cellIndex, user.getUserName()); //이름
			//setText(sheet, 			rowIndex, cellIndex, userDetail.getNickname()); //닉네임
			setText(sheet, 			rowIndex, cellIndex, userDetail.getPost()); //주소
			setTextLeft(sheet, 			rowIndex, cellIndex, userDetail.getAddress() + " " + userDetail.getAddressDetail()); //주소
			setText(sheet, 			rowIndex, cellIndex, userDetail.getFullTelNumber()); //전화번호
			setText(sheet, 			rowIndex, cellIndex, userDetail.getPhoneNumber()); //핸드폰
			setText(sheet, 			rowIndex, cellIndex,  NumberUtils.formatNumber(userDetail.getPoint(),"#,##0")+MessageUtils.getMessage("M00049")); //포인트
			setText(sheet, 			rowIndex, cellIndex, String.valueOf(user.getLoginCount())); //방문횟수
			/*setText(sheet, 			rowIndex, cellIndex, String.valueOf(userDetail.getBuyCount())); //총 구매건수
			setText(sheet, 			rowIndex, cellIndex, NumberUtils.formatNumber(userDetail.getBuyPrice(),"#,##0")+MessageUtils.getMessage("M00049")); //총 상품구매 금액
			*/
			setText(sheet, 			rowIndex, cellIndex, DateUtils.date(String.valueOf(user.getCreatedDate()))); //가입일

			String emailFlag = MessageUtils.getMessage("M00233");
			if (!userDetail.getReceiveEmail().equals("0")) {
				emailFlag = MessageUtils.getMessage("M00234");
			}
			setText(sheet, 			rowIndex, cellIndex, emailFlag); //Email수신동의

			String snsFlag = MessageUtils.getMessage("M00233");
			if (!userDetail.getReceiveSms().equals("0")) {
				snsFlag = MessageUtils.getMessage("M00234");
			}
			setText(sheet, 			rowIndex, cellIndex, snsFlag); //SMS 수신동의

			/*String businessType = "사업자";
			if (!userDetail.getBusinessFlag().equals("Y")) {
				businessType = "일반";
			}
			setText(sheet, 			rowIndex, cellIndex, businessType);*/ //회원구분

			setText(sheet, 			rowIndex, cellIndex, "PC"); //회원가입경로

			rowIndex++;
		}
	}


}
