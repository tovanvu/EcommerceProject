package com.vti.controller;

import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vti.dto.AccountDto;
import com.vti.entity.Account;
import com.vti.form.AccountFormForCreating;
import com.vti.form.AccountFormForUpdating;
import com.vti.service.IAccountService;

@RestController
@RequestMapping(value = "api/v1/accounts")
@CrossOrigin("*")
public class AccountController {
	@Autowired
	private IAccountService accountService;

	@GetMapping
	public ResponseEntity<?> getAllAccounts(Pageable pageable, @RequestParam(required = false) String search) {
		Page<Account> entities = accountService.getAllAccounts(pageable, search);
		Page<AccountDto> pageDto = entities.map(new Function<Account, AccountDto>() {
			@Override
			public AccountDto apply(Account acc) {
				AccountDto accDto = new AccountDto(acc.getId(), acc.getEmail(), acc.getUsername(), acc.getFullname(),
						acc.getDepartment().getName(), acc.getPosition().getName().toString(), acc.getCreateDate());
				return accDto;
			}
		});
		return new ResponseEntity<>(pageDto, HttpStatus.OK);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<?> getAccountByID(@PathVariable(name = "id") short id) {
		Account account = accountService.getAccountByID(id);
		AccountDto dto = new AccountDto(account.getId(), account.getEmail(), account.getUsername(),
				account.getFullname(), account.getDepartment().getName(), account.getPosition().getName().toString(),
				account.getCreateDate());
		return new ResponseEntity<AccountDto>(dto, HttpStatus.OK);
	}

	@PostMapping()
	public ResponseEntity<?> createAccount(@RequestBody AccountFormForCreating acc) {
		accountService.createAccount(acc);
		return new ResponseEntity<String>("Create successfully!", HttpStatus.CREATED);
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<?> updateAccount(@PathVariable(name = "id") short id,
			@RequestBody AccountFormForUpdating acc) {
		accountService.updateAccount(id, acc);
		return new ResponseEntity<String>("Update successfully!", HttpStatus.OK);
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<?> deleteAccount(@PathVariable(name = "id") short id) {
		accountService.deleteAccount(id);
		return new ResponseEntity<String>("Delete successfully!", HttpStatus.OK);
	}

	@GetMapping(value = "/UsernameExists/{name}")
	public ResponseEntity<?> existsByUsername(@PathVariable(name = "name") String name) {
		return new ResponseEntity<>(accountService.existsByUsername(name), HttpStatus.OK);
	}

	@GetMapping(value = "/EmailExists/{email}")
	public ResponseEntity<?> existsByEmail(@PathVariable(name = "email") String email) {
		return new ResponseEntity<>(accountService.existsByEmail(email), HttpStatus.OK);
	}
}
