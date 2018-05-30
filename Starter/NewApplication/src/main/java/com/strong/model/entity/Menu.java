package com.strong.model.entity;

//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
import lombok.ToString;
//import lombok.experimental.Accessors;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.LinkedHashSet;
import java.util.Set;

@ToString
public class Menu implements Serializable {

	private static final long serialVersionUID = 7698862379923111159L;

	/// <summary>
	/// UI Route name
	/// </summary>
	private String route;
	private String title;
	private String menuCode;
	private String roleManagement;
	private String permissionsControl;
	private boolean display;
	private Menu[] subNav;
	
	public String getRoute() {
		return route;
	}
	public void setRoute(String route) {
		this.route = route;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getMenuCode() {
		return menuCode;
	}
	public void setMenuCode(String menuCode) {
		this.menuCode = menuCode;
	}
	public String getRoleManagement() {
		return roleManagement;
	}
	public void setRoleManagement(String roleManagement) {
		this.roleManagement = roleManagement;
	}
	public String getPermissionsControl() {
		return permissionsControl;
	}
	public void setPermissionsControl(String permissionsControl) {
		this.permissionsControl = permissionsControl;
	}
	public boolean getDisplay() {
		return display;
	}
	public void setDisplay(boolean display) {
		this.display = display;
	}
	public Menu[] getSubNav() {
		return subNav;
	}
	public void setSubNav(Menu[] subNav) {
		this.subNav = subNav;
	}
}