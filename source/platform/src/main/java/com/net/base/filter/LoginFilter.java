package com.net.base.filter;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.net.base.util.JsonUtils;
import com.net.base.util.ResponseUtils;

import net.sf.json.JSONObject;


/**
 * 
 * @author Eagle
 *
 */
public class LoginFilter implements Filter{
	
	 
	//无需过滤URL
	private String[] filterProcessesUrl= new String[]{"login.do","captcha.do","login.jsp","index.jsp","webservice","api"};
	//无需过滤资源
	private String  filterTypeString = "css,js,gif,png,jpg,ico";
	
	//需要过滤资源
	private String[] filterTypeFiles ;
	
	//登出路径
    private String loginUrl = "/login.jsp";
    
    //主页面
    private String mainUrl = "/main/main.jsp";
    
   
	@Override
	public void destroy() {
		
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		
		if (!(request instanceof HttpServletRequest)) {
            throw new ServletException("Can only process HttpServletRequest");
        }

        if (!(response instanceof HttpServletResponse)) {
            throw new ServletException("Can only process HttpServletResponse");
        }
		
        HttpServletRequest servletRequest = (HttpServletRequest) request;
		HttpServletResponse servletResponse = (HttpServletResponse) response;
		
		// 获得用户请求的URI
		String uri = servletRequest.getRequestURI();
		String[] fixArray = uri.split("\\.");
		
		String userId = (String) servletRequest.getSession().getAttribute("userId");
		
		// 登陆页面无需过滤
	    if(null != filterProcessesUrl && filterProcessesUrl.length > -1){
	    	for (int i = 0; i< filterProcessesUrl.length;i++) {
	    		if(uri.indexOf(filterProcessesUrl[i]) > -1) {
	    			if(null != userId){
		    			//如果已有登录，直接跳转到主页面
		    			servletResponse.sendRedirect(servletRequest.getContextPath()+mainUrl);
		    			return;
		    		}
	    		    chain.doFilter(servletRequest, servletResponse);
	    		    return;
	    		}
			}
	    	
	    }
	   
	    //判断是否Ajax请求
	    if (servletRequest.getHeader("x-requested-with") != null
				 && servletRequest.getHeader("x-requested-with").equalsIgnoreCase("XMLHttpRequest")){
			  if(null != userId){
				  chain.doFilter(servletRequest, servletResponse);
		   		  return;
			  }
			  returnAjaxMsg(servletResponse);
			  return;
	         
		 }
	    
	    // 无需过滤资源
	    outer:
	    if(filterTypeString.indexOf(fixArray[fixArray.length-1].trim()) > -1) {
	    	//排除需要过滤的资源
	    	if(null != filterTypeFiles && filterTypeFiles.length > 0){
		    	for (String fileName : filterTypeFiles) {
		    		if(uri.indexOf(fileName) > -1){
		   		        break outer;
		   	    	}
				}
	    	}
	    	chain.doFilter(servletRequest, servletResponse);
   		    return;
	    	
	    }
		
		//验证用户登录状态
		
		if(userId == null || "".equals(userId)){
			//如果没有登录，跳转到登录页面
			servletResponse.sendRedirect(servletRequest.getContextPath()+loginUrl);
	    }else{
	    	uri = uri.length()>1 ? uri.substring(0,uri.length()-1):uri;
	    	if(servletRequest.getContextPath().equals(uri)){
	    		servletResponse.sendRedirect(servletRequest.getContextPath()+mainUrl);
	    		return;
	    	}
		    chain.doFilter(servletRequest, servletResponse);  //让目标资源执行，放行
		
	    }
	}
	
	//返回Ajax请求信息
	public void returnAjaxMsg(HttpServletResponse servletResponse){
		try {
			JSONObject json = new JSONObject();
			json.put("isexit", "-1");
			json.put("data", null);
			toMsg(servletResponse, json);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		 
	}
	
	private  void toMsg(HttpServletResponse response,JSONObject jsonObject){
		PrintWriter out = null;
		try {
			ResponseUtils.setJsonHeader(response);
		    out = response.getWriter();

			out.print(JsonUtils.toJson(jsonObject));
			
		} catch (Exception e) {
			// TODO: handle exception
		}finally{
			if(null != out)
			  out.close();
		}
		
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		
		
	}

	public String[] getFilterProcessesUrl() {
		return filterProcessesUrl;
	}

	public void setFilterProcessesUrl(String[] filterProcessesUrl) {
		this.filterProcessesUrl = filterProcessesUrl;
	}

	public String getFilterTypeString() {
		return filterTypeString;
	}

	public void setFilterTypeString(String filterTypeString) {
		this.filterTypeString = filterTypeString;
	}

	public String[] getFilterTypeFiles() {
		return filterTypeFiles;
	}

	public void setFilterTypeFiles(String[] filterTypeFiles) {
		this.filterTypeFiles = filterTypeFiles;
	}

	public String getLoginUrl() {
		return loginUrl;
	}

	public void setLoginUrl(String loginUrl) {
		this.loginUrl = loginUrl;
	}


	
	

}
