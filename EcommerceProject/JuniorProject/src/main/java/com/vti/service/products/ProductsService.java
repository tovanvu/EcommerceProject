package com.vti.service.products;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import com.vti.model.dto.ProductsGroupsDTO;
import com.vti.config.specification.ProductsSpecification;
import com.vti.model.entity.ProductGroups;
import com.vti.model.entity.Products;
import com.vti.model.entity.Users;
import com.vti.model.login.LoginUserDetails;
import com.vti.repository.IProductsGroupRepository;
import com.vti.repository.IProductsRepository;
import com.vti.exception.Message;
import com.vti.exception.NoDataFoundException;
import com.vti.model.dto.request.ProductUpdateRequest;
import com.vti.model.dto.request.ProductsRequest;
import com.vti.model.dto.response.ProductsRespone;
import com.vti.model.dto.response.SearchProductRespone;

@Service
public class ProductsService implements IProductsService {
	@Autowired
	private IProductsRepository productsRepository;
	@Autowired
	private IProductsGroupRepository ProductsGroupRepository;
	@Autowired
	private MessageSource messageSource;

	@Override
	public SearchProductRespone getAll(Pageable pageable, String name, LocalDate createdFrom, LocalDate createdTo,
			String group_name, Long id) {
		Specification<Products> where = null;
		if (!ObjectUtils.isEmpty(name)) {
			ProductsSpecification nameSpecification = new ProductsSpecification("productName", "LIKE", name);
			where = Specification.where(nameSpecification);
		}
		if (!ObjectUtils.isEmpty(group_name)) {
			ProductsSpecification nameGroupSpecification = new ProductsSpecification("group.name", "LIKE", group_name);
			if (where == null) {
				where = Specification.where(nameGroupSpecification);
			} else {
				where = where.and(nameGroupSpecification);
			}
		}
		if (!ObjectUtils.isEmpty(id)) {
			ProductsSpecification idSpecification = new ProductsSpecification("id", "=", id);
			if (where == null) {
				where = Specification.where(idSpecification);
			} else {
				where = where.and(idSpecification);
			}
		}
		if (createdFrom != null && createdTo != null) {
			ProductsSpecification createdFromSpec = new ProductsSpecification("created", ">=", createdFrom);
			if (where == null) {
				where = Specification.where(createdFromSpec);
			} else {
				where = where.and(createdFromSpec);
			}
		}
		if (createdFrom != null && createdTo != null) {
			ProductsSpecification createdToSpec = new ProductsSpecification("created", "<=", createdTo);
			if (where == null) {
				where = Specification.where(createdToSpec);
			} else {
				where = where.and(createdToSpec);
			}
		}
		if (createdFrom.isAfter(createdTo)) {
			throw new Message("Created from must less than created to");
		}
		Page<Products> entities = productsRepository.findAll(where, pageable);
		List<Products> listProducts = entities.getContent();
		List<ProductsRespone> listRespones = new ArrayList<>();
		for (Products listProduct : listProducts) {
			ProductsRespone respone = new ProductsRespone(listProduct.getId(), listProduct.getProductName(),
					listProduct.getPrice(), listProduct.getDescription(),
					new ProductsGroupsDTO(listProduct.getGroup().getId(), listProduct.getGroup().getCreated(),
							listProduct.getGroup().getName(), listProduct.getGroup().getPrice()));
			listRespones.add(respone);
		}
		int pageCurrent = entities.getNumber();
		int total = entities.getTotalPages();

		SearchProductRespone searchRespone = new SearchProductRespone(listRespones, total, pageCurrent + 1);
		return searchRespone;
	}

	@Override
	@Transactional
	public ProductsRespone AddProducts(ProductsRequest dtoProducts) {
		Products products = new Products();
		ProductGroups product_Groups = ProductsGroupRepository.findById(dtoProducts.getGroupId()).orElse(null);
		if (product_Groups == null) {
			throw new NoDataFoundException(messageSource.getMessage("Get.group.id.notfound",
					new String[] { Long.toString(dtoProducts.getGroupId()) }, Locale.getDefault()));
		}
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		LoginUserDetails customUserDetails = (LoginUserDetails) authentication.getPrincipal();
		Users users = customUserDetails.getLoginUser();
		products.setProductName(dtoProducts.getName());
		products.setPrice(dtoProducts.getPrice());
		products.setDescription(dtoProducts.getDescription());
		products.setGroup(product_Groups);
		products.setUser(users);
		productsRepository.save(products);
		ProductsRespone dto = new ProductsRespone(products.getId(), products.getProductName(), products.getPrice(),
				products.getDescription(),
				new ProductsGroupsDTO(products.getGroup().getId(), products.getGroup().getCreated(),
						products.getGroup().getName(), products.getGroup().getPrice()));
		return dto;
	}

	@Override
	public ProductsRespone GetProductsById(Long id) {
		Products products = productsRepository.findById(id).orElse(null);
		if (products == null) {
			throw new NoDataFoundException(messageSource.getMessage("Get.product.id.notfound",
					new String[] { Long.toString(id) }, Locale.getDefault()));
		}
		ProductsRespone dto = new ProductsRespone(products.getId(), products.getProductName(), products.getPrice(),
				products.getDescription(),
				new ProductsGroupsDTO(products.getGroup().getId(), products.getGroup().getCreated(),
						products.getGroup().getName(), products.getGroup().getPrice()));
		return dto;
	}

	@Transactional
	@Override
	@Modifying
	public ProductsRespone updateProduct(Long id, ProductUpdateRequest productsRequest) {
		Products productsUpdate = productsRepository.findById(id).orElse(null);
		if (productsUpdate == null) {
			throw new NoDataFoundException(messageSource.getMessage("Get.product.id.notfound",
					new String[] { Long.toString(id) }, Locale.getDefault()));
		}
		if (productsRequest.getGroupId() != null) {
			ProductGroups product_Groups = ProductsGroupRepository.findById(productsRequest.getGroupId()).orElse(null);
			if (product_Groups != null) {
				productsUpdate.setGroup(product_Groups);
			} else {
				throw new NoDataFoundException(messageSource.getMessage("Get.group.id.notfound",
						new String[] { Long.toString(productsRequest.getGroupId()) }, Locale.getDefault()));
			}

		}
		if (productsRequest.getName() != null) {
			productsUpdate.setProductName(productsRequest.getName());
		}
		if (productsRequest.getPrice() != 0) {
			productsUpdate.setPrice(productsRequest.getPrice());
		}
		if (productsRequest.getDescription() != null) {
			productsUpdate.setDescription(productsRequest.getDescription());
		}
		productsRepository.save(productsUpdate);
		ProductsRespone dto = new ProductsRespone(productsUpdate.getId(), productsUpdate.getProductName(),
				productsUpdate.getPrice(), productsUpdate.getDescription(),
				new ProductsGroupsDTO(productsUpdate.getGroup().getId(), productsUpdate.getGroup().getCreated(),
						productsUpdate.getGroup().getName(), productsUpdate.getGroup().getPrice()));
		return dto;
	}
}