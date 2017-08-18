package com.aten.service.impl;

import java.util.HashMap;
import java.util.List;

import com.aten.model.orm.Organize;
import com.aten.service.GoodsService;
import com.aten.service.NewsService;
import com.communal.util.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aten.dao.CatDao;
import com.aten.model.orm.Cat;
import com.aten.service.CatService;

@Service("catService")
@Transactional
public class CatServiceImpl extends CommonServiceImpl<Cat, String> implements CatService {

	private CatDao catDao;
	@Autowired
	private GoodsService goodsService;
	@Autowired
	private NewsService newsService;

	@Autowired
	public CatServiceImpl(CatDao catDao) {
		super(catDao);
		this.catDao = catDao;
	}

	public List<Cat> selectCatByPid(String pid) {
		return catDao.selectCatByPid(pid);
	}

	@Override
	public List<Cat> getSon(String pId) {
		return catDao.getSon(pId);
	}

	/**
	 * @param
	 * @author chenyi
	 * @Description 禁用
	 * @date 2017/7/3 20:47
	 **/
	public void limitState(String parameter_id) {
		Cat cat = get(parameter_id);
		cat.setState("0");
		cat.setCat_id(parameter_id);
		this.update(cat);
		// 禁用下级地区
		updateSon(parameter_id, "0");
	}

	/**
	 * @param
	 * @author chenyi
	 * @Description 批量禁用
	 * @date 2017/7/3 20:47 //
	 **/
	public void batchLimitState(String parameter_id) {
		// 转成数组
		String[] ids = parameter_id.split(",");
		if (ids != null) {
			for (String id : ids) {
				Cat cat = get(id);
				cat.setState("0");
				cat.setCat_id(id);
				update(cat);
				// 禁用下级地区
				updateSon(id, "0");
			}
		}

	}

	@Override
	public void enableState(String parameter_id) {
		Cat cat = get(parameter_id);
		cat.setState("1");
		cat.setCat_id(parameter_id);
		this.update(cat);
		// 禁用下级地区
		updateSon(parameter_id, "1");
	}

	@Override
	public void batchEnableState(String parameter_id) {
		// 转成数组
		String[] ids = parameter_id.split(",");
		if (ids != null) {
			for (String id : ids) {
				Cat cat = get(id);
				cat.setState("1");
				cat.setCat_id(id);
				update(cat);
				// 禁用下级地区
				updateSon(id, "1");
			}
		}
	}

	@Override
	public List<Cat> getListAndDivideRate(HashMap<String, Object> paraMap) {
		return catDao.getListAndDivideRate(paraMap);
	}

	@Override
	public List<Cat> queryList(Query query) {
		return catDao.queryList(query);
	}

	@Override
	public List<Cat> lastLevelList(Query query) {
		return catDao.lastLevelList(query);
	}

	@Override
	public int lastLevelListCount(Query query) {
		return catDao.lastLevelListCount(query);
	}

	@Override
	public void deletePre(String parameter_id) {
		String[] ids = parameter_id.split(",");
		for (String id : ids) {
			deleteOne(id);
			// 删除下级
			deleteSon(id);
		}
		// 验证提示信息
	}

	@Override
	public void deleteGoodsClass(String parameter_id) {
		String[] ids = parameter_id.split(",");
		for (String id : ids) {
			if (!checkGoodsClass(id)) {
				deleteOne(id);
				// 删除下级
				deleteSonAndCheck(id);
			}

		}

	}

	/**
	 * @param
	 * @author chenyi
	 * @Description 是否有被引用 或下级是否被引用
	 * @date date("yyyy-MM-dd")
	 */
	public boolean checkGoodsClass(String cat_id) {
		boolean flag = goodsService.checkGoodsClass(cat_id);
		List<Cat> catList = getSon(cat_id);
		for (Cat cat : catList) {
			flag = checkSonGoodsClass(cat.getCat_id());
			if (flag) {
				return flag;
			}
			flag = checkGoodsClass(cat.getCat_id());
		}
		return flag;
	}

	public boolean checkSonGoodsClass(String cat_id) {
		boolean flag = goodsService.checkGoodsClass(cat_id);
		return flag;
	}

	/**
	 * @param
	 * @author chenyi
	 * @Description 是否是下级目录
	 * @date 2017-1-5 下午2:29:42
	 */
	@Override
	public Boolean isSon(String cat_id, String parent_id) {
		List<Cat> catList = getSon(cat_id);
		if (catList != null && catList.size() > 0) {
			for (Cat cat : catList) {
				// 如果选择的上级部门是下级部门 返回true
				if (parent_id.equals(cat.getCat_id())) {
					// 是子孙目录
					return true;
				}
				// 递归继续查找下级部门
				isSon(cat.getCat_id(), parent_id);
			}
		}
		return false;
	}
	// @Override
	// public Boolean isSon(String level_cat,String parent_id) {
	// if(level_cat.indexOf(level_cat)!=-1){
	// return true;
	// }
	// return false;
	// }

	/**
	 * @param
	 * @author chenyi
	 * @Description 递归删除下级
	 */
	private Boolean deleteSon(String id) {
		List<Cat> list = getSon(id);
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				Cat cat = list.get(i);
				// 递归删除下级
				deleteOne(cat.getCat_id());
				deleteSon(cat.getCat_id());
			}
		}
		return true;
	}

	private void deleteSonAndCheck(String id) {
		List<Cat> list = getSon(id);
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				Cat cat = list.get(i);
				// 递归删除下级
				if (!checkGoodsClass(cat.getCat_id())) {
					deleteOne(cat.getCat_id());
				}
				deleteSon(cat.getCat_id());
			}
		}

	}

	/**
	 * @param
	 * @author chenyi
	 * @Description 禁用或启用下级
	 * @date 2017/7/3 20:47
	 **/
	private void updateSon(String pId, String state) {
		List<Cat> catList = getSon(pId);
		if (catList != null) {
			for (int i = 0; i < catList.size(); i++) {
				Cat cat = catList.get(i);
				cat.setState(state);
				update(cat);
				// 禁用下级地区
				updateSon(cat.getCat_id(), state);
			}
		}
	}

	/**
	 * @param
	 * @author chenyi
	 * @Description 设置分类串
	 * @date 2017-6-23
	 */
	public void setLevel(String cat_id) {
		List<Cat> catList = getSon(cat_id);
		setLevelCat(catList);

	}

	public void setLevelCat(List<Cat> list) {
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				setStr(list.get(i), list.get(i).getCat_id());
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("parent_cat_id", list.get(i).getCat_id());
				List<Cat> catList = getList(map);
				setLevelCat(catList);
			}
		}

	}

	public void setStr(Cat cat, String level_cat) {
		String catId = level_cat.substring(0, 10);
		String parentId = get(catId).getParent_cat_id();
		if ("1111111111".equals(parentId)) {
			cat.setLevel_cat("1111111111," + level_cat);
			update(cat);
		} else {
			Cat parentCat = get(parentId);
			if (parentCat != null || !"".equals(parentCat)) {
				level_cat = parentCat.getCat_id() + "," + level_cat;
				setStr(cat, level_cat);
			}

		}

	}

	@Override
	public String deleteNewsClass(String parameter_id) {
		String[] ids = parameter_id.split(",");
		for (String id : ids) {
			if (id.equals("7805923758") || id.equals("ee7a9cd0f6") || id.equals("b2cce3f5ad")) {
				return "系统分类不可删除！";
			}

			if (!checkNewsClass(id)) {
				deleteOne(id);
				// 删除下级
				deleteSonAndCheck(id);
			}
		}
		return "操作成功！（被引用的分类无法删除！）";
	}

	@Override
	public List<Cat> findGoodscats(String parent_cat_id) {
		return catDao.findGoodscats(parent_cat_id);
	}
	
	@Override
	public List<Cat> findGoodsCatsByOne(String parent_cat_id) {
		return catDao.findGoodsCatsByOne(parent_cat_id);
	}

	public boolean checkNewsClass(String cat_id) {
		boolean flag = newsService.checkNewsClass(cat_id);
		List<Cat> catList = getSon(cat_id);
		for (Cat cat : catList) {
			flag = checkSonNewsClass(cat.getCat_id());
		}
		return flag;
	}

	public boolean checkSonNewsClass(String cat_id) {
		boolean flag = newsService.checkNewsClass(cat_id);
		return flag;
	}

	@Override
	public Cat getWithRate(String cat_id) {
		
		return catDao.getWithRate(cat_id);
	}

}
