package com.strong.web.menu;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import net.sf.json.JSONArray; 
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import com.strong.model.entity.*;;

public class MenuSettingHelper {
	private static String menu="";
	static {
		// 第1步、使用File类找到一个文件
		File path = null;
		try {
			path = new File(ResourceUtils.getURL("classpath:").getPath());
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		System.out.println("path:"+path.getAbsolutePath());
		
		File menuConfig = new File(path.getAbsolutePath(),"static/menuSetting.json");

		System.out.println("menuConfig url:"+menuConfig.getAbsolutePath());		
		
		File f = new File(menuConfig.getAbsolutePath()); // 声明File对象
		// 第2步、通过子类实例化父类对象
		InputStream input = null; // 准备好一个输入的对象
		try {
			input = new FileInputStream(f);
			// 第3步、进行读操作
			byte b[] = new byte[(int)f.length()] ; // 所有的内容都读到此数组之中
			try {
				input.read(b);
				// 第4步、关闭输出流
				input.close(); // 关闭输出流			
				menu=new String(b);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} // 读取内容

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // 通过对象多态性，进行实例化
	}
	public static String getMenuStr(){
		return menu;
	}
	
	public static Menu[] getMenu(){
		return buildMenus(menu);
	}
	
	private static Menu[] buildMenus(String menuStr){
		JSONArray jsonArray = JSONArray.fromObject(menuStr); 

        //List<Menu> menuList = JSONArray.toList(jsonArray, new Menu(),new JsonConfig());
		System.out.println("jsonArray.size():"+jsonArray.size());		
		Menu[] menus=(Menu[])new Menu[jsonArray.size()];

		for(int i=0;i<jsonArray.size();i++){
			String jsonMenu=jsonArray.getString(i);
			menus[i]=buildMenu(jsonMenu);
		}

        return menus;
	}
	
	private static Menu buildMenu(String json){
		Menu m=new Menu();
		JSONObject jo= JSONObject.fromObject(json);
		m.setRoute(jo.optString("route"));
		m.setTitle(jo.optString("title"));
		m.setMenuCode(jo.optString("menuCode"));
		m.setRoleManagement(jo.optString("roleManagement"));
		m.setPermissionsControl(jo.optString("permissionsControl"));
		String subNav=jo.optString("subNav");
		if(!subNav.isEmpty()){
			m.setSubNav(buildMenus(subNav));	
		}
		return m;
	}

}