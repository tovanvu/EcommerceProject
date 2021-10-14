package com.vti.config.specification.Role;

import java.time.LocalDate;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.vti.model.entity.Role;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class RoleSpecification implements Specification<Role> {

	private static final long serialVersionUID = 1L;

	private String field;
	private String operator;
	private Object value;

	@Override
	public Predicate toPredicate(Root<Role> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
		if (operator.equalsIgnoreCase("=")) {
			return criteriaBuilder.equal(root.get(field), value);
		}

		if (operator.equalsIgnoreCase("LIKE")) {
			if (field.equalsIgnoreCase("createdBy.email")) {
				return criteriaBuilder.like(criteriaBuilder.upper(root.get("createdBy").get("email")),
						"%" + value.toString().toUpperCase() + "%");
			}
			if (field.equalsIgnoreCase("updatedBy.email")) {
				return criteriaBuilder.like(criteriaBuilder.upper(root.get("updatedBy").get("email")),
						"%" + value.toString().toUpperCase() + "%");
			}
		}
		if (operator.equalsIgnoreCase("LIKE")) {
			return criteriaBuilder.like(criteriaBuilder.upper(root.get(field)),
					"%" + value.toString().toUpperCase() + "%");
		}
		if (operator.equalsIgnoreCase(">=")) {
			if (value instanceof LocalDate) {
				return criteriaBuilder.greaterThanOrEqualTo(root.get(field), LocalDate.parse(value.toString()));
			}
		}
		if (operator.equalsIgnoreCase("<=")) {
			if (value instanceof LocalDate) {
				return criteriaBuilder.lessThanOrEqualTo(root.get(field),
						LocalDate.parse(value.toString()).plusDays(1));
			}
		}
		return null;
	}

}
