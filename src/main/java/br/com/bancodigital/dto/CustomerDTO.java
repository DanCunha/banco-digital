package br.com.bancodigital.dto;

import java.time.LocalDate;

import br.com.bancodigital.domain.Address;
import br.com.bancodigital.domain.Customer;
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
	
	private LocalDate dateBirth;
	
	private String cpf;
	
	private AddressDTO address;

	public CustomerDTO(Customer customer) {
		this.id = customer.getId();
		this.name = customer.getName();
		this.lastName = customer.getLastName();
		this.email = customer.getEmail();
		this.dateBirth = customer.getDateBirth();
		this.cpf = customer.getCpf();
	}
	
	public CustomerDTO(Customer customer, Address address) {
		this.id = customer.getId();
		this.name = customer.getName();
		this.lastName = customer.getLastName();
		this.email = customer.getEmail();
		this.dateBirth = customer.getDateBirth();
		this.cpf = customer.getCpf();
		this.address = new AddressDTO(address);
	}
	
}
