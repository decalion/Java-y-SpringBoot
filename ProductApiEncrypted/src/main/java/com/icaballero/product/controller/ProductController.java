package com.icaballero.product.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.icaballero.product.dto.OutputEncryptedDTO;
import com.icaballero.product.dto.ProductDTO;
import com.icaballero.product.exceptions.GeneralSystemFailureException;
import com.icaballero.product.service.IProductService;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author Ismael Caballero
 *
 */
@RestController
@RequestMapping("/v2/product/products")
@Slf4j
public class ProductController {
	
	@Autowired
	@Qualifier("ProductService")
	private IProductService productService;
	
	@GetMapping(consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<OutputEncryptedDTO> findAll() throws GeneralSystemFailureException {

		log.info("[ProductController - findAll()]");
		OutputEncryptedDTO dataEncrypted = productService.findAll();
		
		return new ResponseEntity<OutputEncryptedDTO>(dataEncrypted, HttpStatus.OK);
	}

	
	@PostMapping(path = "/decrypt",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<ProductDTO>> findAllProduct(@RequestBody OutputEncryptedDTO body) throws GeneralSystemFailureException {

		log.info("[ProductController - findAllProduct()]");
		List<ProductDTO> products = productService.findAllProducts(body);
		
		return new ResponseEntity<List<ProductDTO>>(products, HttpStatus.OK);
	}


}
