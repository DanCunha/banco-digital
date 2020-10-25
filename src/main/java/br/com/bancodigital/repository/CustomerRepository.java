package br.com.bancodigital.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.bancodigital.domain.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long>{

	Optional<Customer> findByCpf(String cpf);
	Optional<Customer> findByEmail(String email);
}
