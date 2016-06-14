package com.net.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.net.base.dao.UserManagerDao;
import com.net.security.entity.SysUser;
	

/**
 * 
 * Description:认证实现
 * Company:北京健康在线网络技术有限公司
 * Date: Apr 13, 2013
 * Time: 3:28:46 PM
 * @author gongjinzhao@mail.haoyisheng.com
 */
public class MyUserDetailService implements UserDetailsService {

	@Autowired
	UserManagerDao managerDao;
	
	@Override
	public UserDetails loadUserByUsername(String userName)
			throws UsernameNotFoundException, DataAccessException {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userName", userName);
		List<Map> userList = managerDao.findRoleAndOperaType(params);
		if (userList == null || userList.size() <= 0) { 
			   System.out.println("用户" + userName + "不存在!");
		     // throw new UsernameNotFoundException("用户" + userName + "不存在!");   
		      throw new AuthenticationServiceException("用户" + userName + "不存在!");   

		 } 
		
		Map userMap = userList.get(0);
		Collection<GrantedAuthority> auths = obtionGrantedAuthorities(userList);
		//在这个类中，你就可以从数据库中读入用户的密码，角色信息，是否锁定，账号是否过期等，
		boolean enables = true;
		boolean accountNonExpired = true;
		boolean credentialsNonExpired = true;
		boolean accountNonLocked = true;
		User userdetail = new User(userMap.get("userName").toString(), userMap.get("password").toString(),
				enables, accountNonExpired, credentialsNonExpired,
				accountNonLocked, auths);
		
		//setSysUser(userMap, userdetail);
		return userdetail;
     
	}
	
	/**
	 * 
	 * 方法名：
	 * 方法说明:封装用户信息
	 * @param userMap
	 * @param user
	 * @return
	 * <B>修改记录:</B><BR>
	 * Date: 2016年5月25日下午6:13:02
	 * @author gongjzh@net-east.com
	 */
	private SysUser setSysUser(Map userMap,SysUser user){
		
		user.setUserId(userMap.get("userId").toString());
		user.setUserName(userMap.get("userName").toString());
		user.setOrgCode(userMap.get("orgCode").toString());
		user.setAuthorityType(userMap.get("operateType").toString());
		user.setRoleName(userMap.get("roleName").toString());
		user.setStart_date(userMap.get("userStartDate").toString());
		user.setEnd_date(userMap.get("userEndDate").toString());
		user.setRoleStartDate(userMap.get("roleStartDate").toString());
		user.setRoleEndDate(userMap.get("roleEndDate").toString());
		user.setDefaultPage(userMap.get("defaultPage").toString());
		
		return user;
    }
	
	// 取得用户的权限
		private Collection<GrantedAuthority> obtionGrantedAuthorities(List<Map> roleList){
			Collection<GrantedAuthority> auths = new ArrayList<GrantedAuthority>();
			//List<Map> roleList = managerDao.findRoleAndOperaType(user);
			for (@SuppressWarnings("rawtypes") Map icRole : roleList) {
				@SuppressWarnings("deprecation")
				GrantedAuthorityImpl auth2 = new GrantedAuthorityImpl(icRole.get("roleName").toString());
				auths.add(auth2);
			}
			return auths;
		}
	
	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return super.hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		return super.equals(obj);
	}

}
