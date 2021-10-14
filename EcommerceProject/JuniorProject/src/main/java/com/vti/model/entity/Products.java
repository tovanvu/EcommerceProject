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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "products")
public class Products implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "products_seq")
	@SequenceGenerator(name = "products_seq", sequenceName = "products_seq", allocationSize = 1)
	private Long id;

	@Column(name = "NAME", length = 255, nullable = false)
	private String productName;

	@Column(name = "PRICE", nullable = true)
	private int price;

	@Column(name = "DESCRIPTION", length = 255, nullable = true)
	private String description;

	@CreationTimestamp
	@Column(name = "CREATED", nullable = true)
	private LocalDate created;

	@ManyToOne
	@JoinColumn(name = "GROUP_ID")
	private ProductGroups group;

	@ManyToOne
	@JoinColumn(name = "USER_ID")
	private Users user;

	@OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
	private List<ProductImages> productImges;

	@OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
	private List<OrderItems> orderItems;

}
