package br.com.bancodigital.form;

import java.time.LocalDate;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonFormat;

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
public class CustomerForm {

	@NotNull
	@NotEmpty
	private String name;

	@NotNull
	@NotEmpty
	private String lastName;

	@NotNull
	@NotEmpty
	@Email
	private String email;

	@Past
    @JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate dateBirth;

	@Pattern(regexp="([0-9]{2}[\\.]?[0-9]{3}[\\.]?[0-9]{3}[\\/]?[0-9]{4}[-]?[0-9]{2})|([0-9]{3}[\\.]?[0-9]{3}[\\.]?[0-9]{3}[-]?[0-9]{2})",
            message="CPF format is invalid")
	private String cpf;

	public Customer converter() {
		return new Customer(name, lastName, email, dateBirth, cpf);
	}

}
