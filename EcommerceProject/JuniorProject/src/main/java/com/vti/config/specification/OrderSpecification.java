package com.vti.config.specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.vti.model.entity.GroupVariants;
import com.vti.model.entity.OrderItems;
import com.vti.model.entity.Orders;
import com.vti.model.entity.Products;

public class OrderSpecification implements Specification<Orders> {
	private static final long serialVersionUID = 1L;

	private String field;
	private String operator;
	private Object value;

	public OrderSpecification(String field, String operator, Object value) {
		this.field = field;
		this.operator = operator;
		this.value = value;
	}

	@Override
	public Predicate toPredicate(Root<Orders> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
		if (operator.equalsIgnoreCase("LIKE")) {
			if (field.equalsIgnoreCase("productName")) {
				Join<Orders, OrderItems> join1 = root.join("orderItems", JoinType.INNER);
				Join<OrderItems, Products> join2 = join1.join("product", JoinType.INNER);
				return builder.like(join2.get("productName"), "%" + value.toString() + "%");
			} else if (field.equalsIgnoreCase("variantName")) {
				Join<Orders, OrderItems> join1 = root.join("orderItems", JoinType.INNER);
				Join<OrderItems, GroupVariants> join2 = join1.join("groupVariant", JoinType.INNER);
				return builder.like(join2.get("variantName"), "%" + value.toString() + "%");
			}
		}
		if (operator.equalsIgnoreCase("LIKE")) {
			return builder.like(root.get(field), "%" + value.toString() + "%");
		}
		if (operator.equalsIgnoreCase("=")) {
			return builder.equal(root.get(field), value);
		}

		if (operator.equalsIgnoreCase("==")) {
			if (field.equalsIgnoreCase("totalPrice")) {
				return builder.equal(root.get("totalPrice"), value);
			}
		}
		if (operator.equalsIgnoreCase(">=")) {
			if (field.equalsIgnoreCase("totalPrice"))
				if (value instanceof String) {
					return builder.greaterThanOrEqualTo(root.get("totalPrice"), (String) value);
				}
		}
		if (operator.equalsIgnoreCase("<=")) {
			if (field.equalsIgnoreCase("totalPrice"))
				if (value instanceof String) {
					return builder.lessThanOrEqualTo(root.get("totalPrice"), (String) value);
				}
		}
		if (operator.equalsIgnoreCase(">")) {
			if (field.equalsIgnoreCase("totalPrice"))
				if (value instanceof String) {
					return builder.greaterThan(root.get("totalPrice"), (String) value);
				}
		}
		if (operator.equalsIgnoreCase("<")) {
			if (field.equalsIgnoreCase("totalPrice"))
				if (value instanceof String) {
					return builder.lessThan(root.get("totalPrice"), (String) value);
				}
		}
		return null;
	}
}