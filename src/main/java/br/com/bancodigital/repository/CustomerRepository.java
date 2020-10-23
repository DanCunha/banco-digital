package br.com.bancodigital.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.bancodigital.domain.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long>{

}
