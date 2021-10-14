package com.vti.model.entity;

import java.io.Serializable;
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

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "group_variants")
public class GroupVariants implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "group_variants_seq")
	@SequenceGenerator(name = "group_variants_seq", sequenceName = "group_variants_seq", allocationSize = 1)
	private Long id;

	@Column(name = "VARIANT_NAME", length = 255, nullable = true)
	private String variantName;

	@ManyToOne
	@JoinColumn(name = "GROUP_ID")
	private ProductGroups group;

	@OneToMany(mappedBy = "groupVariant", fetch = FetchType.LAZY)
	private List<OrderItems> orderItems;

}
