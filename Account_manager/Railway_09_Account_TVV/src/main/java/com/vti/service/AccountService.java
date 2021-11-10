package com.vti.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.vti.entity.Account;
import com.vti.entity.AccountStatus;
import com.vti.entity.Department;
import com.vti.entity.Position;
import com.vti.entity.RegistrationUserToken;
import com.vti.event.OnSendRegistrationUserConfirmViaEmailEvent;
import com.vti.form.AccountFormForCreating;
import com.vti.form.AccountFormForCreatingRegister;
import com.vti.form.AccountFormForUpdating;
import com.vti.repository.IAccountRepository;
import com.vti.repository.IDepartmentRepository;
import com.vti.repository.IPositionRepository;
import com.vti.repository.RegistrationUserTokenRepository;
import com.vti.specification.AccountSpecification;

@Service
public class AccountService implements IAccountService {
	@Autowired
	private IAccountRepository accountRepository;

	@Autowired
	private IDepartmentRepository departmentRepository;

	@Autowired
	private IPositionRepository possitionRepository;

	@Autowired
	private ApplicationEventPublisher eventPublisher;

	@Autowired
	private RegistrationUserTokenRepository registrationUserTokenRepository;

//	Dùng để mã hóa Password
	@Autowired
	private PasswordEncoder passwordEncoder;

	@SuppressWarnings("deprecation")
	@Override
	public Page<Account> getAllAccounts(Pageable pageable, String search) {
		Specification<Account> where = null;
		if (!StringUtils.isEmpty(search)) {
			AccountSpecification nameSpecification = new AccountSpecification("fullname", "LIKE", search);

			AccountSpecification emailSpecification = new AccountSpecification("email", "LIKE", search);

			AccountSpecification usernameSpecification = new AccountSpecification("username", "LIKE", search);

			AccountSpecification departmentSpecification = new AccountSpecification("department.name", "LIKE", search);

			where = Specification.where(nameSpecification).or(emailSpecification).or(departmentSpecification)
					.or(usernameSpecification);
		}
		return accountRepository.findAll(where, pageable);
	}

	@Override
	public Account getAccountByID(short id) {
		return accountRepository.findById(id).get();
	}

	@Override
	public void createAccount(AccountFormForCreating acc) {
		Account account = new Account();
		Department dep = departmentRepository.getById(acc.getDepartmentId());
		Position pos = possitionRepository.getById(acc.getPositionId());
		account.setEmail(acc.getEmail());
		account.setUsername(acc.getUsername());
		account.setFullname(acc.getFullname());
		account.setDepartment(dep);
		account.setPosition(pos);
		account.setPassword(passwordEncoder.encode(acc.getPassWord()));
		accountRepository.save(account);
	}

	@Override
	public void updateAccount(short id, AccountFormForUpdating acc) {
		Account account1 = accountRepository.getById(id);
		Department department = departmentRepository.getById(acc.getDepartmentId());
		Position position = possitionRepository.getById(acc.getPositionId());
		account1.setFullname(acc.getFullname());
		account1.setDepartment(department);
		account1.setPosition(position);
		accountRepository.save(account1);
	}

	@Override
	public void deleteAccount(short id) {
		accountRepository.deleteById(id);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Account account = accountRepository.findByUsername(username);
		if (account == null) {
			throw new UsernameNotFoundException(username);
		}
		return new User(account.getUsername(), account.getPassword(), AuthorityUtils.createAuthorityList("user"));
	}

	@Override
	public Account getAccountByUsername(String username) {
		return accountRepository.findByUsername(username);
	}

	@Override
	public boolean existsByUsername(String name) {
		return accountRepository.existsByUsername(name);
	}

	@Override
	public boolean existsByEmail(String email) {
		return accountRepository.existsByEmail(email);
	}

	@Override
	public Account getAccountByEmail(String email) {
		// TODO Auto-generated method stub
		return accountRepository.findByEmail(email);
	}

	@Override
	public void createAccountRegister(AccountFormForCreatingRegister form) {
		Account account = new Account();
		Department department = departmentRepository.getById(form.getDepartmentId());
		Position position = possitionRepository.getById(form.getPositionId());
		account.setEmail(form.getEmail());
		account.setUsername(form.getUsername());
		account.setFullname(form.getFullname());
		account.setDepartment(department);
		account.setPosition(position);
		account.setPassword(passwordEncoder.encode(form.getPassword()));
		accountRepository.save(account);

		// create new user registration token
		createNewRegistrationUserToken(account);

		// send email to confirm
		sendConfirmUserRegistrationViaEmail(account.getEmail());

	}

	private void createNewRegistrationUserToken(Account account) {

		// create new token for confirm Registration
		final String newToken = UUID.randomUUID().toString();
		RegistrationUserToken token = new RegistrationUserToken(newToken, account);

		registrationUserTokenRepository.save(token);
	}

	public void sendConfirmUserRegistrationViaEmail(String email) {
		eventPublisher.publishEvent(new OnSendRegistrationUserConfirmViaEmailEvent(email));
	}

	@Override
	public void activeUser(String token) {
		RegistrationUserToken registrationUserToken = registrationUserTokenRepository.findByToken(token);

		Account account = registrationUserToken.getAccount();
		account.setStatus(AccountStatus.ACTIVE);

		accountRepository.save(account);

		// remove Registration User Token
		registrationUserTokenRepository.deleteById(registrationUserToken.getId());

	}

}
