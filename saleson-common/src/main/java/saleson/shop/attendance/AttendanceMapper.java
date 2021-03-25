package saleson.shop.attendance;

import com.onlinepowers.framework.orm.mybatis.annotation.Mapper;
import saleson.shop.attendance.domain.*;
import saleson.shop.attendance.support.AttendanceParam;

import java.util.List;

@Mapper("attendanceMapper")
public interface AttendanceMapper {

    /**
     * 출석체크 이벤트 관리 목록 카운트 조회
     * @param attendanceParam
     * @return
     */
    int getAttendanceCountByParam(AttendanceParam attendanceParam);


    /**
     * 출석체크 이벤트 관리 목록 조회
     * @param attendanceParam
     * @return
     */
    List<Attendance> getAttendanceListByParam(AttendanceParam attendanceParam);


    /**
     * 출석체크 마스터 정보 DB Insert
     * @param attendance
     * @return
     */
    long insertAttendance(Attendance attendance);


    /**
     * 출석체크 마스터 정보 DB Insert
     * @param attendanceConfig
     * @return
     */
    void insertAttendanceConfig(AttendanceConfig attendanceConfig);


    /**
     * 출석체크 설정 중복 확인
     * @param attendance
     * @return
     */
    int getAttendanceCountForDuplication(Attendance attendance);


    /**
     * 출석체크 설정 삭제
     * @param attendanceParam
     */
    void deleteAttendanceById(AttendanceParam attendanceParam);


	/**
	 * 출석 정보 조회
	 * @param param
	 * @return
	 */
	Attendance getAttendanceByParam(AttendanceParam param);


	/**
	 * 출석 체크 리스트
	 * @param param
	 * @return
	 */
	List<AttendanceCheck> getAttendanceCheckedListByParam(AttendanceParam param);


	/**
	 * 출석 체크 여부 조회 (Count)
	 * @param param
	 * @return
	 */
	int getAttendanceCheckedCountByParam(AttendanceParam param);


	/**
	 * 출석 체크 데이터 등록
	 * @param attendanceCheck
	 */
	void insertAttendanceCheck(AttendanceCheck attendanceCheck);


	/**
	 * 춯석 이벤트 참여 정보 내역 조회 .
	 * @param param
	 * @return
	 */
	List<AttendanceEvent> getAttendanceEventListByParam(AttendanceParam param);


	/**
	 * 이벤트 참여 정보 일괄등록
	 * @param eventList
	 */
	void insertAttendanceEventBy(List<AttendanceEvent> eventList);


	/**
	 * 출석 체크 후 이벤트 정보 내역을 일괄 수정한다. (from AttendanceEventIds)
	 * @param eventData
	 */
	void updateAttendanceEventByIds(EventData eventData);


    /**
     * 출석체크 마스터 수정
     * @param attendance
     */
    void updateAttendance(Attendance attendance);


    /**
     * 출석체크 마스터에 종속된 출석체크 조건 전체 삭제
     * @param attendance
     */
    void deleteAllAttendanceConfigByAttendanceId(Attendance attendance);
}
