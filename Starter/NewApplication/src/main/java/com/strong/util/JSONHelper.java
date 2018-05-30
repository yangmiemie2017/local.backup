//package com.strong.util;
//
//import java.io.IOException;
//
//import javax.validation.Valid;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.validation.BindingResult;
//import org.springframework.validation.DataBinder;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.InitBinder;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.bind.annotation.RestController;
//
//import org.springframework.stereotype.Service;
//import org.springframework.util.Assert;
//import org.springframework.util.ReflectionUtils;
//
//import java.lang.reflect.Field;
//import java.lang.reflect.Method;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.Dictionary;
//import java.util.List;
//import java.util.Map;
//
//import com.strong.constant.ErrorCodes;
//import com.strong.model.entity.User;
//import com.strong.model.entity.UserToken;
//import com.strong.exception.BusinessException;
//import com.strong.exception.ErrorDetail;
//import com.strong.exception.InputValidationException;
//import com.strong.exception.ServiceException;
//import com.strong.service.LoginServiceImpl;
//import com.strong.service.RegisterService;
//import com.strong.util.CheckPwdStrengthUtil;
//import com.strong.web.model.RegisterRequest;
//
//import net.sf.json.JSONArray;
//
//import com.strong.web.controller.ApiServiceController;
//import com.strong.web.model.LoginResponse;
//
//public class JSONHelper {
//
//	/**
//	 * jsonArray 转换成 javaBean list
//	 *
//	 * @param jsonStr
//	 *            json格式的String数据
//	 * @param clazz
//	 *            需要转成的bean的.class对象
//	 * @param <T>
//	 *            转化成的bean类型
//	 * @return 集合
//	 * @throws Exception
//	 */
//	public static <T> List<T> parseJsonToBeanList(String jsonStr, Class<T> clazz) throws Exception {
//
//		List<T> list = null; // 包含的实体列表
//
//		list = new ArrayList<T>();
//		JSONArray jArray = new JSONArray(jsonStr);
//
//		for (int i = 0; i < jArray.length(); i++) {
//			JSONObject jso = (JSONObject) jArray.opt(i);
//
//			Field[] fs = clazz.getDeclaredFields();
//
//			T t = clazz.newInstance();
//
//			for (Field field : fs) {
//				String fieldName = field.getName();
//				if ("_id".equals(fieldName)) {
//					// _id根据自定义的bean做相应修改
//					continue;
//				}
//				Method m = clazz.getDeclaredMethod("set" + Tools.toUpperCaseFirstOne(fieldName), field.getType());
//				Object arg = jso.opt(fieldName);
//				if (m != null && m.getName() != null && arg != null) {
//					if (!arg.toString().equals("null") && !arg.toString().equals("")) {
//
//						if (field.getType().getName().equals("int")) {
//							m.invoke(t, Integer.valueOf(arg.toString()));
//						} else if (field.getType().getName().equals("long")) {
//							m.invoke(t, Long.valueOf(arg.toString()));
//						} else if (field.getType().getName().equals("short")) {
//							m.invoke(t, Short.valueOf(arg.toString()));
//						} else {
//							m.invoke(t, arg);
//						}
//					}
//				}
//			}
//			list.add(t);
//		}
//		return list;
//	}
//
//}