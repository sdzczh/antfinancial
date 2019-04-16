package com.ant.app.model;

import com.alibaba.fastjson.JSON;

public class AntResult {
	
	private Integer type;/*状态码*/
	
	private Integer exist;/*是否存在*/
	
	private JSON data;/*数据*/
	
	private Integer orderType;/*定单类型 0:买入 1:卖出*/
	
	private Integer dataType;/*定单类型 0:定单信息 1:匹配信息*/
	
	private Double currentProfit;/*当前增值包收益*/
	
	private Double afterProfit;/*下一个增值包利润值*/
	
	private String currentProfitPre;/*当前收益比例*/
	
	private String lastActiveDateTime;/*最后激活用户时间*/
	
	private Integer userUpdateState;/*用户信息修改开关*/
	
	private Double amount;/*操作资金*/
	
	private Integer count;/*总数*/
	
	private String imgName;/*图片地址*/
	
	private String after;/*将要下降指数*/
	
	private String profit;/*当前收益指数*/
	
	private String days;/*距离下次下降天数*/
	
	private Double packageJ;/*J钱包*/
	
	private Double packageD;/*钱包*/
	
	private Double packageZ;/*Z钱包*/
	
	private String codeId;/*验证码ID*/
	
	private JSON contextParam;/*通用参数*/
	
	private JSON order;/*定单*/
	
	//----------------------GET--SET----------------------------
	
	public Integer getType() {
		return type;
	}

	public JSON getOrder() {
		return order;
	}

	public void setOrder(JSON order) {
		this.order = order;
	}

	public JSON getContextParam() {
		return contextParam;
	}

	public void setContextParam(JSON contextParam) {
		this.contextParam = contextParam;
	}

	public String getCodeId() {
		return codeId;
	}

	public void setCodeId(String codeId) {
		this.codeId = codeId;
	}

	public String getDays() {
		return days;
	}

	public void setDays(String days) {
		this.days = days;
	}

	public String getAfter() {
		return after;
	}

	public void setAfter(String after) {
		this.after = after;
	}

	public String getProfit() {
		return profit;
	}

	public void setProfit(String profit) {
		this.profit = profit;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getExist() {
		return exist;
	}

	public void setExist(Integer exist) {
		this.exist = exist;
	}

	public JSON getData() {
		return data;
	}

	public void setData(JSON data) {
		this.data = data;
	}

	public Integer getOrderType() {
		return orderType;
	}

	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
	}

	public Double getCurrentProfit() {
		return currentProfit;
	}

	public void setCurrentProfit(Double currentProfit) {
		this.currentProfit = currentProfit;
	}

	public Double getAfterProfit() {
		return afterProfit;
	}

	public void setAfterProfit(Double afterProfit) {
		this.afterProfit = afterProfit;
	}

	public String getCurrentProfitPre() {
		return currentProfitPre;
	}

	public void setCurrentProfitPre(String currentProfitPre) {
		this.currentProfitPre = currentProfitPre;
	}

	public String getLastActiveDateTime() {
		return lastActiveDateTime;
	}

	public void setLastActiveDateTime(String lastActiveDateTime) {
		this.lastActiveDateTime = lastActiveDateTime;
	}

	public Integer getUserUpdateState() {
		return userUpdateState;
	}

	public void setUserUpdateState(Integer userUpdateState) {
		this.userUpdateState = userUpdateState;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Integer getDataType() {
		return dataType;
	}

	public void setDataType(Integer dataType) {
		this.dataType = dataType;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public String getImgName() {
		return imgName;
	}

	public void setImgName(String imgName) {
		this.imgName = imgName;
	}

	public Double getPackageJ() {
		return packageJ;
	}

	public void setPackageJ(Double packageJ) {
		this.packageJ = packageJ;
	}

	public Double getPackageD() {
		return packageD;
	}

	public void setPackageD(Double packageD) {
		this.packageD = packageD;
	}

	public Double getPackageZ() {
		return packageZ;
	}

	public void setPackageZ(Double packageZ) {
		this.packageZ = packageZ;
	}

	
}
