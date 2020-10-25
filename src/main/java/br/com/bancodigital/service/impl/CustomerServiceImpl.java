package br.com.bancodigital.service.impl;

import java.time.LocalDate;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.bancodigital.domain.Address;
import br.com.bancodigital.domain.Customer;
import br.com.bancodigital.exception.CustomerException;
import br.com.bancodigital.repository.AddressRepository;
import br.com.bancodigital.repository.CustomerRepository;
import br.com.bancodigital.service.CustomerService;

@Service
public class CustomerServiceImpl implements CustomerService{
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private AddressRepository addressRepository;

	@Override
	public Customer add(Customer customer) throws CustomerException {

		customerValidation(customer);
		
		return customerRepository.save(customer);
	}
	
	private void customerValidation(Customer entity) throws CustomerException {
		if(entity.getDateBirth().compareTo(LocalDate.now().minusYears(18)) >= 0) {
			throw new CustomerException("Menor de 18 anos", null);
		}
		
		Optional<Customer> findByCpf = customerRepository.findByCpf(entity.getCpf());
		if(!findByCpf.isEmpty())
			throw new CustomerException("Cpf duplicado", null);
		
		Optional<Customer> findByEmail = customerRepository.findByEmail(entity.getEmail());
		if(!findByEmail.isEmpty())
			throw new CustomerException("Email duplicado", null);
	}

	@Override
	public Optional<Customer> findById(Long id) {
		return customerRepository.findById(id);
	}

	@Override
	@Transactional
	public Address addAddress(Address entity, Long customerId) throws CustomerException {
		Address address = addressRepository.save(entity);
		Optional<Customer> customer = customerRepository.findById(customerId);
		if(customer.isEmpty())
			throw new CustomerException("Customer Not found", null);
		
		customer.get().setAddress(address);
		return address;
	}

}
