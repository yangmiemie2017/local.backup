package com.strong.web.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.strong.util.JsonContainer;
import com.strong.web.model.LoginResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@RestController
//@RequestMapping("/download")
public class DownloadController {
	  private static final Logger logger = LoggerFactory.getLogger(DownloadController.class);

	    @RequestMapping(value = "/download", method = RequestMethod.POST, produces = MediaType.MULTIPART_FORM_DATA_VALUE)
	    public ResponseEntity<InputStreamResource> downloadFile(@RequestBody JsonContainer parameters) {
	    	try {
	    			String fileName=parameters.getParameters().get("file").toString();

	    			String filePath=WriteFile(fileName);	
	    			FileSystemResource file = new FileSystemResource(filePath);
	    	        HttpHeaders headers = new HttpHeaders();
	    	        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
	    	        headers.add("Content-Disposition", String.format("attachment; filename=\"%s\"", file.getFilename()));
	    	        headers.add("Pragma", "no-cache");
	    	        headers.add("Expires", "0");
	    	        headers.add("x-filename", file.getFilename());
	    	        headers.add("content-type", "application/octet-stream");
	    	 
	    	        return ResponseEntity
	    	                .ok()
	    	                .headers(headers)
	    	                .contentLength(file.contentLength())
	    	                .contentType(MediaType.parseMediaType("application/octet-stream"))
	    	                .body(new InputStreamResource(file.getInputStream()));	 
	        } catch (IOException e) {
	                    e.printStackTrace();
	        }
	        return null;
	    }
	  
	  //文件下载相关代码
	  @RequestMapping("/download0")
	  public String downloadFile(HttpServletRequest request, HttpServletResponse response){
	    String fileName = "testdownload.txt";
	    if (fileName != null) {
	      //当前是从该工程的document//File//下获取文件(该目录可以在下面一行代码配置)然后下载到C:\\users\\downloads即本机的默认下载的目录
	      //String realPath = request.getServletContext().getRealPath("//document//");
	      String realPath = "d://temp//download//";
	      File file = new File(realPath, fileName);
	      if (file.exists()) {
	        response.setContentType("application/force-download");// 设置强制下载不打开
	        response.addHeader("Content-Disposition",
	            "attachment;fileName=" + fileName);// 设置文件名
	        byte[] buffer = new byte[1024];
	        FileInputStream fis = null;
	        BufferedInputStream bis = null;
	        try {
	          fis = new FileInputStream(file);
	          bis = new BufferedInputStream(fis);
	          OutputStream os = response.getOutputStream();
	          int i = bis.read(buffer);
	          while (i != -1) {
	            os.write(buffer, 0, i);
	            i = bis.read(buffer);
	          }
	          System.out.println("success");
	        } catch (Exception e) {
	          e.printStackTrace();
	        } finally {
	          if (bis != null) {
	            try {
	              bis.close();
	            } catch (IOException e) {
	              e.printStackTrace();
	            }
	          }
	          if (fis != null) {
	            try {
	              fis.close();
	            } catch (IOException e) {
	              e.printStackTrace();
	            }
	          }
	        }
	      }
	    }
	    return null;
	  }

	@RequestMapping(value = "/getFileUrl", method = RequestMethod.POST)
	public ResponseEntity<?> getFile(@RequestBody JsonContainer parameters) throws FileNotFoundException {
		// 第1步、使用File类找到一个文件
		
		String fileName=parameters.getParameters().get("file").toString();

		WriteFile(fileName);		
		
		String result="download"+File.separator +fileName;

		JSONObject jsonResult = new JSONObject();
		jsonResult.put("file", result);
		logger.info("jsonResult -- " +jsonResult.toString());
		
		return ResponseEntity.status(HttpStatus.OK).body(jsonResult);
	}
	
	private String WriteFile(String fileName){
		File path = null;
		try {
			path = new File(ResourceUtils.getURL("classpath:").getPath());
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		logger.info("path -- " +path.getAbsolutePath());
		
		File downloadPath = new File(path.getAbsolutePath() + File.separator + "static" + File.separator + "download");
		if (!downloadPath.exists()) {
			downloadPath.mkdir();
		}

		logger.info("file name -- " +fileName);		
		
		File file = new File(downloadPath, fileName);

		logger.info("file url -- " +file.getAbsolutePath());

		try {
			BufferedWriter out = new BufferedWriter(new FileWriter(file, true));
			out.write("Hello :");
			out.write((new Date()).toString());
			out.newLine(); // 注意\n不一定在各种计算机上都能产生换行的效果

			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}		
		return file.getAbsolutePath();
	}
}
