package br.com.bancodigital.exception;


@SuppressWarnings("serial")
public class CustomerException extends Exception {

	public CustomerException(String message, Throwable cause) {
		super(message, cause);
	}
}

