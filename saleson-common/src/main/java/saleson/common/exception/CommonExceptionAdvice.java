package saleson.common.exception;

import com.onlinepowers.framework.exception.OpRuntimeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.util.AntPathMatcher;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;
import saleson.api.common.ApiResponseEntity;
import saleson.api.common.enumerated.ApiError;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class CommonExceptionAdvice {

    private static final Logger log = LoggerFactory.getLogger(CommonExceptionAdvice.class);

    @ExceptionHandler({
            HttpRequestMethodNotSupportedException.class
    })
    public ResponseEntity methodNotAllowed(HttpServletRequest request, Exception e) {

        ApiError apiError = ApiError.METHOD_NOT_ALLOWED;

        printErrorLog(request, e);

        if (!isApiUrl(request)) {
            throw new OpRuntimeException(apiError.getHttpStatus().toString());
        }

        return ApiResponseEntity.error(apiError);
    }

    @ExceptionHandler({
            MissingServletRequestParameterException.class,
            ServletRequestBindingException.class,
            TypeMismatchException.class,
            HttpMessageNotReadableException.class,
            MethodArgumentNotValidException.class,
            MissingServletRequestPartException.class,
            BindException.class
    })
    public ResponseEntity badRequest(HttpServletRequest request, Exception e) {

        ApiError apiError = ApiError.BAD_REQUEST;

        printErrorLog(request, e);

        if (!isApiUrl(request)) {
            throw new OpRuntimeException(apiError.getHttpStatus().toString());
        }

        return ApiResponseEntity.error(apiError);
    }

    @ExceptionHandler({
            NoHandlerFoundException.class
    })
    public ResponseEntity notFound(HttpServletRequest request, Exception e) {

        ApiError apiError = ApiError.NOT_FOUND;

        printErrorLog(request, e);

        if (!isApiUrl(request)) {
            throw new OpRuntimeException(apiError.getHttpStatus().toString());
        }

        return ApiResponseEntity.error(apiError);
    }

    @ExceptionHandler({
            MissingPathVariableException.class,
            ConversionNotSupportedException.class,
            HttpMessageNotWritableException.class,
            NullPointerException.class,
            NumberFormatException.class,
            IOException.class
    })
    public ResponseEntity internalServerError(HttpServletRequest request, Exception e) {

        ApiError apiError = ApiError.SYSTEM_ERROR;

        printErrorLog(request, e);

        if (!isApiUrl(request)) {
            throw new OpRuntimeException(apiError.getHttpStatus().toString());
        }

        return ApiResponseEntity.error(apiError);

    }

    private boolean isApiUrl(HttpServletRequest request) {

        List<String> urlList = new ArrayList<>();

        urlList.add("/api/**");
        urlList.add("/auth/**");

        AntPathMatcher antPathMatcher = new AntPathMatcher();
        String uri = request.getRequestURI();
        for (String pattern : urlList) {
            if (antPathMatcher.match(pattern, uri)) {
                return true;
            }
        }

        return false;
    }

    private void printErrorLog(HttpServletRequest request, Exception e) {

        log.error("Error URI ->  {}",request.getRequestURI());
        log.error("Exception ->  {}",e.getClass().getSimpleName());
        log.error("Exception Message ->  {}",e.getMessage());
		log.error("ErrorLog", e);
    }
}
