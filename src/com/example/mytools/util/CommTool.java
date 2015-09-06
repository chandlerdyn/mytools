package com.example.mytools.util;

public class CommTool {
	
	
	/**
	 * 根据文件名后缀获取文件的mime
	 * @param filename  文件名称，必须要有后缀
	 * @param mimetype 默认的mime值
	 * @return
	 */
	public static String getFileMimeType(String filename,String mimetype) {
		
		String fileSuffix = filename.substring(filename.lastIndexOf(".")+1);
		fileSuffix = fileSuffix.toLowerCase();
		
		if("doc".equals(fileSuffix)||"docx".equals(fileSuffix)) {
			mimetype = "application/msword";
		}else if("xls".equals(fileSuffix)||"xlsx".equals(fileSuffix)) {
			mimetype = "application/vnd.ms-excel";
		}else if("ppt".equals(fileSuffix)||"pptx".equals(fileSuffix)) {
			mimetype = "application/vnd.ms-powerpoint";
		}else if("pdf".equals(fileSuffix)) {
			mimetype = "application/pdf";
		}else if("txt".equals(fileSuffix)) {
			mimetype = "text/plain";
		}else if("zip".equals(fileSuffix)) {
			mimetype = "application/zip";
		}else if("rar".equals(fileSuffix)) {
			mimetype = "application/x-rar-compressed";
		}else if("jpg".equals(fileSuffix)) {
			mimetype = "image/*";
		}else if("png".equals(fileSuffix)) {
			mimetype = "image/*";
		}
		
		return mimetype;
	}

}
