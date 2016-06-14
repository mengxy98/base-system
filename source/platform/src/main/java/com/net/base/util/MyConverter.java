package com.net.base.util;

import java.io.File;

import org.apache.batik.apps.rasterizer.DestinationType;
import org.apache.batik.apps.rasterizer.SVGConverter;
import org.apache.batik.apps.rasterizer.SVGConverterException;

/**
 *SVG 转换类，实现将SVG文件转换为常见图片格式文件
 */
public class MyConverter extends SVGConverter{
	
	/**
	 * 转换方法
	 * @param sources SVG文件路径
	 * @param destination 目标文件路径
	 * @param type 转换类型，有 image/png | image/jpeg | application/pdf |　image/svg+xml　可选
	 * @param width 导出图片宽度
	 * @return 目标文件名
	 * @throws SVGConverterException
	 */
	public  String conver(String sources,String destination,String type,float width) throws SVGConverterException {
		
		SVGConverter converter = new MyConverter();
		
		// 设置高度，默认是400
		converter.setHeight(400);
				
		// 设置宽度，传入的值
		converter.setWidth(width);
				
		// 设置svg源文件路径，是一个数组，支持多个文件同时转换
		String[] src = {sources};
		converter.setSources(src);
				

		// 设置图片质量
		converter.setQuality(MAXIMUM_QUALITY);
				
		// 记录文件后缀
		String ext = "";
				
		// 更具传入的类型设置导出类型和文件后缀
		if(type.equals("image/png")) {
			converter.setDestinationType(DestinationType.PNG);
			ext = "png";
		} else if(type.equals("image/jpeg")) {
			converter.setDestinationType(DestinationType.JPEG);
			ext = "jpg";
		} else if(type.equals("application/pdf")) {
			converter.setDestinationType(DestinationType.PDF);
			ext = "pdf";
		} else if (type.equals("image/svg+xml")) {
			converter.setDestinationType(DestinationType.TIFF);
			ext = "tif";
		} else {
			return null;
		}

		// 设置目标文件路径
		converter.setDst(new File(destination+"/chart."+ext));
				
		// 执行导出
		converter.execute();
		return "chart."+ext;
	}
}
