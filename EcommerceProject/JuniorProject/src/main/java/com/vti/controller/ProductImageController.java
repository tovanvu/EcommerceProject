package com.vti.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.vti.model.dto.response.ProductImagesRespone;
import com.vti.model.entity.ProductImages;
import com.vti.model.entity.Products;
import com.vti.repository.IProductsImagesRepository;
import com.vti.repository.IProductsRepository;
import com.vti.service.productImage.IProductImageService;
import com.vti.ultils.FileManager;
import com.vti.exception.NoDataFoundException;

@CrossOrigin("*")
@RestController
@RequestMapping
@Validated
public class ProductImageController {
	@Autowired
	private IProductImageService fileService;
	@Autowired
	private IProductsImagesRepository repository;
	@Autowired
	private IProductsRepository productRepository;
	@Autowired
	private MessageSource messageSource;

	@PostMapping(value = "api/v1/product/{id}/uploadimage")
	public ResponseEntity<?> upLoadImage(@PathVariable(name = "id") Long id,
			@RequestParam(name = "file") MultipartFile image) throws IOException {
		if (!new FileManager().isTypeFileImage(image)) {
			return new ResponseEntity<>("File must be image!", HttpStatus.UNPROCESSABLE_ENTITY);
		}
		ProductImages productImages = fileService.uploadImage(image, id);
		String pathRespone = "/api/v1/product/image/" + productImages.getId();
		ProductImagesRespone respone = new ProductImagesRespone(productImages.getId(), id, pathRespone);
		return new ResponseEntity<>(respone, HttpStatus.OK);
	}

	@GetMapping(value = "api/v1/product/image/{id}")
	public ResponseEntity<?> downloadImage(@PathVariable(name = "id") Long id) throws IOException {
		ProductImages productImages = repository.getById(id);

		File imageFile = fileService.dowwnloadImage(id);
		InputStreamResource imageStream = new InputStreamResource(new FileInputStream(imageFile));

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition", String.format("attachment; filename=\"%s\"", productImages.getPath()));
		headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
		headers.add("Pragma", "no-cache");
		headers.add("Expires", "0");

		return ResponseEntity.ok().headers(headers).contentLength(imageFile.length())
				.contentType(MediaType.parseMediaType("image/jpeg")).body(imageStream);
	}

	@GetMapping(value = "api/v1/product/{id}/images")
	public ResponseEntity<?> ProductImgesDetail(@PathVariable(name = "id") Long id) {
		Products products = productRepository.findById(id).orElse(null);
		if (products == null) {
			throw new NoDataFoundException(messageSource.getMessage("Get.product.id.notfound",
					new String[] { Long.toString(id) }, Locale.getDefault()));
		}
		List<ProductImages> productImages = products.getProductImges();
		List<ProductImagesRespone> listRespone = new ArrayList<>();
		for (ProductImages productImage : productImages) {
			String pathRespone = "/api/v1/product/image/" + productImage.getId();
			ProductImagesRespone respone = new ProductImagesRespone(productImage.getId(), id, pathRespone);
			listRespone.add(respone);
		}
		return new ResponseEntity<>(listRespone, HttpStatus.OK);
	}

}
