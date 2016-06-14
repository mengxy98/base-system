package com.net.base.web.plugin;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.net.base.util.MyConverter;



/**
 * SVG 转换类，实现将SVG文件转换为常见图片格式文件
 * @author Zhy
 * 
 * publish on Highcharts中文网  http://www.hcharts.cn 
 *
 * 导出步骤：
 * 	1、接受页面提交的参数   （ 可以将参数打印出来以确保页面提交过来的代码不会乱码）
 * 	2、将svg代码保存为.svg文件 （保存文件时注意编码）
 * 	3、执行转换函数，将.svg文件转换成目标文件
 * 	4、读取目标文件，并调用 浏览器下载
 */
@Controller
@RequestMapping("/plugin")
public class ExportingController  {
	
	Logger log = Logger.getLogger(ExportingController.class);
	
	// 导出函数
	@RequestMapping("/export.do")
	public void  export(HttpServletRequest request ,HttpServletResponse response ) throws Exception{
		String path = request.getContextPath();
		String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
		
		String type = (String)request.getParameter("type");
		String svg = (String)request.getParameter("svg");
		String width = (String)request.getParameter("width");
		String filename = (String)request.getParameter("filename");
		

		//File svgFile = new File(path+"temp");
		/**
		 * 第二步：将svg代码保存为svg文件
		 */
		
		// 打印获取的参数，确保可以获取值且中文不会乱码，如果出现乱码，请将你的Highcharts页面的编码设置为UTF-8
		// System.out.println(type + "\n" + filename + "\n" +svg + "\n" + width+"\n"+scale); 
		
		// 获取项目的绝对路径
		String WebRoot = request.getSession().getServletContext().getRealPath("/")+"/temp/";
		WebRoot = WebRoot.replace("\\","/");
		// SVG临时文件名
		String temp = WebRoot+System.currentTimeMillis()+(int)(Math.random()*1000)+".svg";

		// 将svg代码写入到临时文件中，文件后缀的.svg
		File svgTempFile = new File(temp);
		//写入文件，注意文件编码
		OutputStreamWriter svgFileOsw = new OutputStreamWriter(new FileOutputStream(svgTempFile),"UTF-8");
		svgFileOsw.write(svg);
		svgFileOsw.flush();
		svgFileOsw.close();
		
		/**
		 * 第三步：调用转换函数，生成目标文件
		 */
		MyConverter mc = new MyConverter();
		// 调用转换函数，返回目标文件名
		filename = mc.conver(temp,WebRoot, type, Float.valueOf(width));
		
		log.info("create temp img file : " + filename);
		// 读取目标文件流，转换调用下载
		File resultFile = new File(WebRoot+filename);
		FileInputStream resultFileFi = new FileInputStream(resultFile);
		long l = resultFile.length();
		int k = 0;
		byte abyte0[] = new byte[65000];
		
		/**
		 * 第四步：调用浏览器下载
		 */
		
		// 调用下载
		response.setContentType("application/x-msdownload");
		response.setContentLength((int) l);
		response.setHeader("Content-Disposition", "attachment; filename=" + filename);
		while ((long) k < l) {
			int j;
			j = resultFileFi.read(abyte0, 0, 65000);
			k += j;
			response.getOutputStream().write(abyte0, 0, j);
		}
		resultFileFi.close();
		
		// 转换成功后，删除临时文件
		svgTempFile.delete();
		resultFile.delete();		
	}

}
