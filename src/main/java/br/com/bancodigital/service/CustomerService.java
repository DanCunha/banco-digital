package br.com.bancodigital.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import br.com.bancodigital.domain.Address;
import br.com.bancodigital.domain.Customer;
import br.com.bancodigital.exception.CustomerException;

@Service
public interface CustomerService {

	Customer add(Customer entity) throws CustomerException;
	Address addAddress(Address entity, Long customerId) throws CustomerException;
	Optional<Customer> findById(Long id);
}
