package com.aten.model.orm;

import java.io.Serializable;
import java.math.BigInteger;
import java.sql.Timestamp;

public class OrderDetailNode implements Serializable{
	private static final long serialVersionUID = -2821441462903181643L;
	private BigInteger detail_id;
	private Timestamp create_time;		//创建时间
	private BigInteger order_id;		//主订单ID
	private String order_number;		//主订单编号
	private String detail_number;		//子订单编号
	private BigInteger goods_id;		//商品ID
	private String goods_name;			//商品名称
	private String sku_name;			//商品SKU名称
	private BigInteger sku_id;			//商品SKU编码
	private int num;					//商品数量
	private BigInteger price;			//商品价格
	private String goods_img;			//商品图片
	private int delivery_state;			//商品发货状态  0:未发货 1:已发货 
	private int state;					//状态   0：未付款（等待买家付款） 1：等待卖家发货(买家已付款) 2：等待买家确认收货（卖家已发货） 3：交易成功 4： 退款中 5： 退款成功（交易关闭） 6： 未付款前，关闭交易 7: 退货退款中 8: 退货退款成功 9:售后中 10: 售后成功
	private String fast_code;			//快递公司
	private String fast_waybill;		//快递单号
	private Timestamp send_time;		//发货时间
	private Timestamp sign_time;		//签收时间
	private BigInteger share_account;	//分享人ID
	private BigInteger fenrun_amount;	//分成金额
	private BigInteger sale_price;		//单价
	private BigInteger total_amount;	//总额
	private int is_delete;				//是否删除    0 删除  , 1 正常
	public OrderDetailNode() {
		super();
	}
	public BigInteger getDetail_id() {
		return detail_id;
	}
	public void setDetail_id(BigInteger detail_id) {
		this.detail_id = detail_id;
	}
	public Timestamp getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Timestamp create_time) {
		this.create_time = create_time;
	}
	public BigInteger getOrder_id() {
		return order_id;
	}
	public void setOrder_id(BigInteger order_id) {
		this.order_id = order_id;
	}
	public String getDetail_number() {
		return detail_number;
	}
	public void setDetail_number(String detail_number) {
		this.detail_number = detail_number;
	}
	public BigInteger getGoods_id() {
		return goods_id;
	}
	public void setGoods_id(BigInteger goods_id) {
		this.goods_id = goods_id;
	}
	public String getGoods_name() {
		return goods_name;
	}
	public void setGoods_name(String goods_name) {
		this.goods_name = goods_name;
	}
	public String getSku_name() {
		return sku_name;
	}
	public void setSku_name(String sku_name) {
		this.sku_name = sku_name;
	}
	public BigInteger getSku_id() {
		return sku_id;
	}
	public void setSku_id(BigInteger sku_id) {
		this.sku_id = sku_id;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public BigInteger getPrice() {
		return price;
	}
	public void setPrice(BigInteger price) {
		this.price = price;
	}
	public String getGoods_img() {
		return goods_img;
	}
	public void setGoods_img(String goods_img) {
		this.goods_img = goods_img;
	}
	public int getDelivery_state() {
		return delivery_state;
	}
	public void setDelivery_state(int delivery_state) {
		this.delivery_state = delivery_state;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public String getFast_code() {
		return fast_code;
	}
	public void setFast_code(String fast_code) {
		this.fast_code = fast_code;
	}
	public String getFast_waybill() {
		return fast_waybill;
	}
	public void setFast_waybill(String fast_waybill) {
		this.fast_waybill = fast_waybill;
	}
	public Timestamp getSend_time() {
		return send_time;
	}
	public void setSend_time(Timestamp send_time) {
		this.send_time = send_time;
	}
	public Timestamp getSign_time() {
		return sign_time;
	}
	public void setSign_time(Timestamp sign_time) {
		this.sign_time = sign_time;
	}
	public BigInteger getShare_account() {
		return share_account;
	}
	public void setShare_account(BigInteger share_account) {
		this.share_account = share_account;
	}
	public BigInteger getFenrun_amount() {
		return fenrun_amount;
	}
	public void setFenrun_amount(BigInteger fenrun_amount) {
		this.fenrun_amount = fenrun_amount;
	}
	public BigInteger getSale_price() {
		return sale_price;
	}
	public void setSale_price(BigInteger sale_price) {
		this.sale_price = sale_price;
	}
	public BigInteger getTotal_amount() {
		return total_amount;
	}
	public void setTotal_amount(BigInteger total_amount) {
		this.total_amount = total_amount;
	}
	public int getIs_delete() {
		return is_delete;
	}
	public void setIs_delete(int is_delete) {
		this.is_delete = is_delete;
	}
	public String getOrder_number() {
		return order_number;
	}
	public void setOrder_number(String order_number) {
		this.order_number = order_number;
	}
}
