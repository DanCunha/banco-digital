package br.com.bancodigital.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDTO {

	private Long id;
	
	private String name;
	
	private String lastName;
	
	private String email;
	
	private Date dateBirth;
	
	private String cpf;
	
	private String cep;
	
	private String street;
	
	private String neighborhood;
	
	private String complement;
	
	private String city;
	
	private String state;
}
