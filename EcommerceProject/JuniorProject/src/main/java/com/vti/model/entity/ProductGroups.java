package com.vti.model.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import org.hibernate.annotations.CreationTimestamp;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "product_groups")
public class ProductGroups implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_groups_seq")
	@SequenceGenerator(name = "product_groups_seq", sequenceName = "product_groups_seq", allocationSize = 1)
	private Long id;

	@CreationTimestamp
	@Column(name = "CREATED", nullable = true)
	private LocalDate created;

	@Column(name = "GROUP_NAME", length = 255, nullable = true)
	private String name;

	@Column(name = "PRICE", nullable = true)
	private Integer price;

	@OneToMany(mappedBy = "group", fetch = FetchType.LAZY)
	private List<Products> product;

	@OneToMany(mappedBy = "group", fetch = FetchType.LAZY)
	private List<GroupVariants> groupVariant;

}
