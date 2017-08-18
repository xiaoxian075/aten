package com.aten.model.vo;

import java.math.BigInteger;
import java.sql.Timestamp;

/**
 * Created by 陈熠
 * 2017/8/17.
 */
public class BillFlowVo {
    private String trade_id;		//交易流水号
    private String pay_way_name;	//支付方式名称
    private String bill_amount;	//金额
    private Timestamp bill_time;	//帐单时间
    private String order_type;
    private String bill_type;
    private String ralation_id;		//关联单号
    private String note;			//备注

    public String getTrade_id() {
        return trade_id;
    }

    public void setTrade_id(String trade_id) {
        this.trade_id = trade_id;
    }

    public String getPay_way_name() {
        return pay_way_name;
    }

    public void setPay_way_name(String pay_way_name) {
        this.pay_way_name = pay_way_name;
    }

    public void setBill_amount(String bill_amount) {
        this.bill_amount = bill_amount;
    }

    public Timestamp getBill_time() {
        return bill_time;
    }

    public void setBill_time(Timestamp bill_time) {
        this.bill_time = bill_time;
    }

    public String getOrder_type() {
        return order_type;
    }

    public void setOrder_type(String order_type) {
        this.order_type = order_type;
    }

    public String getBill_amount() {
        return bill_amount;
    }

    public String getBill_type() {
        return bill_type;
    }

    public void setBill_type(String bill_type) {
        this.bill_type = bill_type;
    }

    public String getRalation_id() {
        return ralation_id;
    }

    public void setRalation_id(String ralation_id) {
        this.ralation_id = ralation_id;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
