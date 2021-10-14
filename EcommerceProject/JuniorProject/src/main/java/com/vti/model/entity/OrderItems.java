package com.vti.model.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "order_items")
public class OrderItems implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_items_seq")
	@SequenceGenerator(name = "order_items_seq", sequenceName = "order_items_seq", allocationSize = 1)
	private Long id;

	@Column(name = "QUANTITY")
	private int quantity;

	@ManyToOne
	@JoinColumn(name = "PRODUCT_VARIANT_ID")
	private GroupVariants groupVariant;

	@ManyToOne
	@JoinColumn(name = "ORDER_ID")
	private Orders order;

	@ManyToOne
	@JoinColumn(name = "PRODUCT_ID")
	private Products product;

}