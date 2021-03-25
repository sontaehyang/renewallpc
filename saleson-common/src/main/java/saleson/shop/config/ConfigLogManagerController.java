package saleson.shop.config;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import saleson.common.api.ResponseMessage;
import saleson.common.enumeration.LogType;
import saleson.shop.config.domain.ConfigLog;
import saleson.shop.config.dto.ConfigLogDto;

import java.util.List;


@RestController
@RequestMapping("/api/opmanager/configlogs")
public class ConfigLogManagerController {

    private static final Logger log = LoggerFactory.getLogger(ConfigLogManagerController.class);

    @Autowired
    private ConfigLogService configLogService;

    @Autowired
	private ModelMapper modelMapper;

    @GetMapping
    public ResponseEntity<List<ConfigLog>> configLogs () {

        List<ConfigLog> configLogs;
        HttpStatus httpStatus = HttpStatus.OK;
        try {
            configLogs = configLogService.findConfigLogs();

        } catch (Exception e) {
            log.error("ERROR: {}", e.getMessage(), e);
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            configLogs = null;
        }

        return new ResponseEntity<>(configLogs, httpStatus);
    }

    @GetMapping(value = "/{logType}")
    public ResponseEntity<List<ConfigLog>> configLogsByLogType (@PathVariable("logType") LogType logType) {

        List<ConfigLog> configLogs;
        HttpStatus httpStatus = HttpStatus.OK;
        try {
            configLogs = configLogService.findConfigLogsByLogType(logType);

        } catch (Exception e) {
            log.error("ERROR: {}", e.getMessage(), e);
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            configLogs = null;
        }
        return new ResponseEntity<>(configLogs, httpStatus);
    }

    @PostMapping
    public ResponseEntity<ResponseMessage> insertConfigLog (@RequestBody ConfigLogDto configLogDto) {

        HttpStatus httpStatus = HttpStatus.OK;
        String message = "작업이 완료되었습니다.";
        boolean isSuccess = true;
        try {

        	ConfigLog configLog = modelMapper.map(configLogDto, ConfigLog.class);
            configLogService.insertConfigLog(configLog);

        } catch (Exception e) {
            log.error("ERROR: {}", e.getMessage(), e);

            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            message = "저장에 실패했습니다";
            isSuccess = false;
        }

        return new ResponseEntity<>(new ResponseMessage(isSuccess, message),httpStatus);
    }

    @PutMapping
    public ResponseEntity<ResponseMessage> updateConfigLog (@RequestBody List<ConfigLog> configLogs) {

        HttpStatus httpStatus = HttpStatus.OK;
        String message = "작업이 완료되었습니다.";
        boolean isSuccess = true;
        try {
            configLogService.updateAllConfigLogs(configLogs);
        } catch (Exception e) {
            log.error("ERROR: {}", e.getMessage(), e);

            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            message = "수정에 실패했습니다";
            isSuccess = false;
        }

        return new ResponseEntity<>(new ResponseMessage(isSuccess, message),httpStatus);
    }

}
