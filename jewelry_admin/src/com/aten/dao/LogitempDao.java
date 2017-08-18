package com.aten.dao;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.aten.model.orm.Areaset;
import com.aten.model.orm.ReachArea;
import com.aten.model.orm.ShipTemplate;

public interface LogitempDao {
	int selectcountShipTemplateByShipname(HashMap<String, Object> paraMap);
	
	List<ShipTemplate> selectlistShipTemplateByComId(BigInteger comId);
	List<ShipTemplate> selectlistShipTemplateByShipname(HashMap<String, Object> paraMap);
	List<Areaset> selectlistAreasetByShipId(BigInteger ship_id);
	List<ReachArea> selectlistReachAreaByAsId(BigInteger as_id);
	List<ShipTemplate> selectByComId(String com_id);
	
	ShipTemplate selectShipTemplate(BigInteger ship_id);
	ReachArea selectReachArea(BigInteger rea_id);
	Areaset selectAreaset(BigInteger as_id);
	
	BigInteger selectMaxIdShipTemplate();
	BigInteger selectMaxIdReachArea();
	BigInteger selectMaxIdAreaset();

	int insertShipTemplate(ShipTemplate shipTemplate);
	int insertReachArea(ReachArea reachnode);
	int insertAreaset(Areaset areaset);
	
	int updateShipTemplate(ShipTemplate shipTemplate);
	int updateReachArea(ReachArea reachnode);
	int updateAreaset(Areaset areaset);

	int updateShipTemplateState(Map<String,Object> param);
	void deleteShipTemplateStatus(BigInteger ship_id);

	int deleteReachArea(BigInteger _rea_id);
	int deleteAreaset(BigInteger as_id);
	int deleteReachAreaByAsid(BigInteger as_id);

	
}
