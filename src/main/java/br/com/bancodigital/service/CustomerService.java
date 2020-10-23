package br.com.bancodigital.service;

import org.springframework.stereotype.Service;

import br.com.bancodigital.domain.Customer;

@Service
public interface CustomerService {

	Customer add(Customer customer);
}
