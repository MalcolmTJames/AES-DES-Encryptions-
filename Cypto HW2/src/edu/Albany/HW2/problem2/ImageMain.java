package edu.Albany.HW2.problem2;

import java.io.*;

public class ImageMain {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		
		File dataIMG = new File("/Users/malcolmjames/Desktop/PlainImages/circle.jpg");
		//File pubIMG1 = new File("/Users/malcolmjames/Desktop/PlainImages/ChristmasinAustralia.jpg");
		//File pubIMG2 = new File("/Users/malcolmjames/Desktop/PlainImages/Damavand-mountain.jpg");
		
		
		ImageEncrypt.ecbEncryptIMG(dataIMG);
		ImageEncrypt.cbcEncryptIMG(dataIMG);
		ImageEncrypt.cfbEncryptIMG(dataIMG);
		ImageEncrypt.ofbEncryptIMG(dataIMG);
		
		
		
		
	}
}