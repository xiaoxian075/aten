package com.aten.service;

import java.util.HashMap;
import java.util.List;

import com.aten.model.orm.Sysconfig;
import com.aten.model.vo.GoodsUpdateVo;

public  interface SysconfigService extends CommonService<Sysconfig, String>{


    void SaveIntegral(String sign_id, String sign_value, String share_id, String share_value);

    void SaveGold(String price_id, String price_value, String gold_id, String gold_value);

	List<GoodsUpdateVo> getGoldValueList(String goldValue);

	List<GoodsUpdateVo> getCustomValue(int goods_id);

	void updateGoldsPrice(String custom_attr_value, String result_value,int goods_id);
}

