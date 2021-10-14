package com.vti.service.productImage;

import java.io.File;
import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.vti.model.entity.ProductImages;

public interface IProductImageService {

	public ProductImages uploadImage(MultipartFile image, Long id) throws IOException;

	public File dowwnloadImage(Long id) throws IOException;

}
