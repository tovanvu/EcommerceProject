package com.vti.model.entity;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "users")
public class Users {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_seq")
	@SequenceGenerator(name = "users_seq", sequenceName = "users_seq", allocationSize = 1)
	private Long id;

	@Column(name = "access_token", length = 255, nullable = true)
	private String accessToken;

	@CreationTimestamp
	@Column(name = "created")
	private LocalDate created;

	@Column(name = "email", length = 255, nullable = true)
	private String email;

	@Column(name = "name", length = 255, nullable = true)
	private String name;

	@Column(name = "password", length = 255, nullable = true)
	private String passWord;

	@ManyToOne
	@JoinColumn(name = "ROLE_ID")
	private Role role;

	@ManyToOne
	@JoinColumn(name = "RANK_ID")
	private Rank rank;
	
	@OneToMany(mappedBy = "createdBy", fetch = FetchType.LAZY)
	private List<Role> createRoles;
	
	@OneToMany(mappedBy = "updatedBy", fetch = FetchType.LAZY)
	private List<Role> updateRoles;

	@OneToMany(mappedBy = "createdBy", fetch = FetchType.LAZY)
	private List<Rank> createdBy;

	@OneToMany(mappedBy = "updatedBy", fetch = FetchType.LAZY)
	private List<Rank> updatedBy;

	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	private List<Products> product;

}
