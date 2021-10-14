package com.vti.service.rank;

import java.time.LocalDate;
import java.util.Locale;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.vti.exception.NoDataFoundException;
import com.vti.model.dto.request.RankRequest;
import com.vti.model.dto.response.RankResponse;
import com.vti.model.dto.response.RankResponseDetail;
import com.vti.model.entity.Rank;
import com.vti.model.entity.Users;
import com.vti.model.login.LoginUserDetails;
import com.vti.repository.IRankRepository;

/**
 * This class is Rank Service
 * 
 * @Description: ...
 * @author: KienTT
 * @create_date: Sep 15, 2021
 * @version: 1.0
 * @modifer: KienTT
 * @modifer_date: Sep 15, 2021
 */
@Service
public class RankService implements IRankService {

	@Autowired
	IRankRepository rankRepository;

	@Autowired
	private MessageSource messageSource;

	ModelMapper modelMapper = new ModelMapper();

	/**
	 * @Description: get rank by id
	 * @author: KienTT
	 * @create_date: Sep 15, 2021
	 * @version: 1.0
	 * @param id
	 * @return Rank Response Detail
	 */
	@Override
	public RankResponseDetail getById(Long id) {
		Rank rank = rankRepository.findById(id).orElse(null);
		if (rank == null) {
			throw new NoDataFoundException(messageSource.getMessage("Get.rank.id.notfound",
					new String[] { Long.toString(id) }, Locale.getDefault()));
		}
		RankResponseDetail responseDetail = rank.toRankResponseDetail();

		return responseDetail;
	}

	/**
	 * @Description: create rank
	 * @author: KienTT
	 * @create_date: Sep 15, 2021
	 * @version: 1.0
	 * @param name
	 * @return Rank Response
	 */
	@Transactional
	@Override
	public RankResponse createRank(RankRequest rankRequest) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		LoginUserDetails customUserDetails = (LoginUserDetails) authentication.getPrincipal();
		Users u = customUserDetails.getLoginUser();

		Rank newRank = new Rank();
		newRank.setRankName(rankRequest.getName());
		newRank.setCreatedAt(LocalDate.now());
		newRank.setUpdatedAt(null);

		Users user = new Users();
		user.setId(u.getId());
		user.setName(u.getName());

		newRank.setCreatedBy(user);

		rankRepository.save(newRank);

		RankResponse rankOutPut = newRank.toRankResponse();

		return rankOutPut;
	}

	/**
	 * @Description: update rank name by id
	 * @author: KienTT
	 * @create_date: Sep 15, 2021
	 * @version: 1.0
	 * @param rankRequest
	 * @return
	 */
	@Transactional
	@Modifying
	@Override
	public RankResponse updateRank(Long id, RankRequest rankRequest) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		LoginUserDetails customUserDetails = (LoginUserDetails) authentication.getPrincipal();
		Users u = customUserDetails.getLoginUser();
		Users user = new Users();
		user.setId(u.getId());
		user.setName(u.getName());

		Rank rank = rankRepository.findById(id).orElse(null);
		if (rank == null) {
			throw new NoDataFoundException(messageSource.getMessage("Get.rank.id.notfound",
					new String[] { Long.toString(id) }, Locale.getDefault()));
		}
		rank.setRankName(rankRequest.getName());
		rank.setUpdatedAt(LocalDate.now());

		rank.setUpdatedBy(user);

		rankRepository.save(rank);

		RankResponse rankOutPut = rank.toRankResponse();

		return rankOutPut;
	}

	/**
	 * @Description: delete rank by id
	 * @author: KienTT
	 * @create_date: Sep 15, 2021
	 * @version: 1.0
	 * @param id
	 */
	@Override
	public void deleteRank(Long id) {
		Rank rank = rankRepository.findById(id).orElse(null);
		if (rank == null) {
			throw new NoDataFoundException(messageSource.getMessage("Get.rank.id.notfound",
					new String[] { Long.toString(id) }, Locale.getDefault()));
		}
		rankRepository.deleteById(id);
	}

	/**
	 * @Description: check rank name exist?
	 * @author: KienTT
	 * @create_date: Sep 16, 2021
	 * @version: 1.0
	 * @param rankName
	 * @return
	 */
	@Override
	public boolean isExistsByRankName(String rankName) {
		Rank rank = rankRepository.findByRankName(rankName);

		return rank == null ? false : true;
	}

}
