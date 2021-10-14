package com.vti.config.specification;

import java.time.LocalDate;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.vti.model.entity.Products;

public class ProductsSpecification implements Specification<Products> {
	private static final long serialVersionUID = 1L;

	private String field;
	private String operator;
	private Object value;

	public ProductsSpecification(String field, String operator, Object value) {
		this.field = field;
		this.operator = operator;
		this.value = value;
	}

	@Override
	public Predicate toPredicate(Root<Products> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
		if (operator.equalsIgnoreCase("LIKE")) {
			if (field.equalsIgnoreCase("group.name")) {
				return builder.like(root.get("group").get("name"), "%" + value.toString() + "%");
			} else {
				return builder.like(root.get(field), "%" + value.toString() + "%");
			}
		}
		if (operator.equalsIgnoreCase("=")) {
			return builder.equal(root.get(field), value);
		}
		if (operator.equalsIgnoreCase(">=")) {
			if (value instanceof LocalDate) {
				return builder.greaterThanOrEqualTo(root.get(field), LocalDate.parse(value.toString()));
			}
		}
		if (operator.equalsIgnoreCase("<=")) {
			if (value instanceof LocalDate) {
				return builder.lessThanOrEqualTo(root.get(field), LocalDate.parse(value.toString()).plusDays(1));
			}
		}
		return null;
	}
}
