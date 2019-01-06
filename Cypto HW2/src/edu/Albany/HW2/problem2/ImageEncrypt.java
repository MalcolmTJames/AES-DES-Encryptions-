package edu.Albany.HW2.problem2;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.imageio.ImageIO;

public class ImageEncrypt {
	
	private static String ECB = "AES/ECB/PKCS5PADDING";
	private static String CBC = "AES/CBC/PKCS5PADDING";
	private static String OFB = "AES/OFB/NOPADDING";
	private static String CFB = "AES/CFB/NOPADDING";
	
    private static final int RGB_SIZE = 3;
	private static final int BSHIFT = 0xFF;
	
	private static BufferedImage image;
	private static int width;
	private static int height;
	
	public static void ecbEncryptIMG (File imageFile) throws Exception
	{
		  
		image = ImageIO.read(imageFile);
	    width = image.getWidth();
	    height = image.getHeight();
	    
	    int e = 0;

	    byte[] imagebyte = new byte[width * height * RGB_SIZE];
	    int index = 0;

	    // fill the table with RGB values;
	    for (int i = 0; i < height; i++) {
	      for (int j = 0; j < width; j++) {
	        Color c = new Color(image.getRGB(j, i));

	        byte r = (byte) c.getRed();
	        byte g = (byte) c.getGreen();
	        byte b = (byte) c.getBlue();

	        
	        if(index >= 120000 && index <= 120200){
	            System.out.println("ERROR MADE: "+ e);
	            e++;
	            imagebyte[index++] = b;
	            imagebyte[index++] = g;
	            imagebyte[index++] = r;
	          }else{
		        	  imagebyte[index++] = r;
		        	  imagebyte[index++] = g;
		        	  imagebyte[index++] = b;
	          }
	      }
	    }
	    
	    //Encryption of message
		KeyGenerator CryptoKey = KeyGenerator.getInstance("AES");
	    CryptoKey.init(128);
		SecretKey SecretCryptoKey = CryptoKey.generateKey();
			
		Cipher algorithm = Cipher.getInstance(ECB);
		algorithm.init(Cipher.ENCRYPT_MODE, SecretCryptoKey);

		byte [] cipherByte = algorithm.doFinal(imagebyte);
	      

	    // Re-create image with table-encrypted RGB values
	    BufferedImage newImage = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
	    index = 0;
	    for (int i = 0; i < height; i++) {
	      for (int j = 0; j < width; j++) {

	        // Need to deal with values < 0 so binary AND with 0xFF
	        // Java 8 provides Byte.toUnsignedInt but I am from the old school ;-)
	        int r = cipherByte[index++] & BSHIFT;
	        int g = cipherByte[index++] & BSHIFT;
	        int b = cipherByte[index++] & BSHIFT;

	        Color newColor = new Color(r, g, b);
	        newImage.setRGB(j, i, newColor.getRGB());

	      }
	    }
	     
	    // write the output image
	    File output = new File("/Users/malcolmjames/Desktop/CypherImages/ECBEncryption.jpg");
	    ImageIO.write(newImage, "jpg", output);
	}
	
	public static void cbcEncryptIMG (File imageFile) throws Exception
	{
		  
		image = ImageIO.read(imageFile);
	    width = image.getWidth();
	    height = image.getHeight();
	    
	    int e = 0;

	    byte[] imgByte = new byte[width * height * RGB_SIZE];
	    int index = 0;

	    // fill the table with RGB values;
	    for (int i = 0; i < height; i++) {
	      for (int j = 0; j < width; j++) {
	        Color c = new Color(image.getRGB(j, i));

	        // As byte is SIGNED in Java overflow will occur for values > 127
	        byte r = (byte) c.getRed();
	        byte g = (byte) c.getGreen();
	        byte b = (byte) c.getBlue();

	        if(index >= 120000 && index <= 120200){
	            System.out.println("ERROR MADE: "+ e);
	            e++;
	            imgByte[index++] = b;
	            imgByte[index++] = g;
	            imgByte[index++] = r;
	          }else{
	        	  	imgByte[index++] = r;
	        	  	imgByte[index++] = g;
	        	  	imgByte[index++] = b;
	          }
	      }
	    }
	    
	  //Encryption of image
	  KeyGenerator CryptoKey = KeyGenerator.getInstance("AES");
	  CryptoKey.init(128);
	  SecretKey SecretCryptoKey = CryptoKey.generateKey();
	  		
	  byte[] iv = new byte[16]; //Iv array to Xor plaintext with
	  SecureRandom ranNumbers = new SecureRandom();
	  		
	  ranNumbers.nextBytes(iv);
	  		
	  IvParameterSpec ivParam = new IvParameterSpec(iv);
	  		
	  Cipher algorithm = Cipher.getInstance(CBC);
	  algorithm.init(Cipher.ENCRYPT_MODE, SecretCryptoKey, ivParam);

	  byte [] cipherIMG = algorithm.doFinal(imgByte);
	      

	    // Re-create image with table-encrypted RGB values
	    BufferedImage newImage = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
	    index = 0;
	    for (int i = 0; i < height; i++) {
	      for (int j = 0; j < width; j++) {

	        // Need to deal with values < 0 so binary AND with 0xFF
	        // Java 8 provides Byte.toUnsignedInt but I am from the old school ;-)
	        int r = cipherIMG[index++] & BSHIFT;
	        int g = cipherIMG[index++] & BSHIFT;
	        int b = cipherIMG[index++] & BSHIFT;

	        Color newColor = new Color(r, g, b);
	        newImage.setRGB(j, i, newColor.getRGB());

	      }
	    }
	     
	    // write the output image
	    File output = new File("/Users/malcolmjames/Desktop/CypherImages/CBCEncryption.jpg");
	    ImageIO.write(newImage, "jpg", output);
	}

	public static void ofbEncryptIMG (File imageFile) throws Exception
	{
		  
		image = ImageIO.read(imageFile);
	    width = image.getWidth();
	    height = image.getHeight();
	    int e = 0;

	    byte[] imgByte = new byte[width * height * RGB_SIZE];
	    int index = 0;

	    // fill the table with RGB values;
	    for (int i = 0; i < height; i++) {
	      for (int j = 0; j < width; j++) {
	        Color c = new Color(image.getRGB(j, i));

	        // As byte is SIGNED in Java overflow will occur for values > 127
	        byte r = (byte) c.getRed();
	        byte g = (byte) c.getGreen();
	        byte b = (byte) c.getBlue();

	        if(index >= 120000 && index <= 120200){
	            System.out.println("ERROR MADE: "+ e);
	            e++;
	            imgByte[index++] = b;
	            imgByte[index++] = g;
	            imgByte[index++] = r;
	          }else{
	        	  	imgByte[index++] = r;
	        	  	imgByte[index++] = g;
	        	  	imgByte[index++] = b;
	          }
	      }
	    }
	    
	  //Encryption of image
	  KeyGenerator CryptoKey = KeyGenerator.getInstance("AES");
	  CryptoKey.init(128);
	  SecretKey SecretCryptoKey = CryptoKey.generateKey();
	  		
	  byte[] iv = new byte[16]; //Iv array to Xor plaintext with
	  SecureRandom ranNumbers = new SecureRandom();
	  		
	  ranNumbers.nextBytes(iv);
	  		
	  IvParameterSpec ivParam = new IvParameterSpec(iv);
	  		
	  Cipher algorithm = Cipher.getInstance(OFB);
	  algorithm.init(Cipher.ENCRYPT_MODE, SecretCryptoKey, ivParam);

	  byte [] c = algorithm.doFinal(imgByte);
	      

	    // Re-create image with table-encrypted RGB values
	    BufferedImage newImage = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
	    index = 0;
	    for (int i = 0; i < height; i++) {
	      for (int j = 0; j < width; j++) {

	        // Need to deal with values < 0 so binary AND with 0xFF
	        // Java 8 provides Byte.toUnsignedInt but I am from the old school ;-)
	        int r = c[index++] & BSHIFT;
	        int g = c[index++] & BSHIFT;
	        int b = c[index++] & BSHIFT;

	        Color newColor = new Color(r, g, b);
	        newImage.setRGB(j, i, newColor.getRGB());

	      }
	    }
	     
	    // write the output image
	    File output = new File("/Users/malcolmjames/Desktop/CypherImages/OFBEncryption.jpg");
	    ImageIO.write(newImage, "jpg", output);
	}
	
	public static void cfbEncryptIMG (File imageFile) throws Exception
	{
		  
		image = ImageIO.read(imageFile);
	    width = image.getWidth();
	    height = image.getHeight();
	    
	    int e = 0;

	    byte[] imgByte = new byte[width * height * RGB_SIZE];
	    int index = 0;

	    // fill the table with RGB values;
	    for (int i = 0; i < height; i++) {
	      for (int j = 0; j < width; j++) {
	        Color c = new Color(image.getRGB(j, i));

	        // As byte is SIGNED in Java overflow will occur for values > 127
	        byte r = (byte) c.getRed();
	        byte g = (byte) c.getGreen();
	        byte b = (byte) c.getBlue();

	        if(index >= 120000 && index <= 120200){
	            System.out.println("ERROR MADE: "+ e);
	            e++;
	            imgByte[index++] = b;
	            imgByte[index++] = g;
	            imgByte[index++] = r;
	          }else{
	        	  	imgByte[index++] = r;
	        	  	imgByte[index++] = g;
	        	  	imgByte[index++] = b;
	          }
	      }
	    }
	    
	  //Encryption of image
	  KeyGenerator CryptoKey = KeyGenerator.getInstance("AES");
	  CryptoKey.init(128);
	  SecretKey SecretCryptoKey = CryptoKey.generateKey();
	  		
	  byte[] iv = new byte[16]; //Iv array to Xor plaintext with
	  SecureRandom ranNumbers = new SecureRandom();
	  		
	  ranNumbers.nextBytes(iv);
	  		
	  IvParameterSpec ivParam = new IvParameterSpec(iv);
	  		
	  Cipher algorithm = Cipher.getInstance(CFB);
	  algorithm.init(Cipher.ENCRYPT_MODE, SecretCryptoKey, ivParam);

	  byte [] c = algorithm.doFinal(imgByte);
	      

	    // Re-create image with table-encrypted RGB values
	    BufferedImage newImage = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
	    index = 0;
	    for (int i = 0; i < height; i++) {
	      for (int j = 0; j < width; j++) {

	        // Need to deal with values < 0 so binary AND with 0xFF
	        // Java 8 provides Byte.toUnsignedInt but I am from the old school ;-)
	        int r = c[index++] & BSHIFT;
	        int g = c[index++] & BSHIFT;
	        int b = c[index++] & BSHIFT;

	        Color newColor = new Color(r, g, b);
	        newImage.setRGB(j, i, newColor.getRGB());

	      }
	    }
	     
	    // write the output image
	    File output = new File("/Users/malcolmjames/Desktop/CypherImages/CFBEncryption.jpg");
	    ImageIO.write(newImage, "jpg", output);
	}
}
