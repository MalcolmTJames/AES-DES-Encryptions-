package edu.Albany.HW2.problem1;

import java.util.Base64;

import javax.crypto.*;

public class CryptoECB {
	
	private static String ECB = "AES/ECB/PKCS5PADDING";

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		
		String input = "Hello my name is Malcolm James and I like pizza alot!";
		
		System.out.println("Plain Message: " + input);
		
		//Encryption of message
		KeyGenerator CryptoKey = KeyGenerator.getInstance("AES");
		CryptoKey.init(128);
		SecretKey SecretCryptoKey = CryptoKey.generateKey();
		
		Cipher algorithm = Cipher.getInstance(ECB);
		algorithm.init(Cipher.ENCRYPT_MODE, SecretCryptoKey);

		byte [] cipherByteArray = algorithm.doFinal(input.getBytes("UTF-8"));
		String cipherText = new String(Base64.getEncoder().encode(cipherByteArray), "UTF-8");
		
		System.out.println("Ciphertext with AES/ECB algorithm: " +cipherText);
		
		//Decryption of message
		algorithm.init(Cipher.DECRYPT_MODE, SecretCryptoKey);
		byte[] plainByteArray = algorithm.doFinal(Base64.getDecoder().decode(cipherText.getBytes()));
		String plainText = new String(plainByteArray, "UTF-8");
		
		System.out.println("Plaintext after decryption: " + plainText);
		
	}

}
