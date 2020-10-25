package br.com.bancodigital.validation;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorDetail {

	private String message;

	public ErrorDetail(String message) {
		this.message = message;
	}
	
	
}
