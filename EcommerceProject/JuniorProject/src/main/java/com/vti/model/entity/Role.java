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
import com.vti.model.dto.RoleDTO;
import com.vti.model.dto.Role.Response.RoleResponse;
import com.vti.model.dto.Role.Response.RoleResponseDetail;
import com.vti.model.dto.response.UserResponse;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Data
@Entity
@Table(name = "role")
public class Role {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "role_seq")
	@SequenceGenerator(name = "role_seq", sequenceName = "role_seq", allocationSize = 1)
	private Long id;

	@Column(name = "NAME")
	private String name;

	@Column(name = "ROLE_CODE")
	private String roleCode;

	@CreationTimestamp
	@Column(name = "CREATED_AT")
	private LocalDate createdAt;

	@CreationTimestamp
	@Column(name = "UPDATED_AT")
	private LocalDate updatedAt;

	@ManyToOne
	@JoinColumn(name = "CREATED_BY")
	private Users createdBy;

	@ManyToOne
	@JoinColumn(name = "UPDATED_BY")
	private Users updatedBy;

	@OneToMany(mappedBy = "role", fetch = FetchType.LAZY)
	private List<Users> users;

	public RoleResponse toRoleResponse() {

		return new RoleResponse(id, roleCode, name, createdAt, updatedAt, createdBy == null ? null : createdBy.getId(),
				createdBy == null ? null : createdBy.getName(), updatedBy == null ? null : updatedBy.getId(),
				updatedBy == null ? null : updatedBy.getName());
	}

	public RoleResponseDetail toRoleResponseDetail() {
		UserResponse createdByResponse = null;
		UserResponse updatedByResponse = null;

		if (createdBy != null) {
			createdByResponse = new UserResponse(createdBy.getId(), createdBy.getName(), createdBy.getEmail(),
					createdBy.getRole().toRoleDto(), createdBy.getRank().toRankDTO());
		}

		if (updatedBy != null) {
			updatedByResponse = new UserResponse(updatedBy.getId(), updatedBy.getName(), updatedBy.getEmail(),
					updatedBy.getRole().toRoleDto(), updatedBy.getRank().toRankDTO());

		}

		return new RoleResponseDetail(id, roleCode, name, createdAt, updatedAt, createdByResponse, updatedByResponse);
	}

	public RoleDTO toRoleDto() {

		return new RoleDTO(id, roleCode, name);
	}

	public Role(String name, String roleCode, LocalDate createdAt, LocalDate updatedAt, Users createdBy,
			Users updatedBy) {
		super();
		this.name = name;
		this.roleCode = roleCode;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.createdBy = createdBy;
		this.updatedBy = updatedBy;
	}

}