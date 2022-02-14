package com.realPage.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class Configuration {
	String str;
	private String filePath;
	
	String key = "";
	String value = "";
	
	public Configuration(String filePath) {
		this.filePath = filePath;
	}

	public void ReadProperty() {
//		String propval = "";
		try {
			int check = 0;
			while(check==0) {
				check = 1;
				File file = new File(filePath);
				if(file.exists()) {
					FileInputStream fis = new FileInputStream(file);
					BufferedReader br = new BufferedReader(new InputStreamReader(fis));				
					String line = null;
					
					while((line = br.readLine())!=null) {
						if(line.contains("=")) {
							String keyValue[] = line.split("=");
							key = keyValue[0];
							value = keyValue[1];
							GlobalData.ConfigData.put(key,value);					
						}
					}
					br.close();
				}
				else {
					check = 0;
				}
			}
			
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	

}
