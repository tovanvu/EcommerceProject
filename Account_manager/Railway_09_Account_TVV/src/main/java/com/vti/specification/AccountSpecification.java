package com.vti.specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.vti.entity.Account;

public class AccountSpecification implements Specification<Account> {
	private String field;
	private String operator;
	private Object value;

	public AccountSpecification(String field, String operator, Object value) {
		this.field = field;
		this.operator = operator;
		this.value = value;
	}

	@Override
	public Predicate toPredicate(Root<Account> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
		if (operator.equalsIgnoreCase("LIKE")) {
			if (field.equalsIgnoreCase("department.name")) {
				return builder.like(root.get("department").get("name"), "%" + value.toString() + "%");			
			} else {
				return builder.like(root.get(field), "%" + value.toString() + "%");
			}
		}
		return null;
	}

}
