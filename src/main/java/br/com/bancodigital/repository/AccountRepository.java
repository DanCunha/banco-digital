package br.com.bancodigital.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.bancodigital.domain.Account;

public interface AccountRepository extends JpaRepository<Account, Long>{

}
