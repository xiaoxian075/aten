package com.aten.service.impl;

import com.aten.model.bean.OrgZtreeVo;
import com.aten.model.orm.Area;
import com.aten.model.orm.Organize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.aten.dao.OrganizeDao;
import com.aten.service.OrganizeService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service("organizeService")
public class OrganizeServiceImpl extends CommonServiceImpl<Organize,String> implements OrganizeService{

	private OrganizeDao organizeDao;

	@Autowired
	public OrganizeServiceImpl(OrganizeDao organizeDao) {
		super(organizeDao);
		this.organizeDao=organizeDao;
	}
	
	/*获取所有部门*/
	public List<OrgZtreeVo> getAllOrg() {
		HashMap<String, Object> paraMap = new HashMap<String, Object>();
		List<Organize> orgs = organizeDao.getList(paraMap);
		List<OrgZtreeVo> list = new ArrayList<OrgZtreeVo>();
		for (Organize org : orgs) {
			OrgZtreeVo orgZtreeVo = new OrgZtreeVo();
			orgZtreeVo.setId(org.getOrg_id());
			orgZtreeVo.setpId(org.getParent_org_id());
			orgZtreeVo.setName(org.getOrg_name());
			orgZtreeVo.setCode(org.getOrg_code());
            orgZtreeVo.setOpen("true");
			String parentId=org.getParent_org_id();
			//设置上级部门属性
			if ("1111111111".equals(parentId)){
				//如果是根目录
				orgZtreeVo.setParentCode("yszb");
				orgZtreeVo.setParentName("云商珠宝");
			}else{
				orgZtreeVo.setParentCode(org.getParent_org_code());
				orgZtreeVo.setParentName(org.getParent_org_name());
			}
			orgZtreeVo.setParentCode(org.getOrg_code());
			list.add(orgZtreeVo);
		}
        return list;
	}
   /*获取下级部门*/
	public List<Organize> getSon(String id) {
		return organizeDao.getSon(id);
	}

	public Organize getByOrgCode(String org_code) {
		return organizeDao.getByOrgCode(org_code);
	}
}




