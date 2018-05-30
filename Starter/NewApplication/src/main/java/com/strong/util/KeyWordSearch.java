package com.strong.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class KeyWordSearch {
    
	public static boolean search(String keyword){
		boolean flag = false;
		InputStream is = KeyWordSearch.class.getClassLoader().getResourceAsStream("pwd_dictionary.txt");//获取文件 - get the dictionary file

		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		String nextLine = "";
		
		try{
		while ((nextLine = br.readLine()) != null) {//查找关键字，如果包含，返回true - search the key word, if contain, return true
			if (nextLine.indexOf(keyword) > -1) {
				flag = true;
			}
		}
		} catch(IOException e){
		    e.printStackTrace();
		}
		
		return flag;
	}
}
