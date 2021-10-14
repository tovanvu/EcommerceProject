package com.vti.service.users;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.vti.config.specification.UserSpecification;
import com.vti.exception.Message;
import com.vti.exception.NoDataFoundException;
import com.vti.model.dto.RankDTO;
import com.vti.model.dto.RoleDTO;
import com.vti.model.dto.request.ChangePasswdRequest;
import com.vti.model.dto.request.ForgetPassSendEmailRequest;
import com.vti.model.dto.request.ForgetPasswdRequest;
import com.vti.model.dto.request.RegisterUserRequest;
import com.vti.model.dto.request.UpdateRoleUserRequest;
import com.vti.model.dto.request.UserUpdateRequest;
import com.vti.model.dto.response.SearchUserResponse;
import com.vti.model.dto.response.UserResponse;
import com.vti.model.entity.ResetPasswordToken;
import com.vti.model.entity.Role;
import com.vti.model.entity.Users;
import com.vti.repository.IRedisTokenRepository;
import com.vti.repository.IResetPasswordToken;
import com.vti.repository.IRoleRepository;
import com.vti.repository.IUserRepository;

/**
 * User API service
 * 
 * @author VuTV
 * @Created_on 2021-09-14
 */
@Service
public class UserService implements IUserService {

	@Autowired
	private IUserRepository repository;
	@Autowired
	private MessageSource messageSource;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private IRoleRepository roleRepository;
	@Autowired
	private IRedisTokenRepository tokenRepository;

	private JavaMailSender javaMailSender;

	@Autowired
	private IResetPasswordToken resetPasswordTokenRepo;

	@Value("${resetpassword.expires_in}")
	private int EXPIRES_IN_RESETTOKEN;

	public UserService(JavaMailSender javaMailSender) {
		this.javaMailSender = javaMailSender;
	}

	@Override
	public SearchUserResponse searchUser(Long id, String name, String email, String roleCode, String rankName,
			LocalDate createdFrom, LocalDate createdTo, Pageable pageable) {
		Specification<Users> where = null;
		if (!ObjectUtils.isEmpty(name)) {
			UserSpecification nameSpecification = new UserSpecification("name", "LIKE", name);
			where = Specification.where(nameSpecification);
		}
		if (!ObjectUtils.isEmpty(email)) {
			UserSpecification emailSpecification = new UserSpecification("email", "LIKE", email);
			if (where == null) {
				where = Specification.where(emailSpecification);
			} else {
				where = where.and(emailSpecification);
			}
		}
		if (!ObjectUtils.isEmpty(id)) {
			UserSpecification idSpecification = new UserSpecification("id", "=", id);
			if (where == null) {
				where = Specification.where(idSpecification);
			} else {
				where = where.and(idSpecification);
			}
		}
		if (!ObjectUtils.isEmpty(roleCode)) {
			UserSpecification roleCodeSpecification = new UserSpecification("roleCode", "LIKE", roleCode);
			if (where == null) {
				where = Specification.where(roleCodeSpecification);
			} else {
				where = where.and(roleCodeSpecification);
			}
		}
		if (!ObjectUtils.isEmpty(rankName)) {
			UserSpecification rankNameSpecification = new UserSpecification("rankName", "LIKE", rankName);
			if (where == null) {
				where = Specification.where(rankNameSpecification);
			} else {
				where = where.and(rankNameSpecification);
			}
		}
		if (createdFrom != null && createdTo != null) {
			UserSpecification createdFromSpec = new UserSpecification("created", ">=", createdFrom);
			if (where == null) {
				where = Specification.where(createdFromSpec);
			} else {
				where = where.and(createdFromSpec);
			}
		}
		if (createdFrom != null && createdTo != null) {
			UserSpecification createdToSpec = new UserSpecification("created", "<=", createdTo);
			if (where == null) {
				where = Specification.where(createdToSpec);
			} else {
				where = where.and(createdToSpec);
			}
		}
		if (createdFrom.isAfter(createdTo)) {
			throw new Message("Created from must less than created to");
		}
		Page<Users> entities = repository.findAll(where, pageable);
		List<Users> listUsers = entities.getContent();
		List<UserResponse> listResponses = new ArrayList<>();
		for (Users user : listUsers) {
			RankDTO rankDTO = new RankDTO(user.getRank() == null ? null : user.getRank().getId(),
					user.getRank() == null ? null : user.getRank().getRankName());
			RoleDTO roleDTO = new RoleDTO(user.getRole().getId(), user.getRole().getRoleCode(),
					user.getRole().getName());
			UserResponse response = new UserResponse(user.getId(), user.getName(), user.getEmail(), roleDTO, rankDTO);
			listResponses.add(response);
		}
		int pageCurrent = entities.getNumber();
		int total = entities.getTotalPages();
		SearchUserResponse searchResponse = new SearchUserResponse(listResponses, total, pageCurrent + 1);
		return searchResponse;
	}

	@Override
	public UserResponse userDetail(long id) {
		Users user = repository.findById(id).orElse(null);
		if (user == null) {
			throw new NoDataFoundException(messageSource.getMessage("Get.user.id.notfound",
					new String[] { Long.toString(id) }, Locale.getDefault()));
		}
		RankDTO rankDTO = new RankDTO(user.getRank() == null ? null : user.getRank().getId(),
				user.getRank() == null ? null : user.getRank().getRankName());
		RoleDTO roleDTO = new RoleDTO(user.getRole().getId(), user.getRole().getRoleCode(), user.getRole().getName());
		UserResponse response = new UserResponse(user.getId(), user.getName(), user.getEmail(), roleDTO, rankDTO);
		return response;
	}

	@Transactional
	@Override
	public UserResponse registerUser(RegisterUserRequest register) {
		Role role = roleRepository.findByRoleCode("CUSTOMER");
		if (role == null) {
			throw new NoDataFoundException(
					messageSource.getMessage("Get.role.notfound", new String[] { "CUSTOMER" }, Locale.getDefault()));
		}
		Users user = new Users();

		user.setName(register.getName());
		user.setEmail(register.getEmail());
		user.setPassWord(passwordEncoder.encode(register.getPasswd()));
		user.setRole(role);
		repository.save(user);

		RoleDTO roleDTO = new RoleDTO(user.getRole().getId(), user.getRole().getRoleCode(), user.getRole().getName());
		UserResponse response = new UserResponse(user.getId(), user.getName(), user.getEmail(), roleDTO, null);
		return response;
	}

	@Transactional
	@Modifying
	@Override
	public UserResponse updateUser(long userID, UserUpdateRequest request) {
		Users user = repository.getById(userID);
		if (request.getName() != null) {
			user.setName(request.getName());
		}
		if (request.getEmail() != null) {
			user.setEmail(request.getEmail());
		}
		repository.save(user);
		RankDTO rankDTO = new RankDTO(user.getRank() == null ? null : user.getRank().getId(),
				user.getRank() == null ? null : user.getRank().getRankName());
		RoleDTO roleDTO = new RoleDTO(user.getRole().getId(), user.getRole().getRoleCode(), user.getRole().getName());
		UserResponse response = new UserResponse(user.getId(), user.getName(), user.getEmail(), roleDTO, rankDTO);
		return response;
	}

	@Transactional
	@Modifying
	@Override
	public UserResponse updateRoleUser(long id, UpdateRoleUserRequest request) {
		Users user = repository.findById(id).orElse(null);
		if (user == null) {
			throw new NoDataFoundException(messageSource.getMessage("Get.user.id.notfound",
					new String[] { Long.toString(id) }, Locale.getDefault()));
		}
		Role role = roleRepository.findByRoleCode(request.getRoleCode());
		if (role == null) {
			throw new NoDataFoundException(messageSource.getMessage("Get.role.notfound",
					new String[] { request.getRoleCode() }, Locale.getDefault()));
		}
		if (request.getRoleCode() != null) {
			user.setRole(role);
		}
		repository.save(user);
		RankDTO rankDTO = new RankDTO(user.getRank() == null ? null : user.getRank().getId(),
				user.getRank() == null ? null : user.getRank().getRankName());
		RoleDTO roleDTO = new RoleDTO(user.getRole().getId(), user.getRole().getRoleCode(), user.getRole().getName());
		UserResponse response = new UserResponse(user.getId(), user.getName(), user.getEmail(), roleDTO, rankDTO);
		return response;
	}

	@Override
	public UserResponse currentUser(long userID) {
		Users user = repository.getById(userID);
		RankDTO rankDTO = new RankDTO(user.getRank() == null ? null : user.getRank().getId(),
				user.getRank() == null ? null : user.getRank().getRankName());
		RoleDTO roleDTO = new RoleDTO(user.getRole().getId(), user.getRole().getRoleCode(), user.getRole().getName());
		UserResponse response = new UserResponse(user.getId(), user.getName(), user.getEmail(), roleDTO, rankDTO);
		return response;
	}

	@Transactional
	@Modifying
	@Override
	public UserResponse changePasswd(long userID, ChangePasswdRequest request) {
		Users user = repository.findById(userID).orElse(null);
		if (!passwordEncoder.matches(request.getCurrentPassWd(), user.getPassWord())) {
			throw new Message("Current password incorrect");
		}
		if (passwordEncoder.matches(request.getNewPassWd(), user.getPassWord())) {
			throw new Message("New password must be different current password");
		}
		user.setPassWord(passwordEncoder.encode(request.getNewPassWd()));

		repository.save(user);

		tokenRepository.deleteById(user.getEmail());

		RankDTO rankDTO = new RankDTO(user.getRank() == null ? null : user.getRank().getId(),
				user.getRank() == null ? null : user.getRank().getRankName());
		RoleDTO roleDTO = new RoleDTO(user.getRole().getId(), user.getRole().getRoleCode(), user.getRole().getName());
		UserResponse response = new UserResponse(user.getId(), user.getName(), user.getEmail(), roleDTO, rankDTO);
		return response;
	}

	@Transactional
	@Modifying
	@Override
	public void resetPassword(String token, ForgetPasswdRequest request) throws MessagingException {
		ResetPasswordToken resetPasswordToken = resetPasswordTokenRepo.findByToken(token);
		if (resetPasswordToken == null) {
			throw new NoDataFoundException(messageSource.getMessage("Get.resetpassword.token.notfound",
					new String[] { token }, Locale.getDefault()));
		} else if (resetPasswordToken.getExpiryDate().isBefore(LocalDateTime.now())) {
			throw new NoDataFoundException(messageSource.getMessage("Get.resetpassword.token.expiration",
					new String[] { token }, Locale.getDefault()));
		}

		Users user = resetPasswordToken.getUser();

		user.setPassWord(passwordEncoder.encode(request.getNewPasswd()));
		repository.save(user);

		String Subject = "Ecommerce-VTI: Password Changed Successful!";
		String from = "ecommerce.vti@gmail.com";

		MimeMessage message = javaMailSender.createMimeMessage();

		MimeMessageHelper helper = new MimeMessageHelper(message, true);

		String mailContent = " <h3>Password Changed Successful!</h3> ";
		mailContent += "<p>Hi   " + user.getName() + ",</p>";
		mailContent += "<p>Your password at ecommerce.vti was recently changed. If you are unable to do this, please contact an administrator. </p>";
		mailContent += "<p>Now, your password: <b>" + request.getNewPasswd() + "</b></p>";
		mailContent += "<p>Click <a href='http://localhost:8080/api/v1/login'> http://localhost:8080/api/v1/login </a> to Login!</p>";
		mailContent += "<p>Regards,</p>";
		mailContent += "<p>The Ecommerce-VTI team ,</p>";
		mailContent += "<hr><img src='https://i.stack.imgur.com/1HllQ.png'/>";

		helper.setTo(user.getEmail());
		helper.setSubject(Subject);
		helper.setText(mailContent, true);
		helper.setFrom(from);

		javaMailSender.send(message);
	}

	@Override
	@Modifying
	@Transactional
	public void forgetPassword(ForgetPassSendEmailRequest emailrequest) throws MessagingException {
		/**
		 * find user by email
		 */
		Users user = repository.findByEmail(emailrequest.getEmail());
		if (user == null) {
			throw new NoDataFoundException(messageSource.getMessage("Get.email.notfound",
					new String[] { emailrequest.getEmail() }, Locale.getDefault()));
		}
		/**
		 * Delete token if exit
		 */
		resetPasswordTokenRepo.deleteByUserId(user.getId());
		/**
		 * create new reset password token
		 */
		final String newTokenResetpass = UUID.randomUUID().toString();

		LocalDateTime expiryDate = LocalDateTime.now().plusSeconds(this.EXPIRES_IN_RESETTOKEN);

		ResetPasswordToken resetPasswordToken = new ResetPasswordToken(newTokenResetpass, user, expiryDate);

		resetPasswordTokenRepo.save(resetPasswordToken);

		String Subject = "Ecommerce-VTI: Reset your Ecommerce-VTI password";
		String from = "ecommerce.vti@gmail.com";

		MimeMessage message = javaMailSender.createMimeMessage();

		MimeMessageHelper helper = new MimeMessageHelper(message, true);

		String mailContent = " <h3>Reset your Ecommerce-VTI password!</h3> ";
		mailContent += "<p>Hi   " + user.getName() + ",</p>";
		mailContent += "<p>We heard that you lost your Ecommerce-VTI password. Sorry about that! </p>";
		mailContent += "<p>But don’t worry! You can use the following button to reset your password: </p>";
		mailContent += "<p>" + "<a href='http://localhost:8080/api/v1/password_reset/" + newTokenResetpass + "'> "
				+ " <button class=\"btn btn-primary\"> <p>Click to reset your password</p> <hr><img src='https://icon-library.com/images/reset-icon/reset-icon-13.jpg' width='10%'/> </button> "
				+ "</a>" + "</p>";
		mailContent += "<p>If you don’t use this link within 1 hours, it will expire. To get a new password reset link, visit: </p>";
		mailContent += "<p>Click <a href='http://localhost:8080/api/v1/password_reset'> http://localhost:8080/api/v1/password_reset </a>  to ResetPassword again!</p>";
		mailContent += "<p>Regards,</p>";
		mailContent += "<p>The Ecommerce-VTI team ,</p>";

		helper.setTo(emailrequest.getEmail());
		helper.setSubject(Subject);
		helper.setText(mailContent, true);
		helper.setFrom(from);

		javaMailSender.send(message);
	}

}
