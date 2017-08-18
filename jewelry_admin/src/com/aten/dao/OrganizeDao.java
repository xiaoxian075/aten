package com.aten.dao;
import com.aten.model.orm.Organize;

import java.util.List;

public interface OrganizeDao extends CommonDao<Organize, String>{

    List<Organize> getSon(String id);

    Organize getByOrgCode(String org_code);
}


