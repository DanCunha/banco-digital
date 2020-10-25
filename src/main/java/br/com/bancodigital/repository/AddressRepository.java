package br.com.bancodigital.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.bancodigital.domain.Address;

public interface AddressRepository extends JpaRepository<Address, Long>{

}
