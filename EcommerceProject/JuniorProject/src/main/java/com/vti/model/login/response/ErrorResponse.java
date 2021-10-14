package com.vti.model.login.response;

import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class ErrorResponse {
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private String field;
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private String message;

	public ErrorResponse(String field, String message) {
		this.field = field;
		this.message = message;
	}

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	List<ErrorResponse> errors = new ArrayList<>();

	public void addError(String field, String message) {
		errors.add(new ErrorResponse(field, message));
	}
}