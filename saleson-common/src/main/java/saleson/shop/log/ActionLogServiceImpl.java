package saleson.shop.log;

import com.onlinepowers.framework.sequence.service.SequenceService;
import com.onlinepowers.framework.util.DateUtils;
import com.onlinepowers.framework.util.ValidationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import saleson.common.utils.SellerUtils;
import saleson.common.utils.ShopUtils;
import saleson.common.utils.UserUtils;
import saleson.seller.main.domain.Seller;
import saleson.shop.log.domain.ActionLog;

import javax.servlet.http.HttpServletRequest;

@Service("actionLogService")
public class ActionLogServiceImpl implements ActionLogService {

    @Autowired
    private ActionLogMapper actionLogMapper;

    @Override
    public void insertManagerActionLog(HttpServletRequest request) {

        ActionLog actionLog = new ActionLog();
        Seller seller = SellerUtils.getSeller();

        String loginId = "";
        String loginType = "";

        if (ValidationUtils.isNotNull(seller) && ShopUtils.isSellerPage()) {
            loginId = seller.getLoginId();
            loginType = "SELLER";
        } else {

            if (0 < UserUtils.getManagerId()) {
                // 사용자와 관리자는 같은 메소드 사용
                loginId = UserUtils.getLoginId();
                loginType = "MANAGER";
            }
        }

        actionLog.setLoginId(loginId);
        actionLog.setLoginType(loginType);

        actionLog.setRequestUri(request.getRequestURI());
        actionLog.setRequestMethod(request.getMethod());
        actionLog.setRemoteAddr(request.getRemoteAddr());

        /**
         * chaek
         *
         * 보통은 xml 에서 <include refid="CommonMapper.datetime" /> 를 쓰지만
         * 메뉴 사용 이력과 같이 db connection 이 많은 기능의 경우에는
         * DB 에서
         * TO_CHAR(SYSDATE,'YYYYMMDDHH24MISS') - oracle
         * DATE_FORMAT(NOW(),'%Y%m%d%H%i%s') - mysql
         * 이것과 같은 계산식을 사용하는것을 추천하지 않는다.
         * ==> value 만 넘기는것을 추천한다.
         * 특히 동시 접속이 많은 서비스의 경우에서 더 추천을 하는데
         * by 구글링
         * 관리 페이지는 해당 될지 안될지 모르겠음.. 글쎄 해당 안되는것 같기도 하고..
         * 수정은 주석처리만 하면 되니까 일단 go
         */
        actionLog.setCreatedDate(DateUtils.getToday("yyyyMMddHHmmss"));

        actionLogMapper.insertManagerActionLog(actionLog);


    }
}