package com.vti.service.productsGroup;

import org.springframework.data.domain.Pageable;

import com.vti.model.dto.request.GroupUpdateRequest;
import com.vti.model.dto.request.ProductGroupsRequest;
import com.vti.model.dto.response.ProductGroupRespone;
import com.vti.model.dto.response.SearchGroupResponse;
import com.vti.model.entity.ProductGroups;

public interface IProductsGroupService {
	public ProductGroups getProductsGroupById(Long id);

	public SearchGroupResponse GetAllGroup(Pageable page);

	public ProductGroupRespone AddGroup(ProductGroupsRequest groupsrequest);

	public ProductGroupRespone GetGroupById(Long id);

	public ProductGroupRespone UpdateGroup(Long id, GroupUpdateRequest groupsRequest);

}
