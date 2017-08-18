package com.aten.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.aten.model.orm.Sysconfig;
import com.aten.model.vo.GoodsUpdateVo;

public interface SysconfigDao extends CommonDao<Sysconfig, String> {

	List<GoodsUpdateVo> getGoldValueList(@Param("goldValue") String goldValue);

	List<GoodsUpdateVo> getCustomValue(@Param("goods_id") int goods_id);

	void updateGoldsPrice(@Param("custom_attr_value") String custom_attr_value, 
						  @Param("result_value") String result_value,
						  @Param("goods_id") int goods_id);

}
