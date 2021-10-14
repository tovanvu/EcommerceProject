/**
 * 
 */
package com.vti.service.rank;

import java.time.LocalDate;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.vti.model.entity.Rank;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * This class is RankSpecification
 * 
 * @Description: ...
 * @author: KienTT
 * @create_date: Sep 14, 2021
 * @version: 1.0
 * @modifer: KienTT
 * @modifer_date: Sep 14, 2021
 */
@NoArgsConstructor
@AllArgsConstructor
public class RankSpecification implements Specification<Rank> {

	private static final long serialVersionUID = 1L;

	private String field;
	private String operator;
	private Object value;

	@Override
	public Predicate toPredicate(Root<Rank> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
		if (operator.equalsIgnoreCase("=")) {
			return criteriaBuilder.equal(root.get(field), value);
		}

		if (operator.equalsIgnoreCase("LIKE")) {
//			rank name
			if (field.equalsIgnoreCase("rankName")) {
				return criteriaBuilder.like(criteriaBuilder.upper(root.get("rankName")),
						"%" + value.toString().toUpperCase() + "%");
			}

//			email user created
			if (field.equalsIgnoreCase("createdBy.email")) {
				return criteriaBuilder.like(criteriaBuilder.upper(root.get("createdBy").get("email")),
						"%" + value.toString().toUpperCase() + "%");
			}
//			email user updated
			if (field.equalsIgnoreCase("updatedBy.email")) {
				return criteriaBuilder.like(criteriaBuilder.upper(root.get("updatedBy").get("email")),
						"%" + value.toString().toUpperCase() + "%");
			}

		}

//		so sanh dieu kien ngay thang >=, <= (create_at, update_at..)
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
