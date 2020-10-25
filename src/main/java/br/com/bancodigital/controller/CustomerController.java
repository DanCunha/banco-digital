package br.com.bancodigital.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.time.LocalDate;
import java.util.Optional;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.apache.tomcat.util.http.fileupload.impl.SizeLimitExceededException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.bancodigital.domain.Account;
import br.com.bancodigital.domain.Address;
import br.com.bancodigital.domain.Customer;
import br.com.bancodigital.dto.AddressDTO;
import br.com.bancodigital.dto.CustomerDTO;
import br.com.bancodigital.exception.CustomerException;
import br.com.bancodigital.form.AddressForm;
import br.com.bancodigital.form.CustomerForm;
import br.com.bancodigital.form.StatusEnum;
import br.com.bancodigital.repository.AccountRepository;
import br.com.bancodigital.repository.AddressRepository;
import br.com.bancodigital.repository.CustomerRepository;
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
	private CustomerRepository customerRepository;
	
	@Autowired
	private AddressRepository addressRepository;
	
	@Autowired
	private AccountRepository accountRepository;
	
	@PostMapping
	@ApiOperation(value = "Create a new customer", response = CustomerDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully created a new customer"),
            @ApiResponse(code = 400, message = "Validation error")
    })
	public ResponseEntity<Object> add(@Valid @RequestBody CustomerForm form, UriComponentsBuilder uriBuilder) {
		try {
			customerValidation(form);
			Customer entity = customerRepository.save(form.converter());	
			URI uri = uriBuilder.path("/customer/address/{id}").buildAndExpand(entity.getId()).toUri();
			return ResponseEntity.created(uri).body(new CustomerDTO(entity));
		}catch (Exception e) {
			return ResponseEntity.badRequest().body(new ErrorDetail(e.getMessage()));
		}
	}
	
	@PostMapping("/address/{customerId}")
	@Transactional
	@ApiOperation(value = "Create address", response = CustomerDTO.class)
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "Successfully created a new customer"),
			@ApiResponse(code = 400, message = "Validation error") 
	}) 
	public ResponseEntity<Object> addAddress(@NotNull @PathVariable Long customerId, @Valid @RequestBody AddressForm form, UriComponentsBuilder uriBuilder) {

		try { 
			Optional<Customer> customer = customerRepository.findById(customerId);
			if(customer.isEmpty())
				throw new CustomerException("Customer Not found", null);

			Address address = addressRepository.save(form.converter()); 
			customer.get().setAddress(address);
			URI uri = uriBuilder.path("/customer/upload/{id}").buildAndExpand(customer.get().getId()).toUri();
			return ResponseEntity.created(uri).body(new AddressDTO(address));
		}catch (Exception e) { 
			return ResponseEntity.badRequest().body(new ErrorDetail(e.getMessage())); 
		} 
	}
	
	@PostMapping("/upload/{customerId}")
	@Transactional
	@ApiOperation(value = "Upload cpf image", response = CustomerDTO.class)
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "Successfully created a new customer"),
			@ApiResponse(code = 400, message = "Validation error") 
	}) 
	public ResponseEntity<Object> uplaodImage(@NotNull @RequestParam("imageFile") MultipartFile file, @NotNull @PathVariable Long customerId, UriComponentsBuilder uriBuilder) throws IOException, SizeLimitExceededException {	
		try { 
			System.out.println("Original Image Byte Size - " + file.getBytes().length);
			Optional<Customer> customer = customerRepository.findById(customerId);
			if(customer.isEmpty())
				throw new CustomerException("Customer Not found", null);

			customer.get().setCpfImage(compressBytes(file.getBytes()));
			URI uri = uriBuilder.path("/customer/{id}").buildAndExpand(customer.get().getId()).toUri();
			return ResponseEntity.created(uri).body(new CustomerDTO(customer.get()));
		}catch (CustomerException e) { 
			return ResponseEntity.notFound().build(); 
		}catch (Exception e) {
			return ResponseEntity.badRequest().body(new ErrorDetail(e.getMessage())); 
		}
	}
	 
	@GetMapping("/{id}")
	@ApiOperation(value = "Find customer by id", response = CustomerDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Found customer"),
            @ApiResponse(code = 404, message = "Not found")
    })
	public ResponseEntity<CustomerDTO> add(@PathVariable("id") Long id) {
		Optional<Customer> customer = customerRepository.findById(id);
		if(customer.isPresent()) {
			return ResponseEntity.ok(new CustomerDTO(customer.get(), customer.get().getAddress(), decompressBytes(customer.get().getCpfImage())));
		}
		return ResponseEntity.notFound().build();
	}
	
	@PostMapping("/proposal/{customerId}")
	@Async
	@Transactional
	@ApiOperation(value = "Accept proposal to create account", response = CustomerDTO.class)
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "Successfully created a new account"),
			@ApiResponse(code = 400, message = "Validation error") 
	}) 
	public ResponseEntity<Object> proposal(@NotNull @RequestParam("resp") boolean resp, @NotNull @PathVariable Long customerId) {	
		try { 
			Optional<Customer> customer = customerRepository.findById(customerId);
			if(customer.isEmpty())
				throw new CustomerException("Customer Not found", null);
			
			if(resp) {
				Account account = createAccount();
				customer.get().setStatus(StatusEnum.ACCEPTED);
				customer.get().setAccount(account);
			}else {
				customer.get().setStatus(StatusEnum.REJECTED);
			}
			
			return ResponseEntity.ok().build();
		}catch (CustomerException e) { 
			return ResponseEntity.notFound().build(); 
		}catch (Exception e) {
			return ResponseEntity.badRequest().body(new ErrorDetail(e.getMessage())); 
		}
	}
	
	private Account createAccount() {
		int min = 1000;
		int max = 9999;
		int numberAgency = (int)(Math.random()*(max-min+1)+min);
		min = 10000000;
		max = 99999999;
		int numberAccount = (int)(Math.random()*(max-min+1)+min);
		int codeBank = 666;
		
		return Account.builder()
				.agency(numberAgency)
				.numberAccount(numberAccount)
				.codeBank(codeBank)
				.balance(new BigDecimal(0))
				.build();
	}
	
	private void customerValidation(CustomerForm entity) throws CustomerException {
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
	
	public static byte[] compressBytes(byte[] data) {
		Deflater deflater = new Deflater();
		deflater.setInput(data);
		deflater.finish();
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
		byte[] buffer = new byte[1024];
		while (!deflater.finished()) {
			int count = deflater.deflate(buffer);
			outputStream.write(buffer, 0, count);
		}
		try {
			outputStream.close();
		} catch (IOException e) {
		}
		System.out.println("Compressed Image Byte Size - " + outputStream.toByteArray().length);
		return outputStream.toByteArray();
	}
	
	public static byte[] decompressBytes(byte[] data) {
		Inflater inflater = new Inflater();
		inflater.setInput(data);
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
		byte[] buffer = new byte[1024];
		try {
			while (!inflater.finished()) {
				int count = inflater.inflate(buffer);
				outputStream.write(buffer, 0, count);
			}
			outputStream.close();
		} catch (IOException ioe) {
		} catch (DataFormatException e) {
		}
		return outputStream.toByteArray();
	}
}
