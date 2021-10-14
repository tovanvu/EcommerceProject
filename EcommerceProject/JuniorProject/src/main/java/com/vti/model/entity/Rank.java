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

import com.vti.model.dto.RankDTO;
import com.vti.model.dto.response.RankResponse;
import com.vti.model.dto.response.RankResponseDetail;
import com.vti.model.dto.response.UserResponse;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "rank")
public class Rank {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "rank_seq")
	@SequenceGenerator(name = "rank_seq", sequenceName = "rank_seq", allocationSize = 1)
	private Long id;

	@Column(name = "NAME")
	private String rankName;

	@CreationTimestamp
	@Column(name = "CREATED_AT")
	private LocalDate createdAt;

	@CreationTimestamp
	@Column(name = "UPDATED_AT")
	private LocalDate updatedAt;

	@ManyToOne
	@JoinColumn(name = "CREATED_BY", nullable = false)
	private Users createdBy;

	@ManyToOne
	@JoinColumn(name = "UPDATED_BY", nullable = false)
	private Users updatedBy;

	@OneToMany(mappedBy = "rank", fetch = FetchType.LAZY)
	private List<Users> users;

	public RankDTO toRankDTO() {
		return new RankDTO(id, rankName);
	}

	public RankResponse toRankResponse() {
		return new RankResponse(id, rankName, createdAt, updatedAt, createdBy == null ? null : createdBy.getId(),
				createdBy == null ? null : createdBy.getName(), updatedBy == null ? null : updatedBy.getId(),
				updatedBy == null ? null : updatedBy.getName());

	}

	public RankResponseDetail toRankResponseDetail() {
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

		return new RankResponseDetail(id, rankName, createdAt, updatedAt, createdByResponse, updatedByResponse);

	}

}