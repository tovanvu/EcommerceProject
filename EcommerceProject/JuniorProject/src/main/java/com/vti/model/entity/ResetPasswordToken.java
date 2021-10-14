package com.vti.model.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "RESET_PASSWORD")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResetPasswordToken implements Serializable {

	/**
	 * 
	 * @author PTrXuan Created on Sep 22, 2021
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "reset_password_seq")
	@SequenceGenerator(name = "reset_password_seq", sequenceName = "reset_password_seq", allocationSize = 1)
	@Column(name = "id", unique = true, nullable = false)
	private long id;

	@Column(name = "token", length = 255, unique = true)
	private String token;

	@OneToOne(targetEntity = Users.class, fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id")
	private Users user;

	@Column(name = "EXPIRYTIME")
	private LocalDateTime expiryDate;

	public ResetPasswordToken(String token, Users user, LocalDateTime expiryDate) {
		super();
		this.token = token;
		this.user = user;
		this.expiryDate = expiryDate;
	}

}
