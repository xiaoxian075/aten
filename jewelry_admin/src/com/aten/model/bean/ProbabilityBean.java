package com.aten.model.bean;

/**
 * Created by 陈熠
 * 2017/8/7.
 */
public class ProbabilityBean {
    private String awardName;//奖项名称
    private String awardLevelName;//奖项等级名称
    private int minNum;//最小值
    private int maxNum;//最大值
    private String awardId;//奖项id
    private String shakeId;//活动id

    public String getAwardName() {
        return awardName;
    }

    public void setAwardName(String awardName) {
        this.awardName = awardName;
    }

    public String getAwardLevelName() {
		return awardLevelName;
	}

	public void setAwardLevelName(String awardLevelName) {
		this.awardLevelName = awardLevelName;
	}

	public int getMinNum() {
        return minNum;
    }

    public void setMinNum(int minNum) {
        this.minNum = minNum;
    }

    public int getMaxNum() {
        return maxNum;
    }

    public void setMaxNum(int maxNum) {
        this.maxNum = maxNum;
    }

    public String getAwardId() {
        return awardId;
    }

    public void setAwardId(String awardId) {
        this.awardId = awardId;
    }

    public String getShakeId() {
        return shakeId;
    }

    public void setShakeId(String shakeId) {
        this.shakeId = shakeId;
    }
}
