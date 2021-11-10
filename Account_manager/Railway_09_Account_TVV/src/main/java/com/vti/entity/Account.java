package com.vti.entity;

import java.io.Serializable;

import java.time.LocalDate;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Formula;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.vti.entity.Position.PositionName;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Setter
@Getter
@ToString
@Entity
@Table(name = "`Account`", catalog = "TestingSystem")
public class Account implements Serializable {
	private static final long serialVersionUID = 1L;
	@Column(name = "AccountID")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private short id;
	
	@Column(name = "Email", length = 50, nullable = false, unique = true, updatable = false)
	private String email;
	
	@Column(name = "Username", length = 50, nullable = false, unique = true, updatable = false)
	private String username;
	
	@Column(name = "FullName", length = 50, nullable = false)
	private String fullname;
	
	@ManyToOne
	@JoinColumn(name = "DepartmentID", nullable = false)
	private Department department;
	
	@ManyToOne
	@JoinColumn(name = "PositionID", nullable = false)
	private Position position;
	
	@Column(name = "password", length = 800)
	private String password;
	
	@Enumerated(EnumType.ORDINAL)
	@Column(name = "`status`", nullable = false)
	private AccountStatus status = AccountStatus.NOT_ACTIVE;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "`role`", nullable = false)
	private AccountRoles role = AccountRoles.USER;
	
	@Column(name = "PathImage", length = 50, unique = true, updatable = true)
	private String PathImage;
	
	@Column(name = "CreateDate")
	@Temporal(TemporalType.TIMESTAMP)
	@CreationTimestamp
	private Date createDate;

}