package com.aten.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.aten.model.orm.BaseExample;
import com.aten.model.orm.CustomizedPage;
import com.communal.util.FunUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.aten.dao.CustomizedPageDao;
import com.aten.service.CustomizedPageService;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("customizedPageService")
public class CustomizedPageServiceImpl extends CommonServiceImpl<CustomizedPage,String> implements CustomizedPageService{

	private CustomizedPageDao customizedPageDao;

	@Autowired
	public CustomizedPageServiceImpl(CustomizedPageDao customizedPageDao) {
		super(customizedPageDao);
		this.customizedPageDao=customizedPageDao;
	}
	public List<HashMap> selectActivityGoodsList(Map map){
		return customizedPageDao.selectActivityGoodsList(map);
	}
	public Integer selectActivityGoodsCount(Map map){
		Integer retInt = 0;
		try{
			retInt = customizedPageDao.selectActivityGoodsCount(map);
		}catch (Exception e){

		}
		return  retInt;
	}
	public Integer insertPageModule(CustomizedPage customizedPage,String jsonData){
		Integer state = 0;
		/**
		 * 查询 模块是否存在
		 *      模块存在 , 修改操作
		 *          检查View 非空
		 *              检查db view 是否存在
		 *                  存在   , 修改操作
		 *                  不存在 , 新增操作
		 *      模块不存在 , 新增操作
		 */
		BaseExample baseExample = new BaseExample();
		BaseExample.Criteria criteria = baseExample.createCriteria();
		criteria.andEqualTo("page.page_unique",customizedPage.getPageUnique());
		List<CustomizedPage> list = customizedPageDao.selectCustomizedPageMap(baseExample);
		java.util.HashMap map = new java.util.HashMap<>();
		for(CustomizedPage vo : list){
			String viewKey = vo.getModuleUnique()+"&"+vo.getViewTaxis();
			String mobileKey = vo.getModuleUnique();
			//获取 数据 , 封装到 map 中 用于验证是否存在
			map.put(viewKey,vo);
			map.put(mobileKey,"1");
		}
		/**
		 * 修改 主页 内容
		 */
		customizedPageDao.updateCustomizedPage(customizedPage);
		customizedPageDao.deleteView(customizedPage);
		customizedPageDao.deleteModule(customizedPage);

		/**
		 * 解析 页面 传来的 json 对象
		 */
		JSONObject jsonObject  = JSON.parseObject(jsonData);
		JSONObject data = jsonObject.getJSONObject("data");
		JSONArray moduleList = data.getJSONArray("moduleList");
		Integer moduleTaxis = 0;
		for(Object moduleObj:moduleList){
			moduleTaxis ++;
			JSONObject mobileMap = (JSONObject) moduleObj;
			String mobileType = mobileMap.getString("moduleType");
			String mobileUnique = mobileMap.getString("moduleUnique");
			customizedPage.setModuleType(mobileType);
			customizedPage.setModuleUnique(mobileUnique);
			customizedPage.setModuleTaxis(moduleTaxis);
			customizedPage.setModuleHeight(mobileMap.getBigDecimal("moduleHeight"));
			customizedPage.setModuleRowsInterval(mobileMap.getInteger("moduleRowsInterval"));
			customizedPage.setModuleColsInterval(mobileMap.getInteger("moduleColsInterval"));
			/**
			 * 检查模块 , 删除旧模块 , 新增模块操作
			 */

			customizedPageDao.insertModule(customizedPage);
			JSONArray viewList = mobileMap.getJSONArray("viewList");
			Integer viewNum = 0;
			for(Object obj:viewList){
				viewNum ++;
				JSONObject viewMap = (JSONObject) obj;
				String viewUrl = viewMap.getString("viewUrl");
				String viewImg = viewMap.getString("viewImg");
				String viewUrlType = viewMap.getString("viewUrlType");
				String viewTitle = viewMap.getString("viewTitle");
				String viewPrice = FunUtil.yuanTofen(viewMap.getBigDecimal("viewPrice"),100)+"";
				String viewSalePrice =  FunUtil.yuanTofen(viewMap.getBigDecimal("viewSalePrice"),100)+"";
				if (StringUtils.isEmpty(viewUrlType)){
					viewUrlType="-1";
				}
				customizedPage.setViewTaxis(viewNum+"");
				customizedPage.setViewImg(viewImg);
				customizedPage.setViewUrl(viewUrl);
				customizedPage.setViewUrlType(viewUrlType);
				customizedPage.setViewTitle(viewTitle);
				customizedPage.setViewPrice(viewPrice);
				customizedPage.setViewSalePrice(viewSalePrice);
				customizedPageDao.insertView(customizedPage);
			}
		}

		return state;
	}
	public Integer insertView(CustomizedPage customizedPage){
		return customizedPageDao.insertView(customizedPage);
	}
	public Integer updateCustomizedPage(CustomizedPage customizedPage){
		return customizedPageDao.updateCustomizedPage(customizedPage);
	}
	public Integer deletePage(HttpServletRequest request, CustomizedPage customizedPage){
		Integer state  = 0;
		//删除对应图片文件
		BaseExample baseExample = new BaseExample();
		BaseExample.Criteria criteria = baseExample.createCriteria();
		criteria.andEqualTo("page.page_unique",customizedPage.getPageUnique());
		List <CustomizedPage> list = customizedPageDao.selectCustomizedPageMap(baseExample);
		for(CustomizedPage vo : list){
//			String path = request.getRealPath("/") + Constants.CUSTOMIZED_PAGE + vo.getViewImg() ;
//			FunUtil.delFile(path);
		}
		customizedPageDao.deleteView(customizedPage);
		customizedPageDao.deleteModule(customizedPage);
//		delete(customizedPage.getPageUnique());
		return state;
	}

	public java.util.HashMap selectCustomizedPageMap(BaseExample baseExample){
		java.util.HashMap pageMap = new java.util.HashMap();
		try{
			List<CustomizedPage> list = customizedPageDao.selectCustomizedPageMap(baseExample);

			//模块列表
			java.util.ArrayList moduleList = new java.util.ArrayList();

			if(list.size() > 0){
				CustomizedPage cp = list.get(0);
				//获取 自定义页面数据
				pageMap.put("pageTitle",cp.getPageTitle());//页面标题
				pageMap.put("pageType",cp.getPageType());//页面类型
				pageMap.put("pageUrl",cp.getPageUrl());//页面URL
			}

			String moduleUnique = "";
			//模块
			java.util.HashMap moduleMap = new java.util.HashMap();
			//展示列表
			java.util.ArrayList viewList = new java.util.ArrayList();
			for(CustomizedPage customizedPage : list){
				/**
				 * 检查 模块 是否变动
				 */
				java.util.HashMap viewMap = new java.util.HashMap();
				viewMap.put("viewImg",customizedPage.getViewImg());
				viewMap.put("viewUrl",customizedPage.getViewUrl());
				viewMap.put("viewUrlType",customizedPage.getViewUrlType());
				if(moduleUnique.equals(customizedPage.getModuleUnique())){
					//展示块
					viewList.add(viewMap);
				}else{
					if(viewList.size() > 0){
						moduleMap.put("viewList",viewList);
						moduleList.add(moduleMap);
					}
					moduleMap = new java.util.HashMap();
					viewList = new java.util.ArrayList();
					moduleMap.put("moduleType",customizedPage.getModuleType());
					moduleMap.put("moduleHeight",customizedPage.getModuleHeight());
					viewList.add(viewMap);
					moduleUnique = customizedPage.getModuleUnique();
				}
			}
			if(viewList.size() > 0){
				moduleMap.put("viewList",viewList);
				moduleList.add(moduleMap);
			}
			pageMap.put("moduleList",moduleList);
		}catch (Exception e ){
			e.printStackTrace();
		}
		return pageMap;
	}

}




