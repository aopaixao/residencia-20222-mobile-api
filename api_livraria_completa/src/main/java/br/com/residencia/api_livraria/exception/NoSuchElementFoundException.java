package br.com.residencia.api_livraria.exception;

public class NoSuchElementFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public NoSuchElementFoundException(String message) {
		super(message);
	}
}