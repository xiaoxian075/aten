package com.admin.service.impl;

import com.admin.dao.MenuDaoMapper;
import com.admin.model.Menu;
import com.admin.model.RoleFunction;
import com.admin.service.MenuService;
import com.admin.vo.NodeVo;
import com.core.generic.GenericDao;
import com.core.generic.GenericServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/3/30.
 */
@Service
public class MenuServiceImpl extends GenericServiceImpl<Menu, String> implements MenuService {
    @Resource
    private MenuDaoMapper menuDaoMapper;
    /**
     * 获取菜单树
     * @return
     */
   public List<NodeVo> selectMenuNode(String roleid){

       List<NodeVo> list = new ArrayList<NodeVo>();
       List<Menu> lss=menuDaoMapper.selectByExample();
       List<RoleFunction> functionLlist=this.selectRoleFunction(roleid);
       for (Menu menu : lss) {
           Boolean ckecked=false;
           for (RoleFunction roleFunction:functionLlist){
               if (menu.getId().equals(roleFunction.getMenuId())){
                   ckecked=true;
               }
           }
           NodeVo nodeVo = new NodeVo(menu.getId(), menu.getPid(), menu.getName(), true, ckecked);
           list.add(nodeVo);
       }
       return list;
   }

    @Override
    public GenericDao<Menu, String> getDao() {
        return menuDaoMapper;
    }
    public List<RoleFunction> selectRoleFunction(String roleId){
        return menuDaoMapper.selectRoleFunction(roleId);
    }
}
