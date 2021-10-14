package com.vti.service.users;

import java.time.LocalDate;

import javax.mail.MessagingException;

import org.springframework.data.domain.Pageable;

import com.vti.model.dto.request.ChangePasswdRequest;
import com.vti.model.dto.request.ForgetPassSendEmailRequest;
import com.vti.model.dto.request.ForgetPasswdRequest;
import com.vti.model.dto.request.RegisterUserRequest;
import com.vti.model.dto.request.UpdateRoleUserRequest;
import com.vti.model.dto.request.UserUpdateRequest;
import com.vti.model.dto.response.SearchUserResponse;
import com.vti.model.dto.response.UserResponse;

/**
 * IUser service
 * 
 * @author VuTV Created on 2021-09-14
 */
public interface IUserService {
	public SearchUserResponse searchUser(Long id, String name, String email, String roleCode, String rankName,
			LocalDate createdFrom, LocalDate createdTo, Pageable pageable);

	public UserResponse userDetail(long id);

	public UserResponse registerUser(RegisterUserRequest register);

	public UserResponse updateUser(long userID, UserUpdateRequest request);

	public UserResponse updateRoleUser(long id, UpdateRoleUserRequest request);

	public UserResponse currentUser(long userID);

	public UserResponse changePasswd(long userID, ChangePasswdRequest request);

	public void forgetPassword(ForgetPassSendEmailRequest emailrequest) throws MessagingException;

	public void resetPassword(String token, ForgetPasswdRequest request) throws MessagingException;
}
