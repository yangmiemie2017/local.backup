package com.strong.web.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.strong.util.JsonContainer;

import net.sf.json.JSONObject;

@RestController
public class UploadController {
	private static final Logger logger = LoggerFactory.getLogger(UploadController.class);

	// 文件上传相关代码
	@RequestMapping(value = "upload1", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<?> upload1(@RequestParam("file") MultipartFile file) {
		JSONObject jsonResult = new JSONObject();
		if (file.isEmpty()) {
			jsonResult.put("error", "file is empty!");
			logger.info("jsonResult -- " + jsonResult.toString());
			return ResponseEntity.status(HttpStatus.OK).body(jsonResult);
		}

		// String
		// parameter=parameters.getParameters().get("parameter").toString();
		// logger.info("parameter：" + parameter);

		String fileName = file.getOriginalFilename();
		logger.info("Uploaded file name：" + fileName);

		String suffixName = fileName.substring(fileName.lastIndexOf("."));
		logger.info("uploaded file suffix name：" + suffixName);

		try {
			WriteFile(file, fileName);
			jsonResult.put("upload", fileName + " uploaded successfully!");
		} catch (IllegalStateException e) {
			e.printStackTrace();
			jsonResult.put("error", "Upload failed!");
		}
		logger.info("jsonResult -- " + jsonResult.toString());
		return ResponseEntity.status(HttpStatus.OK).body(jsonResult);
	}

	// 文件上传相关代码
	@RequestMapping(value = "upload0")
	public String upload0(@RequestParam("file") MultipartFile file) {
		if (file.isEmpty()) {
			return "文件为空";
		}
		// 获取文件名
		String fileName = file.getOriginalFilename();
		logger.info("上传的文件名为：" + fileName);
		// 获取文件的后缀名
		String suffixName = fileName.substring(fileName.lastIndexOf("."));
		logger.info("上传的后缀名为：" + suffixName);
		// 文件上传后的路径
		String filePath = "d://temp//upload//";
		// 解决中文问题，liunx下中文路径，图片显示问题
		// fileName = UUID.randomUUID() + suffixName;
		File dest = new File(filePath + fileName);
		// 检测是否存在目录
		if (!dest.getParentFile().exists()) {
			dest.getParentFile().mkdirs();
		}
		try {
			file.transferTo(dest);
			return "上传成功";
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "上传失败";
	}

	// 多文件上传
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public ResponseEntity<?> Upload(HttpServletRequest request) {
		logger.info("upload -- " + request.toString());
		JSONObject jsonResult = new JSONObject();

		String[] data=request.getParameterValues("data");
		JSONObject obj = JSONObject.fromObject(data[0]);
		String parameters=obj.getString("parameters");
		logger.info("parameters -- " + parameters);
		
	     try {
	            // Servlet3.0方式上传文件
	            Collection<Part> parts = request.getParts();

	            String msg="";
	            for (Part part : parts) {
	                if (part.getContentType() != null) {  // 忽略路径字段,只处理文件类型
	                    String path = GetUploadPath();
	                    String fileName=getFileName(part.getHeader("content-disposition"));
	                    logger.info("upload fileName -- " + fileName);
	                    File f = new File(path, fileName);
	                    if (!write(part.getInputStream(), f)) {
	                        throw new Exception("file upload failed!");
	                    }
	                    else{
	                    	msg+=fileName+"\r\n";
	                    }
	                }
	            }
	            if(msg!=""){
	            	jsonResult.put("data", msg+"uploaded successfully!");
	            }
	            else{
	            	jsonResult.put("data", "No file uploaded!");
	            }
	    		logger.info("jsonResult -- " + jsonResult.toString());
	    		return ResponseEntity.status(HttpStatus.OK).body(jsonResult);
	        } catch (Exception e) {
	    		jsonResult.put("data:", "error:"+ e.getMessage());
	    		logger.info("jsonResult -- " + jsonResult.toString());
	    		return ResponseEntity.status(HttpStatus.OK).body(jsonResult);
	        }


	}

	// 多文件上传
	@RequestMapping(value = "/upload2", method = RequestMethod.POST)
	public ResponseEntity<?> Upload2(HttpServletRequest request, HttpServletResponse response) {
		logger.info("upload -- " + request.toString());
		JSONObject jsonResult = new JSONObject();

		MultipartResolver resolver = new CommonsMultipartResolver(request.getSession().getServletContext());
		MultipartHttpServletRequest multipartRequest = resolver.resolveMultipart(request);
		MultipartFile file = multipartRequest.getFile("file");
		// String key = multipartRequest.getParameter("key");

		String fileName = file == null ? "NULL" : file.getOriginalFilename();
		if (file != null && !file.isEmpty()) {
			try {
				WriteFile(file, fileName);

			} catch (Exception e) {
				jsonResult.put("error", "You failed to upload " + fileName + " => " + e.getMessage());
				return ResponseEntity.status(HttpStatus.OK).body(jsonResult);
			}
		} else {
			jsonResult.put("error", "You failed to upload " + fileName + " because the file was empty.");
			return ResponseEntity.status(HttpStatus.OK).body(jsonResult);
		}

		// List<MultipartFile> files = ((MultipartHttpServletRequest)
		// request).getFiles("file");
		// MultipartFile file = null;
		// for (int i = 0; i < files.size(); ++i) {
		// file = files.get(i);
		// if (!file.isEmpty()) {
		// try {
		// WriteFile(file,file.getOriginalFilename());
		//
		// } catch (Exception e) {
		// jsonResult.put("error", "You failed to upload " + i + " => " +
		// e.getMessage());
		// return ResponseEntity.status(HttpStatus.OK).body(jsonResult);
		// }
		// } else {
		// jsonResult.put("error", "You failed to upload " + i + " because the
		// file was empty.");
		// return ResponseEntity.status(HttpStatus.OK).body(jsonResult);
		// }
		// }
		jsonResult.put("upload", " uploaded successfully!");
		logger.info("jsonResult -- " + jsonResult.toString());
		return ResponseEntity.status(HttpStatus.OK).body(jsonResult);
	}
	
	// 多文件上传
	@RequestMapping(value = "/batchupload", method = RequestMethod.POST)
	@ResponseBody
	public String handleFileUpload(HttpServletRequest request) {
		List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("file");
		MultipartFile file = null;
		BufferedOutputStream stream = null;
		for (int i = 0; i < files.size(); ++i) {
			file = files.get(i);
			if (!file.isEmpty()) {
				try {
					byte[] bytes = file.getBytes();
					stream = new BufferedOutputStream(new FileOutputStream(new File(file.getOriginalFilename())));
					stream.write(bytes);
					stream.close();

				} catch (Exception e) {
					stream = null;
					return "You failed to upload " + i + " => " + e.getMessage();
				}
			} else {
				return "You failed to upload " + i + " because the file was empty.";
			}
		}
		return "upload successful";
	}

	private String WriteFile(MultipartFile file, String fileName) {
		File path = null;
		try {
			path = new File(ResourceUtils.getURL("classpath:").getPath());
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		logger.info("path -- " + path.getAbsolutePath());

		File uploadPath = new File(path.getAbsolutePath() + File.separator + "static" + File.separator + "upload");
		if (!uploadPath.exists()) {
			uploadPath.mkdir();
		}

		logger.info("file name -- " + fileName);

		File dest = new File(uploadPath, fileName);

		try {
			file.transferTo(dest);
		} catch (IOException e) {
			e.printStackTrace();
		}
		logger.info("file url -- " + dest.getAbsolutePath());
		return dest.getAbsolutePath();
	}
	
	public static String GetUploadPath(){
		File path = null;
		try {
			path = new File(ResourceUtils.getURL("classpath:").getPath());
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		logger.info("path -- " + path.getAbsolutePath());

		File uploadPath = new File(path.getAbsolutePath() + File.separator + "static" + File.separator + "upload");
		if (!uploadPath.exists()) {
			uploadPath.mkdir();
		}

		logger.info("uploadPath name -- " + uploadPath.getAbsolutePath());
		return uploadPath.getAbsolutePath();
	}
	
	
    public static boolean write(InputStream inputStream, File f) {
        boolean ret = false;

        try (OutputStream outputStream = new FileOutputStream(f)) {

            int read;
            byte[] bytes = new byte[1024];

            while ((read = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, read);
            }
            ret = true;

        } catch (IOException e) {
            e.printStackTrace();

        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return ret;
    }
    
    public static String getFileName(String header) {
        String[] tempArr1 = header.split(";");
        String[] tempArr2 = tempArr1[2].split("=");
        //获取文件名，兼容各种浏览器的写法
        return tempArr2[1].substring(tempArr2[1].lastIndexOf("\\") + 1).replaceAll("\"", "");

    }    
}
