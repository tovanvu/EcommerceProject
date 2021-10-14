package com.vti.service.productImage;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.vti.model.entity.ProductImages;
import com.vti.model.entity.Products;
import com.vti.repository.IProductsImagesRepository;
import com.vti.repository.IProductsRepository;
import com.vti.ultils.FileManager;
import com.vti.exception.NoDataFoundException;

@Service
public class ProductImageService implements IProductImageService {
	@Autowired
	private IProductsImagesRepository productsImagesRepository;
	@Autowired
	private IProductsRepository productsRepository;
	@Autowired
	private MessageSource messageSource;

	private FileManager fileManager = new FileManager();

	@Value("${image.linkFolder}")
	private String linkFolder;

	@Override
	public ProductImages uploadImage(MultipartFile image, Long id) throws IOException {

		String nameImage = new Date().getTime() + "." + fileManager.getFormatFile(image.getOriginalFilename());

		String path = linkFolder + "\\" + nameImage;

		fileManager.createNewMultiPartFile(path, image);

		// TODO save link file to database
		Products products = productsRepository.getById(id);
		ProductImages productImages = new ProductImages();
		productImages.setProduct(products);
		productImages.setPath(nameImage);

		return productsImagesRepository.save(productImages);
	}

	@Override
	public File dowwnloadImage(Long id) throws IOException {
		ProductImages productImages = productsImagesRepository.findById(id).orElse(null);
		if (productImages == null) {
			throw new NoDataFoundException(messageSource.getMessage("Get.productImage.id.notfound",
					new String[] { Long.toString(id) }, Locale.getDefault()));
		}
		String path = linkFolder + "\\" + productImages.getPath();

		return new File(path);
	}

}
