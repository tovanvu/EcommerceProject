/**
 * 
 */
package com.vti.service.products;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.vti.model.entity.Products;
import com.vti.repository.IProductsRepository;
import com.vti.ultils.ExcelHelper;

/**
 * This class is Excel Service
 * 
 * @Description: ...
 * @author: KienTT
 * @create_date: Sep 27, 2021
 * @version: 1.0
 * @modifer: KienTT
 * @modifer_date: Sep 27, 2021
 */
@Service
public class ExcelService {
	@Autowired
	IProductsRepository repository;

	public void save(MultipartFile file) {
		try {
			List<Products> products = ExcelHelper.excelToTutorials(file.getInputStream());
			repository.saveAll(products);
		} catch (IOException e) {
			throw new RuntimeException("fail to store excel data: " + e.getMessage());
		}
	}
}
