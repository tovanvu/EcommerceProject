package com.vti.model.entity;

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
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "orders", catalog = "Edu-DB")
public class Orders {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "orders_seq")
	@SequenceGenerator(name = "orders_seq", sequenceName = "orders_seq", allocationSize = 1)
	private Long id;

	@Column(name = "ADDRESS", length = 255, nullable = true)
	private String address;

	@Column(name = "CITY", length = 255, nullable = true)
	private String city;

	@Column(name = "\"COMMENT\"", length = 255, nullable = true)
	private String comment;

	@CreationTimestamp
	@Column(name = "CREATED", nullable = false)
	private LocalDate created;

	@Column(name = "NAME", length = 255, nullable = true)
	private String name;

	@Column(name = "STATUS", length = 255, nullable = true)
	private String status;

	@Column(name = "TOTAL_PRICE", nullable = true)
	private long totalPrice;

	@Column(name = "TYPE", length = 255, nullable = true)
	private String type;

	@Column(name = "ZIP", length = 255, nullable = true)
	private String zip;

	@OneToMany(mappedBy = "order", fetch = FetchType.LAZY)
	private List<OrderItems> orderItems;
}
