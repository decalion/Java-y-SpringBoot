package com.icaballero.product.service;

import java.util.List;

import com.icaballero.product.dto.OutputEncryptedDTO;
import com.icaballero.product.dto.ProductDTO;
import com.icaballero.product.exceptions.GeneralSystemFailureException;

/**
 * IProductService
 * @author Ismael Caballero
 *
 */
public interface IProductService {
	
	/**
	 *  find all products
	 * @return data encrypted
	 */
	public OutputEncryptedDTO findAll() throws GeneralSystemFailureException;
	
	/**
	 * 
	 * @param encryptedData
	 * @return
	 * @throws GeneralSystemFailureException
	 */
	public List<ProductDTO> findAllProducts(OutputEncryptedDTO encryptedData) throws GeneralSystemFailureException;

}
