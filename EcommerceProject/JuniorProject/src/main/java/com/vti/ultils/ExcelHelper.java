/**
 * 
 */
package com.vti.ultils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import com.vti.model.entity.Products;
import com.vti.repository.IProductsGroupRepository;

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
public class ExcelHelper {
	public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
	static String[] HEADERs = { "NAME", "PRICE", "DESCRIPTION", "GROUP_ID" };
	static String SHEET = "Products";
	
	@Autowired
	static
	IProductsGroupRepository productsGroupRepository;

	public static boolean hasExcelFormat(MultipartFile file) {
		if (!TYPE.equals(file.getContentType())) {
			return false;
		}
		return true;
	}

	public static List<Products> excelToTutorials(InputStream is) {
		try {
			Workbook workbook = new XSSFWorkbook(is);
			Sheet sheet = workbook.getSheet(SHEET);
			Iterator<Row> rows = sheet.iterator();
			List<Products> products = new ArrayList<>();
			int rowNumber = 0;
			while (rows.hasNext()) {
				Row currentRow = rows.next();
				// skip header
				if (rowNumber == 0) {
					rowNumber++;
					continue;
				}
				Iterator<Cell> cellsInRow = currentRow.iterator();
				Products product = new Products();
				int cellIdx = 0;
				while (cellsInRow.hasNext()) {
					Cell currentCell = cellsInRow.next();
					switch (cellIdx) {
					case 0:
						product.setProductName(currentCell.getStringCellValue());
						break;
					case 1:
						if(!currentCell.getStringCellValue().toString().isEmpty()) {
							product.setDescription(currentCell.getStringCellValue());
						}
						else {
							product.setDescription(null);
						}
						break;
					case 2:
						product.setPrice((int) currentCell.getNumericCellValue());
						break;
					
//					case 3:
//						ProductGroups product_Groups = productsGroupRepository.findById(currentCell.getNumericCellValue()).orElse(null);
//						long groupId = 13;
//						product.getGroup().setId(groupId);
//						product.getGroup().setId(Long.parseLong(currentCell.getStringCellValue()));
//						product.getGroup().setId(Long.parseLong(currentCell.getStringCellValue()));
//						break;
					default:
//						Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//						LoginUserDetails customUserDetails = (LoginUserDetails) authentication.getPrincipal();
//						Users users = customUserDetails.getLoginUser();
//						product.getUser().setId(users.getId());
//						long userID = 1;
//						product.getGroup().setId(userID) ;
//						product.getUser().setId(userID);
						break;
					}
					cellIdx++;
				}
//				Long userID = (long) 1;
//				product.getGroup().setId(userID) ;
//				product.getUser().setId(userID);
				products.add(product);
			}
			workbook.close();
			return products;
		} catch (IOException e) {
			throw new RuntimeException("fail to parse Excel file: " + e.getMessage());
		}
	}
}
