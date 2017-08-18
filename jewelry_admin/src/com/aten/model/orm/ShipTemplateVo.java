package com.aten.model.orm;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.communal.util.AmountUtil;

public class ShipTemplateVo implements Serializable{
	private static final long serialVersionUID = 7298369974994554624L;
	private BigInteger ship_id;
	private BigInteger com_id;
	private BigInteger goods_id;
	private String ship_name;
	private String start_area;
	private String send_time_id;
	private String send_time;
	private int valuation_mode;
	private String express_id_str;
	private int free_ship;
	private int state;
	private Timestamp tem_modify_time;
	private List<AreasetVo> area_info;
	public ShipTemplateVo() {
		super();
	}
	public ShipTemplateVo(BigInteger ship_id, BigInteger com_id, BigInteger goods_id, String ship_name, String start_area,
			String send_time_id, String send_time, int valuation_mode, String express_id_str, int free_ship,
			int state, Timestamp tem_modify_time,List<AreasetVo> area_info) {
		super();
		this.ship_id = ship_id;
		this.com_id = com_id;
		this.goods_id = goods_id;
		this.ship_name = ship_name;
		this.start_area = start_area;
		this.send_time_id = send_time_id;
		this.send_time = send_time;
		this.valuation_mode = valuation_mode;
		this.express_id_str = express_id_str;
		this.free_ship = free_ship;
		this.state = state;
		this.tem_modify_time = tem_modify_time;
		this.area_info = area_info;
	}
	public ShipTemplateVo(ShipTemplate info,List<AreasetVo> area_info) {
		this.ship_id = info.getShip_id();
		this.com_id = info.getCom_id();
		this.goods_id = info.getGoods_id();
		this.ship_name = info.getShip_name();
		this.start_area = info.getStart_area();
		this.send_time_id = info.getSend_time_id();
		this.send_time = info.getSend_time();
		this.valuation_mode = info.getValuation_mode();
		this.express_id_str = info.getExpress_id_str();
		this.free_ship = info.getFree_ship();
		this.state = info.getState();
		this.tem_modify_time = info.getTem_modify_time();
		this.area_info = area_info;
	}
	public ShipTemplateVo(ShipTemplatePa info) {
		this.ship_id = info.getShip_id();
		this.com_id = info.getCom_id();
		this.goods_id = info.getGoods_id();
		this.ship_name = info.getShip_name();
		this.start_area = info.getStart_area();
		this.send_time_id = info.getSend_time_id();
		this.send_time = info.getSend_time();
		this.valuation_mode = info.getValuation_mode();
		this.express_id_str = info.getExpress_id_str();
		this.free_ship = info.getFree_ship();
		this.state = info.getState();
		this.tem_modify_time = Timestamp.valueOf(info.getTem_modify_time());
		
		this.area_info = new ArrayList<AreasetVo>();
		List<AreasetPa> listPa = info.getArea_info();
		for (AreasetPa pa : listPa) {
			List<ReachArea> listRa = new ArrayList<ReachArea>();
			List<ReachAreaPa> paList = pa.getReach_area();
			for (ReachAreaPa rpa : paList) {
				listRa.add(new ReachArea(rpa.getRea_id(),rpa.getAs_id(),rpa.getEnd_area()));
			}
			area_info.add(new AreasetVo(
					pa.getAs_id(),
					pa.getShip_id(),
					pa.getExpress_start(),
					new BigDecimal(AmountUtil.yuanToFen(pa.getExpress_price())),
					//pa.getExpress_price(),
					pa.getExpress_plus(),
					new BigDecimal(AmountUtil.yuanToFen(pa.getExpress_priceplus())),
					//pa.getExpress_priceplus(),
					pa.getExpress_id(),
					pa.getDefault_ship(),
					listRa));
		}
	}
	public BigInteger getShip_id() {
		return ship_id;
	}
	public void setShip_id(BigInteger ship_id) {
		this.ship_id = ship_id;
	}
	public BigInteger getCom_id() {
		return com_id;
	}
	public void setCom_id(BigInteger com_id) {
		this.com_id = com_id;
	}
	public BigInteger getGoods_id() {
		return goods_id;
	}
	public void setGoods_id(BigInteger goods_id) {
		this.goods_id = goods_id;
	}
	public String getShip_name() {
		return ship_name;
	}
	public void setShip_name(String ship_name) {
		this.ship_name = ship_name;
	}
	public String getStart_area() {
		return start_area;
	}
	public void setStart_area(String start_area) {
		this.start_area = start_area;
	}
	public String getSend_time_id() {
		return send_time_id;
	}
	public void setSend_time_id(String send_time_id) {
		this.send_time_id = send_time_id;
	}
	public String getSend_time() {
		return send_time;
	}
	public void setSend_time(String send_time) {
		this.send_time = send_time;
	}
	public int getValuation_mode() {
		return valuation_mode;
	}
	public void setValuation_mode(int valuation_mode) {
		this.valuation_mode = valuation_mode;
	}
	public String getExpress_id_str() {
		return express_id_str;
	}
	public void setExpress_id_str(String express_id_str) {
		this.express_id_str = express_id_str;
	}
	public int getFree_ship() {
		return free_ship;
	}
	public void setFree_ship(int free_ship) {
		this.free_ship = free_ship;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public Timestamp getTem_modify_time() {
		return tem_modify_time;
	}
	public void setTem_modify_time(Timestamp tem_modify_time) {
		this.tem_modify_time = tem_modify_time;
	}
	public List<AreasetVo> getArea_info() {
		return area_info;
	}
	public void setArea_info(List<AreasetVo> area_info) {
		this.area_info = area_info;
	}
}
