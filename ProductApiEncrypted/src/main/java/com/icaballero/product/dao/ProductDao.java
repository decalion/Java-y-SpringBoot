package com.icaballero.product.dao;

import java.util.ArrayList;
import java.util.List;

import com.icaballero.product.dto.ProductDTO;

import lombok.extern.slf4j.Slf4j;

/**
 * ProductDao Sample for testing data
 * @author Ismael Caballero
 *
 */
@Slf4j
public class ProductDao {
	
	private static List<ProductDTO> products = new ArrayList<>();
	
	static {
		for (int i = 0; i < 10; i++) {
			ProductDTO dto = new ProductDTO();
			dto.setCode(""+i);
			dto.setName("Name " + i);
			dto.setType("Type " + i);
			products.add(dto);
			
		}
		log.info("Show list Products {}",products);
	}

	
	/**
	 * getProducts
	 * @return list of products
	 */
	public static List<ProductDTO> getProducts() {
		return products;
	}
}
