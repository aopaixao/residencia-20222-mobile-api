package br.com.residencia.api_livraria.exception;

import java.util.List;

public class ErrorResponse {
	private final int status;
	private final String message;
	private List<String> details;

	public ErrorResponse(int status, String message, List<String> details) {
		super();
		this.status = status;
		this.message = message;
		this.details = details;
	}

	public ErrorResponse(int status, String message) {
		super();
		this.status = status;
		this.message = message;
	}

	public List<String> getDetails() {
		return details;
	}

	public void setDetails(List<String> details) {
		this.details = details;
	}

	public int getStatus() {
		return status;
	}

	public String getMessage() {
		return message;
	}
}