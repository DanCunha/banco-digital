package br.com.bancodigital.controller;

import java.net.URI;
import java.util.Optional;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.bancodigital.domain.Address;
import br.com.bancodigital.domain.Customer;
import br.com.bancodigital.dto.AddressDTO;
import br.com.bancodigital.dto.CustomerDTO;
import br.com.bancodigital.form.AddressForm;
import br.com.bancodigital.form.CustomerForm;
import br.com.bancodigital.service.CustomerService;
import br.com.bancodigital.validation.ErrorDetail;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value = "Customer Controller")
@RestController()
@RequestMapping(value = "/customer")
public class CustomerController {

	@Autowired
	private CustomerService service;
	
	@PostMapping
	@ApiOperation(value = "Create a new customer", response = CustomerDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully created a new customer"),
            @ApiResponse(code = 400, message = "Validation error")
    }
    )
	public ResponseEntity<Object> add(@Valid @RequestBody CustomerForm form, UriComponentsBuilder uriBuilder) {

		try {
			Customer entity = service.add(form.converter());	
			URI uri = uriBuilder.path("/customer/address/{id}").buildAndExpand(entity.getId()).toUri();
			return ResponseEntity.created(uri).body(new CustomerDTO(entity));
		}catch (Exception e) {
			return ResponseEntity.badRequest().body(new ErrorDetail(e.getMessage()));
		}
	}
	

	@PostMapping("/address/{customerId}")
	@ApiOperation(value = "Create address", response = CustomerDTO.class)
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "Successfully created a new customer"),
			@ApiResponse(code = 400, message = "Validation error") } ) public
	ResponseEntity<Object> addAddress(@NotNull @PathVariable Long customerId, @Valid @RequestBody AddressForm form, UriComponentsBuilder uriBuilder) {

		try { 
			Address entity = service.addAddress(form.converter(), customerId); 
			URI uri = uriBuilder.path("/customer/{id}").buildAndExpand(entity.getId()).toUri();
			return ResponseEntity.created(uri).body(new AddressDTO(entity));
		}catch (Exception e) { 
			return ResponseEntity.badRequest().body(new ErrorDetail(e.getMessage())); 
		} 
	}
	 
	
	@GetMapping("/{id}")
	@ApiOperation(value = "Find customer by id", response = CustomerDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Found customer"),
            @ApiResponse(code = 404, message = "Not found")
    }
    )
	public ResponseEntity<CustomerDTO> add(@PathVariable("id") Long id) {
		Optional<Customer> customer = service.findById(id);
		if(customer.isPresent()) {
			return ResponseEntity.ok(new CustomerDTO(customer.get(), customer.get().getAddress()));
		}
		return ResponseEntity.notFound().build();
	}
}
