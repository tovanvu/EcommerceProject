package com.vti.service.rank;

import com.vti.model.dto.request.RankRequest;
import com.vti.model.dto.response.RankResponse;
import com.vti.model.dto.response.RankResponseDetail;

/**
 * This class is IRankService
 * 
 * @Description: ...
 * @author: KienTT
 * @create_date: Sep 14, 2021
 * @version: 1.0
 * @modifer: KienTT
 * @modifer_date: Sep 14, 2021
 */

public interface IRankService {
	RankResponseDetail getById(Long id);

	RankResponse createRank(RankRequest rankRequest);

	RankResponse updateRank(Long id, RankRequest rankRequest);

	public void deleteRank(Long id);
	
	boolean isExistsByRankName(String rankName);
}
