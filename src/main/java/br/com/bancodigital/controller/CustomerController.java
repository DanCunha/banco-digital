package br.com.bancodigital.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.bancodigital.domain.Customer;
import br.com.bancodigital.dto.CustomerDTO;
import br.com.bancodigital.service.CustomerService;

//@Api(value = "Poi Controller")
@RestController()
@RequestMapping(value = "/poi")
@CrossOrigin
public class CustomerController {

	@Autowired
	private CustomerService service;
	
	@PostMapping
	public ResponseEntity<CustomerDTO> add(@RequestBody @Validated CustomerDTO dto, UriComponentsBuilder uriBuilder) {
//		Customer entity;
//		CustomerDTO resultDTO;
//		= service.add(entity);
//		
//		URI uri = uriBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();
//		return ResponseEntity.created(uri).body(new TopicoDTO(topico));
	}
}
