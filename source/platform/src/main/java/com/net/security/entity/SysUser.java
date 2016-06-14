package com.net.security.entity;


public class SysUser extends ScUser{

	private static final long serialVersionUID = 1L;
	
	//role name
	private String roleName;
	
	//authority type
	private String authorityType;
	
	//start date
	private String roleStartDate;
	
	//end date
	private String roleEndDate;
	//默认页面
	private String defaultPage;

	public String getDefaultPage() {
		return defaultPage;
	}

	public void setDefaultPage(String defaultPage) {
		this.defaultPage = defaultPage;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getAuthorityType() {
		return authorityType;
	}

	public void setAuthorityType(String authorityType) {
		this.authorityType = authorityType;
	}

	public String getRoleStartDate() {
		return roleStartDate;
	}

	public void setRoleStartDate(String roleStartDate) {
		this.roleStartDate = roleStartDate;
	}

	public String getRoleEndDate() {
		return roleEndDate;
	}

	public void setRoleEndDate(String roleEndDate) {
		this.roleEndDate = roleEndDate;
	}

	
	
	

}
