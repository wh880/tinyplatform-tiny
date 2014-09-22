package org.tinygroup.webservicetest;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;

public class NumberFilter {
	private static String num = "";
	public static void main(String[] args) {
		try {
	            // 一次读一个字符
			FileReader f = new FileReader(new File("F:\\ltnum\\156.TXT"));
	            BufferedReader br=new BufferedReader(f);
	            String line="";
	            StringBuffer  buffer = new StringBuffer();
	            while((line=br.readLine())!=null){
	            	buffer.append(line);
	            }
	            String fileContent = buffer.toString();
	            //System.out.println(fileContent);
	            String[] str = fileContent.split(",");
	            System.out.println(str.length);
	            double[] nums =new double[str.length];
	            
	            for(int i = 0 ; i < str.length ; i++){
	            	System.out.println(str[i]);
//	            	nums[i] = Double.parseDouble(str[i].trim());
	            }
		} catch (Exception e) {
			e.printStackTrace();
		}
	           
	}
}
