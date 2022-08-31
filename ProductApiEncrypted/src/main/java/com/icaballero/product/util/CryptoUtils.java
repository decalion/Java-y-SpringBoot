package com.icaballero.product.util;


import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.icaballero.product.exceptions.GeneralSystemFailureException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class CryptoUtils {
	
	@Value("${crytoutils.algo}")
	private String ALGO;
	
	@Value("${cryptoutils.thecipher}")
	private String THECIPHER;
	
	@Value("${cryptoutils.aes}")
	private String AES;
	
	/**
	 * 
	 * @param inputStr The String to Encrypt
	 * @param secret The secret key
	 * @param salt The Salt
	 * @param bitsCifrado Number of bits for cipher, 128, 256, etc..
	 * @param inputRIV parametro de especificacion. Si null entonces se genera en el metodo.
	 * 
	 * @return String
	 * @throws GeneralSystemFailureException 
	 */
	public String encryptAES(final String inputStr, 
									final String secret, 
									final String salt, 
									final int bitsCifrado,
									final byte[] inputRIV) throws GeneralSystemFailureException {

		String result = null;


		try {
			IvParameterSpec ivspec = null;

			if(null == inputRIV) {
				ivspec = new IvParameterSpec(genRamdomIV());
			}
			else {
				ivspec = new IvParameterSpec(inputRIV);
			}

			SecretKeySpec secretKey = null;
			if(null != salt) {
				SecretKeyFactory factory = SecretKeyFactory.getInstance(ALGO);
				KeySpec spec = new PBEKeySpec(secret.toCharArray(), salt.getBytes(), 65536, bitsCifrado);
				SecretKey secretTMP = factory.generateSecret(spec);
				secretKey = new SecretKeySpec(secretTMP.getEncoded(), AES);
			}
			else {
				secretKey = new SecretKeySpec(secret.getBytes("UTF-8"), AES);	
			}

			Cipher cipher = Cipher.getInstance(THECIPHER);
			cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivspec);
			result = Base64.getEncoder().encodeToString(cipher.doFinal(inputStr.getBytes(StandardCharsets.UTF_8)));
		} 
		catch (Exception e) {
			log.error(e.getMessage());
			throw new GeneralSystemFailureException(GeneralSystemFailureExceptionEnum.UNXPECTED_ERROR_OCCURED);
		}

		return result;
	}


	/**
	 * 
	 * @param inputStr The encrypted String to decrypt
	 * @param secret The secret key
	 * @param salt The Salt
	 * @param bitsCifrado Number of bits for cipher, 128, 256, etc..
	 * @param inputRIV parametro de especificacion. Si null entonces se genera en el metodo.
	 * 
	 * @return String
	 * @throws GeneralSystemFailureException 
	 */
	public String decryptAES(final String inputStr, 
									final String secret, 
									final String salt,
									final int bitsCifrado,
									final byte[] inputRIV) throws GeneralSystemFailureException {

		String result = null;


		try {
			IvParameterSpec ivspec = null;

			if(null == inputRIV) {
				ivspec = new IvParameterSpec(genRamdomIV());
			}
			else {
				ivspec = new IvParameterSpec(inputRIV);
			}

			SecretKeySpec secretKey = null;
			if(null != salt) {
				SecretKeyFactory factory = SecretKeyFactory.getInstance(ALGO);
				KeySpec spec = new PBEKeySpec(secret.toCharArray(), salt.getBytes(), 65536, bitsCifrado);
				SecretKey secretTMP = factory.generateSecret(spec);
				secretKey = new SecretKeySpec(secretTMP.getEncoded(), AES);
			}
			else {
				secretKey = new SecretKeySpec(secret.getBytes("UTF-8"), AES);	
			}

			Cipher cipher = Cipher.getInstance(THECIPHER);
			cipher.init(Cipher.DECRYPT_MODE, secretKey, ivspec);
			result = new String(cipher.doFinal(Base64.getDecoder().decode(inputStr)));
		}
		catch (Exception e) {
			log.error(e.getMessage());
			throw new GeneralSystemFailureException(GeneralSystemFailureExceptionEnum.UNXPECTED_ERROR_OCCURED);
		}

		return result;
	}
	
	
	/**
	 * Generate the byte array for IvParameterSpec
	 * @return
	 */
	private byte[] genRamdomIV() {

		byte[] randomIV = new byte[16];
		new SecureRandom().nextBytes(randomIV);

		return randomIV;
	}

}
