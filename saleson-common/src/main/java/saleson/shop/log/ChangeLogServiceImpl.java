package saleson.shop.log;

import com.onlinepowers.framework.security.userdetails.User;
import com.onlinepowers.framework.sequence.service.SequenceService;
import com.onlinepowers.framework.util.JsonViewUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import saleson.common.utils.UserUtils;
import saleson.shop.log.domain.ChangeLog;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;

@Service("changeLogService")
public class ChangeLogServiceImpl implements ChangeLogService {

    @Autowired
    private ChangeLogMapper changeLogMapper;

    @Autowired
    private
    SequenceService sequenceService;

    @Override
    public void insertUserChangeLog(long userId, HttpServletRequest request) {

        long managerId;

        if (UserUtils.isManagerLogin()) {
            managerId = UserUtils.getManagerId();
        } else {
            managerId = 0;
        }

        changeLogMapper
                .insertUserChangeLog(
                        new ChangeLog(userId, managerId, getParameterByJson(request),
                                request.getRemoteAddr()));
    }

    @Override
    public void insertManagerChangeLog(HttpServletRequest request, User user) {

        changeLogMapper
                .insertManagerChangeLog(
                        new ChangeLog(user.getUserId(), UserUtils.getManagerId(),
                                getParameterByJson(request),
                                request.getRemoteAddr()));

    }

    private String getParameterByJson(HttpServletRequest request) {

        Enumeration<?> paramNames = request.getParameterNames();

        HashMap<String, Object> paramValueMap = new HashMap<>();

        while (paramNames.hasMoreElements()) {

            String paramName = (String) paramNames.nextElement();
            String[] parameterValues = request.getParameterValues(paramName);

            if (paramName.startsWith("password")
                    || paramName.startsWith("changePassword")
                    || paramName.startsWith("reChangePassword") ) {

            } else {

                if (parameterValues.length == 1) {
                    paramValueMap.put(paramName, parameterValues[0]);
                } else {
                    paramValueMap.put(paramName, parameterValues);
                }
            }

        }

        return JsonViewUtils.objectToJson(paramValueMap);
    }

}
