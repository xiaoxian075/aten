package com.aten.function;

import java.util.HashMap;
import java.util.List;

import com.aten.model.orm.Power;
import com.aten.model.orm.Sysmenu;
import com.aten.service.PowerService;
import com.aten.service.SysmenuService;

public class PowerFuc extends SpringContextFuc {

	private static PowerService powerService = (PowerService) getContext().getBean("powerService");

	/**
	 * @author linjunqin
	 * @Description 根据条件获取菜单列表
	 * @param
	 * @date 2017-1-3 下午3:08:12
	 */
	public static String getPowerIdByUrl(String url) {
		String power_id = "0";// 默认不进行权限过滤，如果该权限需要过滤则返回权限的power_id
		HashMap<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("url_vague", url);
		List<Power> powerList = powerService.getList(paraMap);
		if (powerList != null && powerList.size() > 0) {
			Power power = powerList.get(0);
			if ("1".equals(power.getIs_control_power())) {
				power_id = powerList.get(0).getPower_id();
			}
		}
		return power_id;
	}
}
