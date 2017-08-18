//package com.aten.annotation;
//
//import java.lang.annotation.Annotation;
//import java.lang.reflect.Method;
//import java.util.List;
//
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
//
//import com.communal.util.PackageUtil;
//
//public class AnnotationParsing {
//
//	public static void main(String[] args) throws ClassNotFoundException {
//
//		String packageName = "com.aten.controller";
//
//		List<String> classNames = PackageUtil.getClassName(packageName);
//		for (String className : classNames) {
//
//			//System.out.println(className);
//
//			Class<?> clazz = Class.forName(className);
//
//			Method[] methods = clazz.getMethods();
//
//			//Method[] methods = BrandController.class.getMethods();
//
//		    for (Method method : methods) {
//		         Annotation[] annotations = method.getAnnotations();
//		         for (Annotation annotation : annotations) {
//		             // 获取注解的具体类型
//		             Class<? extends Annotation> annotationType = annotation.annotationType();
//		             if(RequestMapping.class==annotationType){
//		            	 RequestMapping an = (RequestMapping)annotation;
//		                 System.out.println(annotations);
//		             }
//		             /*if (InitAnnotation.class == annotationType) {
//		                 // 方式一：获取注解的具体的值
//		            	 InitAnnotation an = (InitAnnotation)annotation;
//		                 System.out.println(an.note());
//		                 // 方式二：获取注解的具体的值
//		            	 InitAnnotation iat = (InitAnnotation) annotationType.cast(annotation);
//		                 System.out.println(iat.url());
//
//		                 System.out.println(method.getName()+"()\t" + iat.level());
//
//		                 // 打印出java.lang.annotation.Annotation，注解类其实都实现了Annotation这个接口
//		                 Class<?>[] interfaces = InitAnnotation.class.getInterfaces();
//		                 System.out.println(interfaces[0].getName());
//		             }  */
//		         }
//		    }
//
//		}
//	}
//
//
//
//
//
//}
