/**
 * 
 */
package com.vti.service.rank;

import java.time.LocalDate;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.ObjectUtils;

import com.vti.model.entity.Rank;

/**
 * This class is SpecificationBuilder
 * 
 * @Description: ...
 * @author: KienTT
 * @create_date: Sep 14, 2021
 * @version: 1.0
 * @modifer: KienTT
 * @modifer_date: Sep 14, 2021
 */

public class SpecificationBuilder {
	public static Specification<Rank> buildWhere(Long id, String rank_name, LocalDate created_from,
			LocalDate created_to, LocalDate updated_from, LocalDate updated_to, String email_user_created,
			String email_user_updated, int page, int limit) {

		Specification<Rank> searchQuery = null;

		if (!ObjectUtils.isEmpty(rank_name)) {
			RankSpecification nameSpec = new RankSpecification("rankName", "LIKE", rank_name);
			searchQuery = Specification.where(nameSpec);
		}

		if (!ObjectUtils.isEmpty(id)) {
			Specification<Rank> idSpec = new RankSpecification("id", "=", id);
			if (searchQuery == null) {
				searchQuery = Specification.where(idSpec);
			} else {
				searchQuery = searchQuery.and(idSpec);
			}
		}

		if (!ObjectUtils.isEmpty(email_user_created)) {
			RankSpecification emailUserCreated = new RankSpecification("createdBy.email", "LIKE", email_user_created);
			if (searchQuery == null) {
				searchQuery = Specification.where(emailUserCreated);
			} else {
				searchQuery = searchQuery.and(emailUserCreated);
			}
		}

		if (!ObjectUtils.isEmpty(email_user_updated)) {
			RankSpecification emailUserUpdated = new RankSpecification("updatedBy.email", "LIKE", email_user_updated);
			if (searchQuery == null) {
				searchQuery = Specification.where(emailUserUpdated);
			} else {
				searchQuery = searchQuery.and(emailUserUpdated);
			}
		}

//		filter theo ngay tao (createdAt)
		if (created_from != null && created_to != null) {
			RankSpecification createdFromSpec = new RankSpecification("createdAt", ">=", created_from);
			if (searchQuery == null) {
				searchQuery = Specification.where(createdFromSpec);
			} else {
				searchQuery = searchQuery.and(createdFromSpec);
			}
		}

		if (created_from != null && created_to != null) {
			RankSpecification createdToSpec = new RankSpecification("createdAt", "<=", created_to);
			if (searchQuery == null) {
				searchQuery = Specification.where(createdToSpec);
			} else {
				searchQuery = searchQuery.and(createdToSpec);
			}
		}

//		filter theo ngay tao (updatedAt)
		if (updated_from != null && updated_to != null) {
			RankSpecification updatedFromSpec = new RankSpecification("updatedAt", ">=", updated_from);
			if (searchQuery == null) {
				searchQuery = Specification.where(updatedFromSpec);
			} else {
				searchQuery = searchQuery.and(updatedFromSpec);
			}
		}

		if (updated_from != null && updated_to != null) {
			RankSpecification updatedToSpec = new RankSpecification("updatedAt", "<=", updated_to);
			if (searchQuery == null) {
				searchQuery = Specification.where(updatedToSpec);
			} else {
				searchQuery = searchQuery.and(updatedToSpec);
			}
		}

		return searchQuery;

	}
}
