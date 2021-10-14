package com.vti.service.productsGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import com.vti.model.dto.GroupsVariantsDTO;
import com.vti.model.dto.request.GroupUpdateRequest;
import com.vti.model.dto.request.GroupsVariantsRequest;
import com.vti.model.dto.request.ProductGroupsRequest;
import com.vti.model.dto.response.ProductGroupRespone;
import com.vti.model.dto.response.SearchGroupResponse;
import com.vti.model.entity.GroupVariants;
import com.vti.model.entity.ProductGroups;
import com.vti.repository.IGroupVariantRepository;
import com.vti.repository.IProductsGroupRepository;
import com.vti.exception.NoDataFoundException;

@Service
public class ProductsGroupService implements IProductsGroupService {
	@Autowired
	private IProductsGroupRepository repository;
	@Autowired
	private IGroupVariantRepository variantRepository;
	@Autowired
	private MessageSource messageSource;
	
	@Override
	public ProductGroups getProductsGroupById(Long id) {
		// TODO Auto-generated method stub
		return repository.getById(id);
	}

	@Override
	public SearchGroupResponse GetAllGroup(Pageable pageable) {
		Page<ProductGroups> entities = repository.findAll(pageable);
		List<ProductGroups> listGroups = entities.getContent();
		List<ProductGroupRespone> listGroupDto = new ArrayList<>();
		for (ProductGroups listGroup : listGroups) {
			List<GroupVariants> listVariants = variantRepository.findByGroup(listGroup);
			List<GroupsVariantsDTO> listVariantsDTOs = new ArrayList<>();
			for (GroupVariants listVariant : listVariants) {
				GroupsVariantsDTO variantsDTO = new GroupsVariantsDTO();
				variantsDTO.setId(listVariant.getId());
				variantsDTO.setVariantName(listVariant.getVariantName());
				listVariantsDTOs.add(variantsDTO);
			}
			ProductGroupRespone groupRespone = new ProductGroupRespone(listGroup.getId(), listGroup.getName(),
					listVariantsDTOs);
			listGroupDto.add(groupRespone);
		}
		int pageCurren = entities.getNumber();
		int totalPage = entities.getTotalPages();
		SearchGroupResponse searchResponse = new SearchGroupResponse(listGroupDto, totalPage, pageCurren + 1);
		return searchResponse;
	}

	@Override
	@Transactional
	public ProductGroupRespone AddGroup(ProductGroupsRequest groupsrequest) {
		ProductGroups productGroups = new ProductGroups();
		productGroups.setName(groupsrequest.getName());
		repository.save(productGroups);

		List<GroupVariants> listVariants = new ArrayList<>();
		List<GroupsVariantsDTO> listVariantsDto = new ArrayList<>();
		List<GroupsVariantsRequest> listVariantRequests = groupsrequest.getVariants();
		for (GroupsVariantsRequest listVariantRequest : listVariantRequests) {
			GroupVariants variant = new GroupVariants();
			variant.setVariantName(listVariantRequest.getVarianName());
			variant.setGroup(productGroups);
			listVariants.add(variant);
		}
		variantRepository.saveAll(listVariants);
		for (GroupVariants variant : listVariants) {
			GroupsVariantsDTO variantsDto = new GroupsVariantsDTO(variant.getId(), variant.getVariantName());
			listVariantsDto.add(variantsDto);
		}
		productGroups.setGroupVariant(listVariants);

		ProductGroupRespone respone = new ProductGroupRespone(productGroups.getId(), productGroups.getName(),
				listVariantsDto);
		return respone;
	}

	@Override
	public ProductGroupRespone GetGroupById(Long id) {
		ProductGroups group = repository.findById(id).orElse(null);
		if (group == null) {
			throw new NoDataFoundException(messageSource.getMessage("Get.group.id.notfound",
					new String[] { Long.toString(id) }, Locale.getDefault()));
		}
		List<GroupVariants> variants = group.getGroupVariant();
		List<GroupsVariantsDTO> variantsDTOs = new ArrayList<>();
		for (GroupVariants variant : variants) {
			GroupsVariantsDTO dto = new GroupsVariantsDTO();
			dto.setId(variant.getId());
			dto.setVariantName(variant.getVariantName());
			variantsDTOs.add(dto);
		}
		ProductGroupRespone respone = new ProductGroupRespone(group.getId(), group.getName(), variantsDTOs);
		return respone;
	}

	@Transactional
	@Modifying
	@Override
	public ProductGroupRespone UpdateGroup(Long id, GroupUpdateRequest groupsRequest) {
		ProductGroups group = repository.findById(id).orElse(null);
		if (group == null) {
			throw new NoDataFoundException(messageSource.getMessage("Get.group.id.notfound",
					new String[] { Long.toString(id) }, Locale.getDefault()));
		}
		List<GroupVariants> listVariants = new ArrayList<>();
		List<GroupsVariantsDTO> listVariantsDto = new ArrayList<>();

		if (groupsRequest.getVariants() == null) {
			List<GroupVariants> listBeforUpdate = variantRepository.findByGroup(group);
			for (GroupVariants groupVariant : listBeforUpdate) {
				GroupsVariantsDTO variantsDto = new GroupsVariantsDTO(groupVariant.getId(),
						groupVariant.getVariantName());
				listVariantsDto.add(variantsDto);
			}
		}

		if (groupsRequest.getVariants() != null) {
			List<GroupVariants> listBeforUpdate = variantRepository.findByGroup(group);
			variantRepository.deleteAll(listBeforUpdate);

			List<GroupsVariantsRequest> listVariantRequests = groupsRequest.getVariants();
			for (GroupsVariantsRequest listVariantRequest : listVariantRequests) {
				GroupVariants variant = new GroupVariants();
				variant.setVariantName(listVariantRequest.getVarianName());
				variant.setGroup(group);
				listVariants.add(variant);
			}
			variantRepository.saveAll(listVariants);
			for (GroupVariants variant : listVariants) {
				GroupsVariantsDTO variantsDto = new GroupsVariantsDTO(variant.getId(), variant.getVariantName());
				listVariantsDto.add(variantsDto);
			}
		}
		if (groupsRequest.getName() != null) {
			group.setName(groupsRequest.getName());
		}
		if (groupsRequest.getVariants() != null) {
			group.setGroupVariant(listVariants);
		}
		repository.save(group);
		ProductGroupRespone respone = new ProductGroupRespone();
		respone.setId(group.getId());
		respone.setGroupName(group.getName());
		respone.setGroupsVariants(listVariantsDto);
		return respone;
	}
}
