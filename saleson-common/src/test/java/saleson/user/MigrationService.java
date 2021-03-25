package saleson.user;

import com.onlinepowers.framework.sequence.domain.Sequence;
import com.onlinepowers.framework.sequence.service.SequenceService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import saleson.common.SalesonTest;
import saleson.user.domain.MigrationMapper;

@Slf4j
public class MigrationService extends SalesonTest {
	@Autowired
	private MigrationMapper migrationMapper;


	@Autowired
	private SequenceService sequenceService ;


	@Test
	void migrateUser() {
		// 1. 회원 엑셀을 열어 좌측 1열을 추가하고 USER_ID를 엑셀에서 부여한 후 저장한다.
		// 2. excel_member 데이터를 삭제하고 테이블에 엑셀 데이터를 IMPORT 한다.
		// - DELETE FROM EXCEL_MEMBER;


		// 3. 초기화 maxUserId
		migrationMapper.deleteUser();
		migrationMapper.deleteUserRole();
		migrationMapper.deletePoint();
		migrationMapper.deleteUserSns();

		long maxUserId = migrationMapper.getMaxUserId();


		// 3. OP_USER 데이터 등록
		// 아이디 중복이 있음. (Jun.00) ==> 중복데이터의 LOGIN_ID를 Jun.00 => email로 변경.
		// -- UPDATE EXCEL_USER SET LOGIN_ID = EMAIL WHERE EMAIL = 'june7900@naver.com'
		migrationMapper.insertUser();


		// 4. OP_USER_DETAIL 데이터 등록
		migrationMapper.insertUserDetail();


		// 5. OP_USER_ROLE 데이터 등록
		migrationMapper.insertUserRole();


		// 6. OP_POINT 등록
		migrationMapper.insertPoint();


		// 7. OP_USER_SNS 데이터 등록
		migrationMapper.insertUserSns();


		// 8. SEQUENCE 업데이트 (USER, POINT)
		sequenceService.updateSequence(new Sequence("OP_USER", maxUserId + 1L));
		sequenceService.updateSequence(new Sequence("OP_POINT", maxUserId + 1L));

	}
}
