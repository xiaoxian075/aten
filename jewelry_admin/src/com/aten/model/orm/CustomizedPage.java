package com.aten.model.orm;


import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class CustomizedPage implements Serializable {

	private static final long serialVersionUID = 1L;
	private String pageUnique;    //唯一性
	private String pageTitle;    //页面标题
	private Integer pageType;    //页面类型
	private String pageBody;    //页面内容
	private String pageUrl;    //页面地址
	private String createTime;
	private String lastTime;

	private String moduleUnique;    //模块唯一性
	private Integer moduleTaxis;    //模块排序
	private String moduleType;    //模块类型
	private BigDecimal moduleHeight;    //模块高度
	private Integer moduleRowsInterval ;//列间距
	private Integer moduleColsInterval ;//行间距

	private String viewImg;    //展示图片
	private String viewUrl;    //展示地址
	private String viewTaxis;    //展示地址
	private String viewUrlType;    //展示URL类型
	private String viewTitle;
	private String viewPrice;
	private String viewSalePrice;

	public String getPageUnique() {
		return pageUnique;
	}

	public void setPageUnique(String pageUnique) {
		this.pageUnique = pageUnique;
	}

	public String getPageTitle() {
		return pageTitle;
	}

	public void setPageTitle(String pageTitle) {
		this.pageTitle = pageTitle;
	}

	public Integer getPageType() {
		return pageType;
	}

	public void setPageType(Integer pageType) {
		this.pageType = pageType;
	}

	public String getPageBody() {
		return pageBody;
	}

	public void setPageBody(String pageBody) {
		this.pageBody = pageBody;
	}

	public String getPageUrl() {
		return pageUrl;
	}

	public void setPageUrl(String pageUrl) {
		this.pageUrl = pageUrl;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getLastTime() {
		return lastTime;
	}

	public void setLastTime(String lastTime) {
		this.lastTime = lastTime;
	}

	public String getModuleUnique() {
		return moduleUnique;
	}

	public void setModuleUnique(String moduleUnique) {
		this.moduleUnique = moduleUnique;
	}

	public Integer getModuleTaxis() {
		return moduleTaxis;
	}

	public void setModuleTaxis(Integer moduleTaxis) {
		this.moduleTaxis = moduleTaxis;
	}

	public String getModuleType() {
		return moduleType;
	}

	public void setModuleType(String moduleType) {
		this.moduleType = moduleType;
	}

	public BigDecimal getModuleHeight() {
		return moduleHeight;
	}

	public void setModuleHeight(BigDecimal moduleHeight) {
		this.moduleHeight = moduleHeight;
	}

	public Integer getModuleColsInterval() {
		return moduleColsInterval;
	}

	public void setModuleColsInterval(Integer moduleColsInterval) {
		this.moduleColsInterval = moduleColsInterval;
	}

	public Integer getModuleRowsInterval() {
		return moduleRowsInterval;
	}

	public void setModuleRowsInterval(Integer moduleRowsInterval) {
		this.moduleRowsInterval = moduleRowsInterval;
	}

	public String getViewImg() {
		return viewImg;
	}

	public void setViewImg(String viewImg) {
		this.viewImg = viewImg;
	}

	public String getViewUrl() {
		return viewUrl;
	}

	public void setViewUrl(String viewUrl) {
		this.viewUrl = viewUrl;
	}

	public String getViewTaxis() {
		return viewTaxis;
	}

	public void setViewTaxis(String viewTaxis) {
		this.viewTaxis = viewTaxis;
	}

	public String getViewUrlType() {
		return viewUrlType;
	}

	public void setViewUrlType(String viewUrlType) {
		this.viewUrlType = viewUrlType;
	}

	public String getViewTitle() {
		return viewTitle;
	}

	public void setViewTitle(String viewTitle) {
		this.viewTitle = viewTitle;
	}

	public String getViewPrice() {
		return viewPrice;
	}

	public void setViewPrice(String viewPrice) {
		this.viewPrice = viewPrice;
	}

	public String getViewSalePrice() {
		return viewSalePrice;
	}

	public void setViewSalePrice(String viewSalePrice) {
		this.viewSalePrice = viewSalePrice;
	}
}

