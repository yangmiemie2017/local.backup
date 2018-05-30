package com.strong.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.strong.constant.PwdStrength;


/**  
 * 检查密码强度的工具类 - check password strength class 
 * */
public class CheckPwdStrengthUtil {
	/**  
	 * 检查密码强度 的主方法 - check password strength main method
	 * @param pwd
	 *传入的用于校验的密码 - validate password
	 * @throws IOException 
	 * */
	public static int check(String pwd){
		boolean boolData1 = false;// 数字flag - number flag
		boolean boolData2 = false;// 小写字母flag - lower case flag
		boolean boolData3 = false;// 大写字母flag - upper case flag
		boolean boolData4 = false;// 字符flag - character flag
		int level;// level
		
		if (pwd == "") {
			return PwdStrength.EMPTY;// 如果密码为空，则返回level 0 - if password is empty, return LV 0
		}

		for (int i = 0; i < pwd.length(); i++) {
			char code = pwd.charAt(i);//get each char
			// 判断输入密码的各个字符 - judge each char
			if ((code >= 48) && (code <= 57)) {// 数字 - number
				boolData1 = true;
			} else if ((code >= 97) && (code <= 122)) {// 小写字母 - lower case
				boolData2 = true;
			} else if ((code >= 65) && (code <= 90)) {// 大写字母 - upper case
				boolData3 = true;
			} else if ((code >= 35) && (code <= 38) || code == 46 || code == 64 || code == 95) {// other #$%&.@_  support these
				boolData4 = true;
			} else {
				return PwdStrength.INVALID_CHARACTER;// 汉字和其他一些不合法字符 - Chinese character and other illegal char
			}
		}

		// 首先匹配密码字典 - firstly match the password dictionary 
		if (KeyWordSearch.search(pwd)) {
			level = PwdStrength.VERY_WEAK;// 密码字典中包含则 level -1  - dictionary already have LV -1
		} else if (pwd.length() < 8) {// 如果密码长度小于8则认为是弱密码 level 1 - if password length is less than 8 consider weak password
			level = PwdStrength.VERY_WEAK;
		} else {//其他大于等于8位的密码 - other more than 8 length
			if (boolData1 && !boolData2 && !boolData3 && !boolData4) {// 全是数字 level 1 - all number 
				level = PwdStrength.WEAK;
			} else if (!boolData1 && boolData2 && !boolData3 && !boolData4
					|| !boolData1 && !boolData2 && boolData3 && !boolData4) {// 全是字母 LV 1 - all lower case/upper case
				level = PwdStrength.WEAK;
			} else if (!boolData1 && !boolData2 && !boolData3 && boolData4) {// 全是字符 LV 1 - all character
				level = PwdStrength.WEAK;
			} else if (getStrCharNum(pwd) < 8 && pwd.length()>8) {// 在密码长度>8的基础上，不同的字符数小于8,相等的数比较多 LV 2 - when pwd length is over 8, if different character count is less than 8, the same character is relatively more
				level = PwdStrength.MIDDLE;
			} else if ((!isGetNumEmpty(pwd))&&(isOver2NumsContinuity(pwd))) {// 在密码长度>=8的基础上，连续3个数升序或降序 LV 2 - Continuously number is ascending/Descending
				level = PwdStrength.MIDDLE;
			}else if (!boolData1 && boolData2 && boolData3 && !boolData4) {// 小写字母+大写 LV 2 - lower case+upper case LV2
				level = PwdStrength.MIDDLE;
			} else if (!boolData1 && boolData2 && !boolData3 && boolData4) {// 字符+小写 LV 2 -character+lower case LV2
				level = PwdStrength.MIDDLE;
			} else if (!boolData1 && !boolData2 && boolData3 && boolData4) {// 字符+大写 LV 2 - character+upper case LV2
				level = PwdStrength.MIDDLE;
			} else if (boolData1 && !boolData2 && !boolData3 && boolData4) {// 数字+字符 LV 2 - number+character LV2
				level = PwdStrength.MIDDLE;
			} else if (boolData1 && boolData2 && !boolData3 && !boolData4) {// 数字+小写 LV 2 - number+lower case LV2
				level = PwdStrength.MIDDLE;
			} else if (boolData1 && !boolData2 && boolData3 && !boolData4) {// 数字+大写 LV 2 - number+upper case LV2
				level = PwdStrength.MIDDLE;
			} else if (boolData1 && boolData2 && boolData3 && !boolData4) {// 数字+大写+小写 LV 3 - number+upper case+lower case LV2
				level = PwdStrength.STRONG;
			} else if (!boolData1 && boolData2 && boolData3 && boolData4) {// 字符+小写+大写 LV 3 - character+lower case+upper case LV3
				level = PwdStrength.STRONG;
			} else if (boolData1 && boolData2 && !boolData3 && boolData4) {// 数字+字符+小写 LV 3 - number+character+lower case LV3
				level = PwdStrength.STRONG;
			} else if (boolData1 && !boolData2 && boolData3 && boolData4) {// 数字+字符+大写 LV 3 - number+character+upper case LV3
				level = PwdStrength.STRONG;
			} else if (boolData1 && boolData2 && boolData3 && boolData4) {// 都有---极强 LV 4 - all have LV4
				level = PwdStrength.EXTREME_STRONG;
			} else {
				level = PwdStrength.INVALID_CHARACTER;// invalid		//非法字符如汉字之类 - others invalid
			}
		}

		return level;  //返回Level - return LV
	}

	/**  
	 *判断字符串中是否含数字
	 * @param pwd
	 * 传入的用于检测的密码
	 * @return boolean
	 * */
		public static boolean isGetNumEmpty(String pwd) {
			boolean flag = false;
			String regEx = "[^0-9]"; // 判断数字的正则
			Pattern p = Pattern.compile(regEx);
			Matcher m = p.matcher(pwd);
			String newStr = m.replaceAll("").trim();
			if ("".equals(newStr)) {
				flag = true;//不含数字返回true
			}

			return flag;//返回判断结果
		}

	/**  
	 * 获取字符串中不同字符的统计个数 - get not same character count in string  
	 * @param str
	 * 传入的用于检测的字符串 - the validate string
	 * @return int
	 * */
	public static int getStrCharNum(String str) {
		char[] charArray = str.toCharArray();

		TreeMap<Character, Integer> tm = new TreeMap<Character, Integer>();

		for (int x = 0; x < charArray.length; x++) {
			if (!tm.containsKey(charArray[x])) {
				tm.put(charArray[x], 1);
			} else {
				int count = tm.get(charArray[x]) + 1;
				tm.put(charArray[x], count);
			}
		}
		return tm.size();//返回不同字符个数 - return not same character count
	}

	/**  
	 * 判断截获的分段数字，是否有连续升序或降序的数
	 * @param pwd
	 * 传入的用于检测的字符串
	 * @return boolean
	 * */
	public static boolean isOver2NumsContinuity(String pwd) {
		boolean flag = false;
		List<String> ss = new ArrayList<String>();
		for (String str : pwd.replaceAll("[^0-9]", ",").split(",")) {
			if (str.length() > 2)
				ss.add(str);//将分段的数字放入list
		}

		for (int i = 0; i < ss.size(); i++) {
			if (isContinuity(ss.get(i))==true) {
				flag = true;//有连续的数true
				break;//如果有了立即跳出循环
			}
		}

		return flag;//返回判断结果
	}
	
	//判断连续三个数是否为升序或降序
	public static boolean isContinuity(String pwd) {
		boolean flag = false;
		pwd += "000";
		char[] chars = pwd.toCharArray();
		int tmp1 = 0;
		int tmp2 = 0;
		int tmp3 = 0;
		for (int i = 0; i < chars.length - 3; i++) {
			//三个临时变量存储用于计算是否为等差数列
			tmp1 = Integer.parseInt(chars[i] + "");
			tmp2 = Integer.parseInt(chars[i + 1] + "");
			tmp3 = Integer.parseInt(chars[i + 2] + "");
			if ((tmp3 - tmp2) == (tmp2 - tmp1)) {//判断是否等差树列
				flag = true;//结果相等说明三个数连续了
				break;//so break out loop
			}
		}
		return flag;//返回判断结果
	}
}
