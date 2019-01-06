package edu.Albany.HW2.problem1;

import java.security.SecureRandom;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

public class CryptoCFB {
	
	private static String CFB = "AES/CFB/NOPADDING";

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		
		String input = "Hello my name is Malcolm James and I like pizza alot!";
		
		System.out.println("Plain Message: " + input);
		
		//Encryption of message
		KeyGenerator CryptoKey = KeyGenerator.getInstance("AES");
		CryptoKey.init(128);
		SecretKey SecretCryptoKey = CryptoKey.generateKey();
		
		byte[] iv = new byte[16]; //Iv array to Xor plaintext with
		SecureRandom ranNumbers = new SecureRandom();
		
		ranNumbers.nextBytes(iv);
		
		IvParameterSpec ivParam = new IvParameterSpec(iv);
		
		Cipher algorithm = Cipher.getInstance(CFB);
		algorithm.init(Cipher.ENCRYPT_MODE, SecretCryptoKey, ivParam);

		byte [] cipherByteArray = algorithm.doFinal(input.getBytes("UTF-8"));
		String cipherText = new String(Base64.getEncoder().encode(cipherByteArray), "UTF-8");
		
		System.out.println("Ciphertext with AES/CFB algorithm: " +cipherText);
		
		//Decryption of message
		algorithm.init(Cipher.DECRYPT_MODE, SecretCryptoKey, ivParam);
		byte[] plainByteArray = algorithm.doFinal(Base64.getDecoder().decode(cipherText.getBytes()));
		String plainText = new String(plainByteArray, "UTF-8");
		
		System.out.println("Plaintext after decryption: " + plainText);

	}

}
