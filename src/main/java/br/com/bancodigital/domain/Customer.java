package br.com.bancodigital.domain;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import br.com.bancodigital.form.StatusEnum;
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
	
	@Column(name = "cpfImage", length = 1000000)
	private byte[] cpfImage;
	
	private StatusEnum status;
	
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
	private Address address;
	
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "account_id", referencedColumnName = "id")
	private Account account;

	public Customer(String name, String lastName, String email, LocalDate dateBirth, String cpf) {
		this.name = name;
		this.lastName = lastName;
		this.email = email;
		this.dateBirth = dateBirth;
		this.cpf = cpf;
		this.status = StatusEnum.ANALYSIS;
	}
	
}
