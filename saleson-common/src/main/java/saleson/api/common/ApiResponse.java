package saleson.api.common;

import saleson.api.common.exception.ApiException;

public class ApiResponse {

	private boolean success;

	private String message;


	public ApiResponse() {}

	public ApiResponse(boolean success, String message) {
		this.message = message;
		this.success = success;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public static ApiResponse success() {
		return success("Success");
	}

	public static ApiResponse success(String message) {
		return new ApiResponse(true, message);
	}

	public static ApiResponse error(Exception e) {
		if (e instanceof ApiException) {
			return error(e.getMessage());
		} else {
			return error("SystemError");
		}
	}


	public static ApiResponse error() {
		return error("Success");
	}

	public static ApiResponse error(String message) {
		return new ApiResponse(false, message);
	}


}
