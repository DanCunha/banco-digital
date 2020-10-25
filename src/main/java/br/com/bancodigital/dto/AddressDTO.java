package br.com.bancodigital.dto;

import br.com.bancodigital.domain.Address;
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
public class AddressDTO {

	private Long id;

	private String cep;

	private String street;

	private String neighborhood;

	private String complement;

	private String city;

	private String state;

	public AddressDTO(Address address) {
		this.id = address.getId();
		this.cep = address.getCep();
		this.street = address.getStreet();
		this.neighborhood = address.getNeighborhood();
		this.complement = address.getComplement();
		this.city = address.getCity();
		this.state = address.getState();
	}
	
	
}
