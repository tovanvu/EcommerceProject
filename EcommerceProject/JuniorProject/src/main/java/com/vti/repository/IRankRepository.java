package com.vti.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import com.vti.model.entity.Rank;

public interface IRankRepository extends JpaRepository<Rank, Long>, JpaSpecificationExecutor<Rank> {
	public Rank findByRankName(String rankName);

}
