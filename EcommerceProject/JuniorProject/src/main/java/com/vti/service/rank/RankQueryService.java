package com.vti.service.rank;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.vti.exception.Message;
import com.vti.model.dto.response.RankResponse;
import com.vti.model.dto.response.RankResponsePageable;
import com.vti.model.entity.Rank;
import com.vti.repository.IRankRepository;

/**
 * This class is RankQueryService
 * 
 * @Description: to search rank by pageable
 * @author: KienTT
 * @create_date: Sep 14, 2021
 * @version: 1.0
 * @modifer: KienTT
 * @modifer_date: Sep 14, 2021
 */
@Service
public class RankQueryService {
	@Autowired
	IRankRepository rankRepository;

	public RankResponsePageable search(Long id, String rank_name, LocalDate created_from, LocalDate created_to,
			LocalDate updated_from, LocalDate updated_to, String email_user_created, String email_user_updated,
			int page, int limit) {

		Pageable pageable = PageRequest.of(page - 1, limit, Sort.by("id"));

		Specification<Rank> where = SpecificationBuilder.buildWhere(id, rank_name, created_from, created_to,
				updated_from, updated_to, email_user_created, email_user_updated, page, limit);

		Page<Rank> pageEntity = rankRepository.findAll(where, pageable);

		List<Rank> listRanks = pageEntity.getContent();
		List<RankResponse> listRanksResponse = new ArrayList<>();

		for (Rank rank : listRanks) {
			listRanksResponse.add(rank.toRankResponse());
		}
		if (created_from.isAfter(created_to)) {
			throw new Message("Created from must less than created to");
		}
		if (updated_from.isAfter(updated_to)) {
			throw new Message("Updated from must less than Updated to");
		}
		int pageCurrent = pageEntity.getNumber();
		int totalPage = pageEntity.getTotalPages();

		RankResponsePageable rankResponsePageable = new RankResponsePageable(listRanksResponse, totalPage,
				pageCurrent + 1);

		return rankResponsePageable;

	}
}
