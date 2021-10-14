package com.vti.service.products;

import java.time.LocalDate;
import org.springframework.data.domain.Pageable;
import com.vti.model.dto.request.ProductUpdateRequest;
import com.vti.model.dto.request.ProductsRequest;
import com.vti.model.dto.response.ProductsRespone;
import com.vti.model.dto.response.SearchProductRespone;


public interface IProductsService {
	public SearchProductRespone getAll(Pageable pageable, String name, LocalDate createdFrom, LocalDate createdTo, String group_name, Long id);

	public ProductsRespone AddProducts(ProductsRequest dtoProducts);

	public ProductsRespone GetProductsById(Long id);

	public ProductsRespone updateProduct(Long id, ProductUpdateRequest productsRequest);
}
