package controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.PictureData;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.mongodb.BasicDBObject;

import models.Pricing;
import models.Product;
import models.Specifications;
import play.mvc.Controller;
import play.mvc.Http.MultipartFormData;
import play.mvc.Http.MultipartFormData.FilePart;
import utils.Mongo;
import play.mvc.Result;

public class CatalogUpload extends Controller {

	public Result uploadCatalog() throws Exception {
		MultipartFormData body = request().body().asMultipartFormData();
		FilePart catalogFile = body.getFiles().get(0);
		if (catalogFile != null) {
			File file = catalogFile.getFile();
			FileInputStream inputStream = new FileInputStream(file);
	         
	        Workbook workbook = new XSSFWorkbook(inputStream);
	        Sheet firstSheet = workbook.getSheetAt(0);
	        Iterator<Row> iterator = firstSheet.iterator();
	        @SuppressWarnings("unchecked")
			List<PictureData> pictures = (List<PictureData>) workbook.getAllPictures();
	        int i = 0;
	        while (iterator.hasNext()) {
	        	if (i == 0) {
	        		i++;
	        		iterator.next();
	        		continue;
	        	}
	            Row nextRow = iterator.next();
	            if (nextRow.getCell(0) == null) {
	            	break;
	            }
	            String name = nextRow.getCell(0).getStringCellValue();
	            String category = nextRow.getCell(1).getStringCellValue();
	            String subCategory = nextRow.getCell(2).getStringCellValue();
	            Double costPrice = nextRow.getCell(3).getNumericCellValue();
	            Double discount = nextRow.getCell(4).getNumericCellValue();
	            Double sellingPrice = nextRow.getCell(5).getNumericCellValue();
	            List<String> features = Arrays.asList(nextRow.getCell(6).getStringCellValue().split(","));
	            String brand = nextRow.getCell(8).getStringCellValue();
	            String modelNo = nextRow.getCell(9).getStringCellValue();
	            String color = nextRow.getCell(10).getStringCellValue();
	            String size = nextRow.getCell(11).getStringCellValue();
	            Integer itemsInStock = Double.valueOf(nextRow.getCell(12).getNumericCellValue()).intValue();
	            List<String> citiesForDelivery = Arrays.asList(nextRow.getCell(13).getStringCellValue().split(","));
	            Pricing pricing = new Pricing(costPrice, discount, sellingPrice);
	            Specifications specifications = new Specifications(brand, modelNo, color, size);
	            Product product = new Product(name, "anuja", category, subCategory, pricing, features, pictures.get(i-1).getData(), specifications, itemsInStock, citiesForDelivery);
	            Mongo.datastore().save(product);
        		i++;
	        }
	        
	        inputStream.close();
			BasicDBObject result = new BasicDBObject("result", "failure");
			return ok(result.toJson());
		} else {
			flash("error", "Missing file");
		}
		BasicDBObject result = new BasicDBObject("result", "failure");
		return ok(result.toJson());
	}
}
