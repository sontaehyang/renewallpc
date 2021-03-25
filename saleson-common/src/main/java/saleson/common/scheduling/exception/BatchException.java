package saleson.common.scheduling.exception;

import com.onlinepowers.framework.exception.OpRuntimeException;

@SuppressWarnings("serial")
public class BatchException extends OpRuntimeException {

	public BatchException(String message) {
		super(message);
	}
	
	public BatchException(Throwable cause) {
		super(cause);
	}
	
	public BatchException(String message, Throwable cause) {
		super(message, cause);
	}
}
