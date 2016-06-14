package com.net.base.tag;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import com.net.base.system.pageTag.Pagination;

/**
 * 分页标签，用于生成分页工具条
 * 
 * @author Administrator
 */
public class PageTag extends SimpleTagSupport {
	private Pagination page = null;

	public void setPage(Pagination page) {
		this.page = page;
	}

	public void doTag() throws JspException, IOException {
		JspWriter out = getJspContext().getOut();
		PageContext pageContext = (PageContext) getJspContext();
		HttpServletRequest request = (HttpServletRequest) pageContext
				.getRequest();

		String path = request.getQueryString();
		if (path == null)
			path = "";
		else {
			path = path.replaceAll("^page=\\d*&?", "");
			path = path.replaceAll("&?page=\\d*$", "");
		}
		if (path.length() <= 0)
			path = "?";
		else
			path = "?" + path + "&";
		out.println(getContext(path));
	}

	private String getContext(String path) {
		if (page == null)
			return null;

		StringBuffer sb = new StringBuffer();
		sb.append("<div class=\"dataTables_paginate paging_simple_numbers\">");
		sb.append("<ul class=\"pagination pagination-sm\">");
		if (page.getPageno() > 1) {
			sb.append("<li class=\"paginate_button previous\">");
			sb.append("<a href=\"")
					.append(path)
					.append("page=1\">First</a>");
			sb.append("</li>");
			sb.append("<li class=\"paginate_button previous\">");
			sb.append("<a href=\"")
					.append(path)
					.append("page=")
					.append(page.getPageno() - 1)
					.append("\">Previous</a>");
			sb.append("</li>");
		}
		for (int pageindex = page.getPageno() - 4; pageindex < page.getPageno(); pageindex++) {
			if (pageindex <= 0)
				continue;
			sb.append("<li class=\"paginate_button\">");
			sb.append("<a href=\"").append(path).append("page=")
					.append(pageindex).append("\">").append(pageindex)
					.append("</a>");
			sb.append("</li>");
		}

		sb.append("<li class=\"paginate_button active\">");
		sb.append("<a href=\"javascript:void(0)\">").append(page.getPageno())
				.append("</a>");
		sb.append("</li>");

		for (int pageindex = page.getPageno() + 1; pageindex <= page
				.getPageno() + 4; pageindex++) {
			if (pageindex > page.getTotalpage())
				break;
			sb.append("<li class=\"paginate_button\">");
			sb.append("<a href=\"").append(path)
					.append("page=" + pageindex + "\">").append(pageindex)
					.append("</a>");
			sb.append("</li>");
		}
		if (page.getPageno() < page.getTotalpage()) {
			sb.append("<li class=\"paginate_button next\">");
			sb.append("<a href=\"")
					.append(path)
					.append("page=")
					.append(page.getPageno() + 1)
					.append("\">next</a>");
			sb.append("</li>");
			sb.append("<li class=\"paginate_button next\">");
			sb.append("<a href=\"")
					.append(path)
					.append("page=")
					.append(page.getTotalpage())
					.append("\">last</a>");
			sb.append("</li>");
		}
		sb.append("</ul>");
		sb.append("</div>");

		return sb.toString();
	}
}
