package com.net.security.entity;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;
import org.springframework.security.core.userdetails.User;


public class BaseUser extends User{

	/**
	 * 
	 */
	private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;



	public BaseUser(String username, String password, boolean enabled,
			boolean accountNonExpired, boolean credentialsNonExpired,
			boolean accountNonLocked,
			Collection<? extends GrantedAuthority> authorities) {
		super(username, password, enabled, accountNonExpired, credentialsNonExpired,
				accountNonLocked, authorities);
		
	}

      
    private String userId;  
    private String userName;  
    private String orgCode;
    private String userAlias;  
    private String password;  
    private String cellphoneNum;  
    private String phoneNum;  
    private String email;  
    private String start_date;  
    private String end_date;  
    private Integer accountEnabled = 1;  
    private Integer accountExpired = 0;  
    private Integer accountLocked = 0;  
    private Integer credentialsExpired = 0;
    
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
      
    private Collection<GrantedAuthority>  authorities;
    


	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getUserAlias() {
		return userAlias;
	}

	public void setUserAlias(String userAlias) {
		this.userAlias = userAlias;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getCellphoneNum() {
		return cellphoneNum;
	}

	public void setCellphoneNum(String cellphoneNum) {
		this.cellphoneNum = cellphoneNum;
	}

	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getStart_date() {
		return start_date;
	}

	public void setStart_date(String start_date) {
		this.start_date = start_date;
	}

	public String getEnd_date() {
		return end_date;
	}

	public void setEnd_date(String end_date) {
		this.end_date = end_date;
	}

	public Integer getAccountEnabled() {
		return accountEnabled;
	}

	public void setAccountEnabled(Integer accountEnabled) {
		this.accountEnabled = accountEnabled;
	}

	public Integer getAccountExpired() {
		return accountExpired;
	}

	public void setAccountExpired(Integer accountExpired) {
		this.accountExpired = accountExpired;
	}

	public Integer getAccountLocked() {
		return accountLocked;
	}

	public void setAccountLocked(Integer accountLocked) {
		this.accountLocked = accountLocked;
	}

	public Integer getCredentialsExpired() {
		return credentialsExpired;
	}

	public void setCredentialsExpired(Integer credentialsExpired) {
		this.credentialsExpired = credentialsExpired;
	}

	public Collection<GrantedAuthority> getAuthorities() {
		return authorities;
	}

	public void setAuthorities(Collection<GrantedAuthority> authorities) {
		this.authorities = authorities;
	}

	
	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return false;
	}
	

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
