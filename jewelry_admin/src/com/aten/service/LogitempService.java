package com.aten.service;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;

import com.aten.model.orm.ShipTemplate;
import com.aten.model.orm.ShipTemplateVo;

public interface LogitempService {
	int selectcountShipTemplateByShipname(HashMap<String, Object> paraMap);
	List<ShipTemplate> getlist(HashMap<String, Object> paraMap);
	List<ShipTemplateVo> getlist2(BigInteger com_id);
	boolean insertShipTemplate(ShipTemplateVo vo);
	boolean updateShipTemplate(ShipTemplateVo vo);
	List<ShipTemplate> selectByComId(String com_id);
	boolean setShipTemplateStatus(BigInteger ship_id, int state);
	boolean deleteShipTemplateStatus(BigInteger ship_id);
	boolean batchShipTemplateDelete(List<BigInteger> arr_shipid);
	boolean batchShipTemplateUpdateState(List<BigInteger> arr_shipid, int state);
	ShipTemplateVo getShipTemplateVo(BigInteger ship_id);
	ShipTemplate getShipTemplate(BigInteger ship_id);
}
