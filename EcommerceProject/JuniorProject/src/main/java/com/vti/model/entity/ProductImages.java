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
@Table(name = "product_images")
public class ProductImages implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_images_seq")
	@SequenceGenerator(name = "product_images_seq", sequenceName = "product_images_seq", allocationSize = 1)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "PRODUCT_ID")
	private Products product;

	@Column(name = "PATH", length = 255, nullable = false)
	private String path;

}
