package com.aten.service;

import com.aten.model.bean.OrgZtreeVo;
import com.aten.model.orm.Area;
import com.aten.model.orm.Organize;

import java.util.List;

public  interface OrganizeService extends CommonService<Organize, String>{


    List<OrgZtreeVo> getAllOrg();

    List<Organize> getSon(String id);

    Organize getByOrgCode(String parameter_id);

}

