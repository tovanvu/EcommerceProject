package com.vti.config.specification;

import java.time.LocalDate;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;
import com.vti.model.entity.Rank;
import com.vti.model.entity.Role;
import com.vti.model.entity.Users;

public class UserSpecification implements Specification<Users> {
	private static final long serialVersionUID = 1L;

	private String field;
	private String operator;
	private Object value;

	public UserSpecification(String field, String operator, Object value) {
		this.field = field;
		this.operator = operator;
		this.value = value;
	}

	@Override
	public Predicate toPredicate(Root<Users> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
		if (operator.equalsIgnoreCase("LIKE")) {
			if (field.equalsIgnoreCase("roleCode")) {
				Join<Users, Role> join = root.join("role", JoinType.INNER);
				return builder.like(join.get("roleCode"), "%" + value.toString() + "%");
			} else if (field.equalsIgnoreCase("rankName")) {
				Join<Users, Rank> join = root.join("rank", JoinType.INNER);
				return builder.like(join.get("rankName"), "%" + value.toString() + "%");
			}
		}
		if (operator.equalsIgnoreCase("LIKE")) {
			return builder.like(root.get(field), "%" + value.toString() + "%");
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