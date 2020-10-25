package br.com.bancodigital.domain;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

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
public class Customer {

	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String name;
	
	private String lastName;
	
	private String email;
	
	private LocalDate dateBirth;
	
	private String cpf;
	
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
	private Address address;

	public Customer(String name, String lastName, String email, LocalDate dateBirth, String cpf) {
		this.name = name;
		this.lastName = lastName;
		this.email = email;
		this.dateBirth = dateBirth;
		this.cpf = cpf;
	}
	
}
