package org.hbhk.test.jsoup;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;


public class JsoupTest {

	
	public static String outChar(String str){
		StringBuilder  sb = new StringBuilder();
		for(int i=0;i<str.length();i++){
			if(i==0 && (int)str.charAt(i)==65279){
				continue;
			}
			sb.append(str.charAt(i));
			System.out.print((long)str.charAt(i)+"-");
		}
		return  sb.toString();
	}
	
	public static void main(String[] args) {
		
		BufferedReader in =null;
		StringBuffer buffer = new StringBuffer();
		File file = new File("C:/Users/jumbo/Desktop/模板/template-v2.html");
		try {
			InputStream input = new FileInputStream(file);
			
			in = new BufferedReader(new InputStreamReader(input,"utf-8"));
			String line = "";
			while ((line = in.readLine()) != null){
			    buffer.append(line );
			}

		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e1) {
				}
			}
		}
		System.out.println(buffer.toString()+buffer.toString().length());
		
		String h = outChar(buffer.toString().trim()) ;
		System.out.println("");
		
		Document doc1 = Jsoup.parse(h);
		
		System.out.println(doc1.toString());
		System.out.println("type1:"+doc1.toString());
	}
}
