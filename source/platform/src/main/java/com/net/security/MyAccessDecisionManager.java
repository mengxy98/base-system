package com.net.security;

import java.util.Collection;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

/**
 * 在这个类中，最重要的是decide方法，如果不存在对该资源的定义，直接放行；
 * 否则，如果找到正确的角色，即认为拥有权限，并放行，
 * 否则throw new AccessDeniedException("no right");这样，就会进入上面提到的403.jsp页面。
 */
public class MyAccessDecisionManager implements AccessDecisionManager {

	@Override
	public void decide(Authentication authentication, Object object,
			Collection<ConfigAttribute> ConfigAttributes) throws AccessDeniedException,
			InsufficientAuthenticationException {
		if(ConfigAttributes == null){
			return;
		}
		System.out.println(object.toString());
		for (ConfigAttribute configAttribute : ConfigAttributes) {
			String needRole = ((SecurityConfig)configAttribute).getAttribute();
			for (GrantedAuthority ga : authentication.getAuthorities()) {
				if(needRole.equals(ga.getAuthority())){
					return;
				}
			}
		}
		 throw new AccessDeniedException("no right");

		
	}

	@Override
	public boolean supports(ConfigAttribute arg0) {
		return true;
	}

	@Override
	public boolean supports(Class<?> arg0) {
		return true;
	}

}
