package com.net.base.system.pageTag;

import java.util.ArrayList;
import java.util.List;

public class Pagination<T> {
	public static Integer DEFAULT_PAGESIZE = 10;
	private List<T> list = new ArrayList<T>();
	private int pageno = 1; // 当前页
	private int pagesize = 1;// 每页显示条数
	private int rowstotal;// 总条数

	public Pagination(int pageno, int pagesize, int rowstotal, List<T> list) {
		this.pageno = pageno;
		this.pagesize = pagesize;
		this.rowstotal = rowstotal;
		this.list = list;
	}

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}

	public int getRowstotal() {
		return rowstotal;
	}

	public void setRowstotal(int rowstotal) {
		this.rowstotal = rowstotal;
	}

	public int getPageno() {
		return pageno;
	}

	public void setPageno(int pageno) {
		this.pageno = pageno;
	}

	public int getPagesize() {
		return pagesize;
	}

	public void setPagesize(int pagesize) {
		this.pagesize = pagesize;
	}

	public int getTotalpage() {
		if (this.rowstotal % this.pagesize == 0)
			return this.rowstotal / this.pagesize;
		else {
			return this.rowstotal / this.pagesize + 1;
		}
	}
}
