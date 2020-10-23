package br.com.bancodigital.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.bancodigital.domain.Customer;
import br.com.bancodigital.repository.CustomerRepository;
import br.com.bancodigital.service.CustomerService;

@Service
public class CustomerServiceImpl implements CustomerService{
	
	@Autowired
	private CustomerRepository repository;

	@Override
	public Customer add(Customer customer) {
		return repository.save(customer);
	}

}
