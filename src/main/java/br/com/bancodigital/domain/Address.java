package br.com.bancodigital.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Address {
	
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String cep;

	private String street;

	private String neighborhood;

	private String complement;

	private String city;

	private String state;

	public Address(String cep, String street, String neighborhood, String complement, String city, String state) {
		this.cep = cep;
		this.street = street;
		this.neighborhood = neighborhood;
		this.complement = complement;
		this.city = city;
		this.state = state;
	}
	
	

}
