package com.icaballero.product.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.icaballero.product.dao.ProductDao;
import com.icaballero.product.dto.OutputEncryptedDTO;
import com.icaballero.product.dto.ProductDTO;
import com.icaballero.product.exceptions.GeneralSystemFailureException;
import com.icaballero.product.service.IProductService;
import com.icaballero.product.util.CryptoUtils;
import com.icaballero.product.util.GeneralSystemFailureExceptionEnum;

import lombok.extern.slf4j.Slf4j;

/**
 * ProductServiceImpl
 * 
 * @author Ismael Caballero
 *
 */
@Service
@Qualifier("ProductService")
@Slf4j
public class ProductServiceImpl implements IProductService {

	@Value("${cryptoutils.bits}")
	private Integer bits;
	@Value("${cryptoutils.salt}")
	private String salt;
	@Value("${cryptoutils.secret}")
	private String secret;

	@Autowired
	private CryptoUtils cryptoUtils;

	@Override
	public OutputEncryptedDTO findAll() throws GeneralSystemFailureException {

		log.info("[ProductService - findAll()] - START");

		OutputEncryptedDTO dto = new OutputEncryptedDTO();

		try {

			List<ProductDTO> products = ProductDao.getProducts();

			String encryptedData = encryptedData(products);

			log.info("Data Encrypted Sucefull.");

			dto.setData(encryptedData);

		} catch (Exception e) {
			log.error("Error during calling ProductService.");
			throw new GeneralSystemFailureException(GeneralSystemFailureExceptionEnum.UNXPECTED_ERROR_OCCURED);
		}

		log.info("[ProductService - findAll()] - END");
		return dto;
	}

	/**
	 * Encrypt Product info
	 * 
	 * @return data encrypted
	 */
	private String encryptedData(List<ProductDTO> products) throws GeneralSystemFailureException {

		log.info("Encrypting data...");

		byte[] vectorInicializacionIV = salt.getBytes();
		return cryptoUtils.encryptAES(new Gson().toJson(products), secret, null, bits, vectorInicializacionIV);

	}

	/**
	 * 
	 * @param encryptedData
	 * @return
	 */
	private List<ProductDTO> decryptData(String encryptedData) throws GeneralSystemFailureException {

		log.info("[Decrypting data ...");

		byte[] vectorInicializacionIV = salt.getBytes();
		String json = cryptoUtils.decryptAES(encryptedData, secret, null, bits, vectorInicializacionIV);
		List<ProductDTO> list = Arrays.asList(new Gson().fromJson(json, ProductDTO[].class));

		return list;

	}

	@Override
	public List<ProductDTO> findAllProducts(OutputEncryptedDTO encryptedData) throws GeneralSystemFailureException {
		
		List<ProductDTO> products = new ArrayList<>();
		log.info("[ProductService - findAllProducts()] - START");
		try {

			products = decryptData(encryptedData.getData());

			log.info("Data Decrypted Sucefull.");


		} catch (Exception e) {
			log.error("Error during calling ProductService.");
			throw new GeneralSystemFailureException(GeneralSystemFailureExceptionEnum.UNXPECTED_ERROR_OCCURED);
		}
		
		log.info("[ProductService - findAllProducts()] - END");
		return products;
	}

}
