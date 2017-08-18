package com.aten.service.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aten.dao.GoodsDao;
import com.aten.dao.LogitempDao;
import com.aten.model.orm.Areaset;
import com.aten.model.orm.AreasetVo;
import com.aten.model.orm.ReachArea;
import com.aten.model.orm.ShipTemplate;
import com.aten.model.orm.ShipTemplateVo;
import com.aten.service.LogitempService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@Service("logitempService")
public class LogitempServiceImpl implements LogitempService{
	//private static final Logger logger = Logger.getLogger(LogitempServiceImpl.class);

	@Autowired
	private LogitempDao dao;
	
	@Autowired
	private GoodsDao goodsDao;
	
//	@Autowired
//	public LogitempServiceImpl(LogitempDao dao) {
//		super();
//		this.dao=dao;
//	}
	

	@Override
	public int selectcountShipTemplateByShipname(HashMap<String, Object> paraMap) {
		return dao.selectcountShipTemplateByShipname(paraMap);
	}
	
	@Override
	public List<ShipTemplate> getlist(HashMap<String, Object> paraMap) {
		return dao.selectlistShipTemplateByShipname(paraMap);
	}

	@Override
	public List<ShipTemplateVo> getlist2(BigInteger com_id) {
		List<ShipTemplateVo> shipTemplateVoList = new ArrayList<ShipTemplateVo>();
		
		List<ShipTemplate> shipTemplateList = dao.selectlistShipTemplateByComId(com_id);
		for (ShipTemplate shipTemplate : shipTemplateList) {
			List<AreasetVo> areasetVoList = new ArrayList<AreasetVo>();
			
			List<Areaset> areasetList = dao.selectlistAreasetByShipId(shipTemplate.getShip_id());
			for (Areaset areaset : areasetList) {
				List<ReachArea> reach_area = dao.selectlistReachAreaByAsId(areaset.getAs_id());
				areasetVoList.add(new AreasetVo(areaset,reach_area));
			}
			
			shipTemplateVoList.add(new ShipTemplateVo(shipTemplate,areasetVoList));
		}
		
		return shipTemplateVoList;
	}

	@Override
	@Transactional
	public boolean insertShipTemplate(ShipTemplateVo vo) {
//		try {
			List<AreasetVo> area_info = vo.getArea_info();
			if (area_info==null)
				return false;
			
			BigInteger maxShip_id = dao.selectMaxIdShipTemplate();
			BigInteger ship_id = new BigInteger("1");
			if (maxShip_id!=null) {
				ship_id = maxShip_id.add(new BigInteger("1"));
			}
			vo.setShip_id(ship_id);
		
		
			for (AreasetVo node : area_info) {
				BigInteger maxAs_id = dao.selectMaxIdReachArea();
				BigInteger as_id = new BigInteger("1");
				if (maxAs_id!=null) {
					as_id = maxAs_id.add(new BigInteger("1"));
				}
				node.setAs_id(as_id);
				node.setShip_id(ship_id);
				
				List<String> listArriveCity = new ArrayList<String>();
				if (node.getDefault_ship()==0) {
					List<ReachArea> reach_area = node.getReach_area();
					if (reach_area==null || reach_area.size()==0) {
						throw new RuntimeException();
					}
					for (ReachArea reachnode : reach_area) {
						BigInteger maxRea_id = dao.selectMaxIdAreaset();
						BigInteger rea_id = new BigInteger("1");
						if (maxRea_id!=null) {
							rea_id = maxRea_id.add(new BigInteger("1"));
						}
						reachnode.setRea_id(rea_id);
						reachnode.setAs_id(as_id);
						
						if(1!=dao.insertReachArea(reachnode)) {
							throw new RuntimeException();
						}
						
						listArriveCity.add(reachnode.getEnd_area());
					}
				}
				
				Areaset areaset = new Areaset(node);
				areaset.setArrive_city(new Gson().toJson(listArriveCity, new TypeToken<List<String>>() {}.getType()));
				if(1!=dao.insertAreaset(areaset)) {
					throw new RuntimeException();
				}
			}
			
			if(1!=dao.insertShipTemplate(new ShipTemplate(vo))) {
				throw new RuntimeException();
			}
//		} catch (Exception e) {
//			logger.error("insertShipTemplate",e);
//			return false;
//		}
		
		return true;
	}

	@Override
	@Transactional
	public boolean updateShipTemplate(ShipTemplateVo vo) {
//		try {
			ShipTemplate ship = dao.selectShipTemplate(vo.getShip_id());
			if (ship==null)
				return false;
			
			if (	//这三个值不允许修改
					vo.getValuation_mode()!=ship.getValuation_mode() ||
					!vo.getExpress_id_str().equals(ship.getExpress_id_str()) ||
					vo.getFree_ship()!=ship.getFree_ship() ) {
				return false;
			}
			
			
			
			List<AreasetVo> area_info = vo.getArea_info();
			if (area_info==null)
				return false;
			
			if(1!=dao.updateShipTemplate(new ShipTemplate(vo))) {
				throw new RuntimeException();
			}
			
			for (AreasetVo node : area_info) {
				BigInteger as_id = node.getAs_id();
				BigInteger ship_id = node.getShip_id();
				if (as_id.compareTo(BigInteger.ZERO)==0) {	//主键为0表示插入
					BigInteger maxAs_id = dao.selectMaxIdReachArea();
					if (maxAs_id==null) {
						maxAs_id = new BigInteger("1");
					} else {
						maxAs_id = maxAs_id.add(new BigInteger("1"));
					}
					
					List<String> listArriveCity = new ArrayList<String>();
					if (node.getDefault_ship()==0) {
						List<ReachArea> reach_area = node.getReach_area();
						if (reach_area==null || reach_area.size()==0) {
							throw new RuntimeException();
						}
						for (ReachArea reachnode : reach_area) {	//reachArea必然都是新增的
							BigInteger maxRea_id = dao.selectMaxIdAreaset();
							if (maxRea_id==null) {
								maxRea_id = new BigInteger("1");
							} else {
								maxRea_id = maxRea_id.add(new BigInteger("1"));
							}
							reachnode.setRea_id(maxRea_id);
							reachnode.setAs_id(node.getAs_id());
							if(1!=dao.insertReachArea(reachnode)) {
								throw new RuntimeException();
							}
							listArriveCity.add(reachnode.getEnd_area());
						}
					}
					
					node.setAs_id(maxAs_id);
					node.setShip_id(vo.getShip_id());
					Areaset areaset = new Areaset(node);
					areaset.setArrive_city(new Gson().toJson(listArriveCity, new TypeToken<List<String>>() {}.getType()));
					if(1!=dao.insertAreaset(areaset)) {
						throw new RuntimeException();
					}
				} else if (ship_id.compareTo(BigInteger.ZERO)==0){	//外键为0表示删除
					if(1!=dao.deleteAreaset(as_id)) {
						throw new RuntimeException();
					}
					dao.deleteReachAreaByAsid(as_id);
				} else {	//否则就是更新
					
					List<String> listArriveCity = new ArrayList<String>();
					if (node.getDefault_ship()==0) {
						List<ReachArea> reach_area = node.getReach_area();
						if (reach_area==null || reach_area.size()==0) {
							throw new RuntimeException();
						}
						dao.deleteReachAreaByAsid(as_id);	//更新的时候，也是先删除，再新增
						for (ReachArea reachnode : reach_area) {
							BigInteger maxRea_id = dao.selectMaxIdAreaset();
							if (maxRea_id==null) {
								maxRea_id = new BigInteger("1");
							} else {
								maxRea_id = maxRea_id.add(new BigInteger("1"));
							}
							reachnode.setRea_id(maxRea_id);
							reachnode.setAs_id(node.getAs_id());
							if(1!=dao.insertReachArea(reachnode)) {
								throw new RuntimeException();
							}
							listArriveCity.add(reachnode.getEnd_area());
						}
					}
					
					Areaset areaset = new Areaset(node);
					areaset.setArrive_city(new Gson().toJson(listArriveCity, new TypeToken<List<String>>() {}.getType()));
					if(1!=dao.updateAreaset(areaset)) {
						throw new RuntimeException();
					}
				}
			}
			
			
//		} catch (Exception e) {
//			logger.error("updateShipTemplate",e);
//			return false;
//		}
		
		return true;
	}

	@Override
	public List<ShipTemplate> selectByComId(String com_id) {
		return dao.selectByComId(com_id);
	}


	@Override
	public boolean setShipTemplateStatus(BigInteger ship_id, int state) {
		ShipTemplate shipTemplate = dao.selectShipTemplate(ship_id);
		if (shipTemplate!=null) {
			if (shipTemplate.getState()!=state) {
				if (goodsDao.getcountByShipTemplate(ship_id)>0) 
					return false;
				Map<String,Object> param = new HashMap<String,Object>();
				param.put("ship_id", ship_id);
				param.put("state", state);
				if (1==dao.updateShipTemplateState(param))
					return true;
			} else {
				return true;
			}
		}
		return false;
	}


	@Override
	@Transactional
	public boolean deleteShipTemplateStatus(BigInteger ship_id) {
		if (goodsDao.getcountByShipTemplate(ship_id)>0) 
			return false;
		List<Areaset> listAreaset = dao.selectlistAreasetByShipId(ship_id);
		for (Areaset areaset : listAreaset) {
			BigInteger as_id = areaset.getAs_id();
			dao.deleteReachAreaByAsid(as_id);
			dao.deleteAreaset(as_id);
		}
		dao.deleteShipTemplateStatus(ship_id);
		return true;
	}


	@Override
	@Transactional
	public boolean batchShipTemplateDelete(List<BigInteger> arr_shipid) {

		boolean result = true;
			for (BigInteger ship_id : arr_shipid) {
				if (goodsDao.getcountByShipTemplate(ship_id)<=0) {
					List<Areaset> listAreaset = dao.selectlistAreasetByShipId(ship_id);
					for (Areaset areaset : listAreaset) {
						BigInteger as_id = areaset.getAs_id();
						dao.deleteReachAreaByAsid(as_id);
						dao.deleteAreaset(as_id);
					}
					dao.deleteShipTemplateStatus(ship_id);
				} else {
					result = false;
				}
			}
		return result;
	}


	@Override
	@Transactional
	public boolean batchShipTemplateUpdateState(List<BigInteger> arr_shipid, int state) {

		boolean result = true;
		for (BigInteger ship_id : arr_shipid) {
			ShipTemplate shipTemplate = dao.selectShipTemplate(ship_id);
			if (shipTemplate!=null) {
				if (shipTemplate.getState()!=state) {
					if (state==0 && goodsDao.getcountByShipTemplate(ship_id)>0) {	//禁用的时候，确保没被使用
						result = false;
						continue;
					}
					Map<String,Object> param = new HashMap<String,Object>();
					param.put("ship_id", ship_id);
					param.put("state", state);
					if (1!=dao.updateShipTemplateState(param)) {
						throw new RuntimeException();
					}
				} else {
					continue;
				}
			} else {
				throw new RuntimeException();
			}
		}

		return result;
	}


	@Override
	public ShipTemplateVo getShipTemplateVo(BigInteger ship_id) {
		ShipTemplate shipTemplate = dao.selectShipTemplate(ship_id);
		if (shipTemplate==null) {
			return null;
		}
		
		List<AreasetVo> listAreasetVo = new ArrayList<AreasetVo>();
		
		List<Areaset> listAreaset = dao.selectlistAreasetByShipId(ship_id);
		if (listAreaset!=null && listAreaset.size()>0) {
			for (Areaset areaset : listAreaset) {
				BigInteger as_id = areaset.getAs_id();
				List<ReachArea> listReachArea = dao.selectlistReachAreaByAsId(as_id);
				if (listReachArea==null) {
					listReachArea = new ArrayList<ReachArea>();
				}
				
				AreasetVo avo = new AreasetVo(areaset,listReachArea);
				listAreasetVo.add(avo);
			}
		}
		
		ShipTemplateVo vo = new ShipTemplateVo(shipTemplate,listAreasetVo);
		return vo;
	}
	
	@Override
	public ShipTemplate getShipTemplate(BigInteger ship_id) {
		ShipTemplate shipTemplate = dao.selectShipTemplate(ship_id);
		if (shipTemplate==null) {
			return null;
		}
		return shipTemplate;
	}
}
