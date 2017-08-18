package com.communal.thirdservice.oss;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.WeakHashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Auth
 */
public class AuthServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static final WeakHashMap<String, Long> CACHE = new WeakHashMap<String, Long>(200);
	
    /**
     * Default constructor. 
     */
    public AuthServlet() {
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("utf-8");
		try(PrintWriter pw = response.getWriter()){
			String sign = request.getParameter("sign");
			String key = request.getParameter("key");//uuid
			String user = request.getParameter("user");//固定的
			
			if(sign == null || sign.length() == 0
					|| key == null || key.length() == 0){
				pw.write(STSAuthUtil.ERROR);
				return ;
			}
			System.out.println("size:" + CACHE.size());
			if(CACHE.containsKey(sign)){//GC前的重复签名无效化
				pw.write(STSAuthUtil.ERROR);
				return ;
			}
			pw.write(STSAuthUtil.getAuth(user,key,sign));
		}
	}

}
