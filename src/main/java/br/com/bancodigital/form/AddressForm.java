package br.com.bancodigital.form;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

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
public class AddressForm {
	
	private Long id;

	@NotNull
	@NotEmpty
	@Pattern(regexp = "\\d{5}-\\d{3}",
    message="CEP format is invalid")
	private String cep;

	@NotNull
	@NotEmpty
	private String street;

	@NotNull
	@NotEmpty
	private String neighborhood;

	@NotNull
	@NotEmpty
	private String complement;

	@NotNull
	@NotEmpty
	private String city;

	@NotNull
	@NotEmpty
	private String state;
	
	public Address converter() {
		return new Address(cep, street, neighborhood, complement, city, state);
	}
}
