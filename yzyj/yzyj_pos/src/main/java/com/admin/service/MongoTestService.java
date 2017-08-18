package com.admin.service;

import com.admin.model.MongoTest;

import java.util.List;
import java.util.Map;


public interface MongoTestService {
	/**
	 * 根据当前用户名查询好友--返回全字段
	 * @param userName
	 * @return
	 */
	Map<String,MongoTest> queryOfrosters(String userName);
	/**
	 * 添加好友
	 * @return
	 */
	String addOfroster(String loginName, String userName);
	/**
	 * 昵称更新，头像更新
	 * @param loginName
	 * @param nickName
	 * @return
	 */
	String updBaseInfo(String loginName, String nickName);
	/**
	 * 备注名更新
	 * @param loginName
	 * @param remarkName
	 * @param userName
	 * @return
	 */
	String updRemarkName(String loginName, String remarkName, String userName);
	/**
	 * 好友删除
	 * @return
	 */
	String removeOfroster(MongoTest mongoTest);
	/**
	 * 
	 * @param loginName
	 * @return
	 */
	Map<String,MongoTest> findOfRoster(String loginName);
	/**
	 * 添加好友
	 * @return
	 */
	String addOfroster(MongoTest ofr);
	
	/**
	 * 好友删除--批量更新专用
	 * @param ofroster
	 * @return
	 */
	String removeOfrosters(List<MongoTest> ofroster);
	/**
	 * 根据当前用户名查询好友--批量更新专用
	 * @param userName
	 * @return
	 */
	Map<String,MongoTest> queryOfrostersB(String userName);
	/**
	 * 查询当前登录名的好友
	 * @param loginName
	 * @param loginNames
	 * @return
	 */
	String listHeadPic(String loginName, String loginNames);
	/***
	 * 查询好友信息
	 * @param logiName
	 * @param loginNames
	 * @return
	 */
	List<MongoTest> queryOfrosters(String logiName, String[] loginNames);
}
