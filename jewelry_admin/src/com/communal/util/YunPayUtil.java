package com.communal.util;

import java.util.HashMap;
import org.apache.log4j.Logger;
import net.sf.json.JSONObject;
import com.aten.function.SysconfigFuc;
import com.aten.model.bean.HcRequestData;
import com.communal.util.HttpClientUtil;
import com.communal.util.YunPayUtil;


public abstract class YunPayUtil {
	
	private static final Logger logger = Logger.getLogger(YunPayUtil.class);

	//云支付用户注册
	public static String YUNPAY_REGIST = SysconfigFuc.getSysValue("cfg_yunpay_regist");
	//用户支付
	public static String YUNPAY_YUNPAY = SysconfigFuc.getSysValue("cfg_yunpay");;
	//验证支付密码
	public static String YUNPAY_YUNPAY_PWD = SysconfigFuc.getSysValue("cfg_yunpay_pwd");;
	//验证是否已存在该账号
	public static String YUNPAY_CHECK_USER =SysconfigFuc.getSysValue("cfg_yunpay_check_user");;
	//云支付用户登录
	public static String YUNPAY_LOGIN = SysconfigFuc.getSysValue("cfg_yunpay_login");
	
	/**
	 * @author linjunqin
	 * @Description 云支付检测帐户是否存在
	 * @param  
	 * @date 2017-2-9 上午9:30:10
	 */
	public static boolean yunPayCheckUser(HashMap<String,String> paraMap) {
		HcRequestData hrd =HttpClientUtil.postUrl(YUNPAY_CHECK_USER,paraMap);
		//请求成功
		if(hrd.getStateCode()==200){
			JSONObject jsonObject = JSONObject.fromObject(hrd.getResult());
			Integer resultIs = (Integer) jsonObject.get("is");
			//返回0表示存该用户
			if (resultIs.intValue() == 0) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * @author linjunqin
	 * @Description 注册云支付帐户
	 * @param
	 * @date 2017-2-9 上午10:57:37
	 */
	public static String registerYunPayAccount(HashMap<String,String> paraMap){
		HcRequestData hrd =HttpClientUtil.postUrl(YUNPAY_REGIST,paraMap);
		if(hrd.getStateCode()==200){
			return hrd.getResult();
		}
		return null; 
	}
	
	/**
	 * @author linjunqin
	 * @Description 云支付帐户登录
	 * @param
	 * @date 2017-2-10 上午10:47:58
	 */
	public static String loginYunPayAccount(HashMap<String,String> paraMap){
		HcRequestData hrd =HttpClientUtil.postUrl(YUNPAY_LOGIN,paraMap);
		if(hrd.getStateCode()==200){
			return hrd.getResult();
		}
		return null; 
	}
	
	
	/**
	 * @author linjunqin
	 * @Description 验证支付密码
	 * @param
	 * @date 2017-3-15 上午9:16:01
	 */
	public static String checkPayPwd(HashMap<String,String> paraMap){
		HcRequestData hrd =HttpClientUtil.postUrl(YUNPAY_YUNPAY_PWD,paraMap);
		if(hrd.getStateCode()==200){
			return hrd.getResult();
		}
		return null; 
	}
	
	/**
	 * @author linjunqin
	 * @Description 支付方式减金额
	 * @param
	 * @date 2017-3-15 上午9:22:46
	 */
	public static String yunPay(HashMap<String,String> paraMap){
		HcRequestData hrd =HttpClientUtil.postUrl(YUNPAY_YUNPAY,paraMap);
		if(hrd.getStateCode()==200){
			return hrd.getResult();
		}
		return null; 
	}
	
	
	public static void main(String args[]){
		registerYunPayAccount(null);
	}
	
	
	
	
}