package com.aten.model.vo;

/**
 * Created by 陈熠
 * 2017/8/17.
 */
public class CashVo {
    private String login_name;
    private String amount;
    private String create_time;
    private String withdraw_note;
    private String audit_state;

    public String getLogin_name() {
        return login_name;
    }

    public void setLogin_name(String login_name) {
        this.login_name = login_name;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getWithdraw_note() {
        return withdraw_note;
    }

    public void setWithdraw_note(String withdraw_note) {
        this.withdraw_note = withdraw_note;
    }

    public String getAudit_state() {
        String state="";
        if(audit_state.equals("0")){
            state="审批中";
        }
        if(audit_state.equals("1")){
            state="审批通过";
        }
        if(audit_state.equals("2")){
            state="审批未通过";
        }
        return state;
    }

    public void setAudit_state(String audit_state) {
        this.audit_state = audit_state;
    }
}
