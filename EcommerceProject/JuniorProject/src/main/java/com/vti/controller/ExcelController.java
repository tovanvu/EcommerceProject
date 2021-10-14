/**
 * 
 */
package com.vti.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.vti.service.products.ExcelService;
import com.vti.ultils.ExcelHelper;

/**
 * This class is ...
 * 
 * @Description: ...
 * @author: KienTT
 * @create_date: Sep 27, 2021
 * @version: 1.0
 * @modifer: KienTT
 * @modifer_date: Sep 27, 2021
 */
@RestController
@RequestMapping(value = "api/v1/products")
@CrossOrigin("*")
public class ExcelController {
	@Autowired
	ExcelService service;

	@PostMapping("/import")
	public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) {
		if (ExcelHelper.hasExcelFormat(file)) {
			try {
				service.save(file);
				return new ResponseEntity<>("Uploaded the file successfully: " + file.getOriginalFilename() + "!", HttpStatus.OK);
			} catch (Exception e) {
				return new ResponseEntity<>("Could not upload the file: " + file.getOriginalFilename() + "!", HttpStatus.OK);
			}
		}

		return new ResponseEntity<>("Please upload an excel file!", HttpStatus.OK);
	}
}
