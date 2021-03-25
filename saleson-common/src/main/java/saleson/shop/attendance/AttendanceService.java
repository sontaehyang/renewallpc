package saleson.shop.attendance;


import saleson.shop.attendance.domain.Attendance;
import saleson.shop.attendance.domain.AttendanceCheck;
import saleson.shop.attendance.domain.CalendarData;
import saleson.shop.attendance.support.AttendanceParam;

import java.util.List;
import java.util.Map;

public interface AttendanceService {

    /**
     * 출석체크 관리 목록 카운트 조회
     * @param attendanceParam
     * @return
     */
	public int getAttendanceCountByParam(AttendanceParam attendanceParam);

    /**
     * 출석체크 관리 목록 조회
     * @param attendanceParam
     * @return
     */
	public List<Attendance> getAttendanceListByParam(AttendanceParam attendanceParam);

	/**
	 * 출석체크 마스터 & 설정 등록
	 * @param attendance
	 */
	void insertAttendanceAndConfig(Attendance attendance);

    /**
     * 출석체크 마스터 등록
     * @param attendance*
     * @return
     */
    public void insertAttendance(Attendance attendance);

    /**
     * 출석체크 설정 등록
     * @param attendance
     * @return
     */
    public void insertAttendanceConfig(Attendance attendance);

    /**
     * 출석체크 설정 중복 확인
     * @param attendance
     * @return
     */
    public int getAttendanceCountForDuplication(Attendance attendance);

    /**
     * 출석체크 설정 삭제
     * @param attendanceParam
     */
    public void deleteAttendanceById(AttendanceParam attendanceParam);

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
	 * 출석 체크 달력
	 * @return
	 */
	List<CalendarData> getAttendanceCalendar();

	/**
	 * 출석 체크
	 */
	Map<String, List<String>> checkAttendance(String date, Long userId);

    /**
     * 출석체크 설정 업데이트
     * @param attendance
     */
	void updateAttendance(Attendance attendance);

}
