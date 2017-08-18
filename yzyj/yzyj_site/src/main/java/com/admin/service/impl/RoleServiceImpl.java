package com.admin.service.impl;
import com.admin.dao.RoleDaoMapper;
import com.admin.model.*;
import com.admin.service.RoleService;
import com.core.generic.GenericDao;
import com.core.generic.GenericServiceImpl;
import com.core.mybatis.DataTablesPage;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List; /** Created by Administrator on 2016/3/24. */
@Service public class RoleServiceImpl extends GenericServiceImpl<Role, String> implements RoleService {
    @Resource
    private RoleDaoMapper roleDaoMapper; /** 根据角色查询权限 @return */
    public List<Menu> selectMenuByroleId(String roleId){ return null; }
    @Override
    public GenericDao<Role, String> getDao() { return roleDaoMapper; }
    public List<Role> selectByExample(RoleExample roleExample){
        return roleDaoMapper.selectByExample(roleExample);
    }
    public List<Role> selectByExampleAndPage(DataTablesPage<Role> page, RoleExample roleExample){
        return roleDaoMapper.selectByExampleAndPage(page,roleExample);
    }
    public void updateRoleFunction(String roleId, String... menuIds){
        this.deleteFunctionByRoleId(roleId);
        if(!StringUtils.isEmpty(menuIds)){
            /**
             * 获取 操作权限
             */
            BaseExample baseExample = new BaseExample();
            List<String> arrList = Arrays.asList(menuIds);
//            baseExample.createCriteria().andIn("menu_id",arrList);
//            List<MenuHandleModule> list = roleDaoMapper.selectMenuHandleModule(baseExample);
            for ( String str :menuIds){
                RoleFunction roleFunction=new RoleFunction();
                roleFunction.setRoleId(roleId);
                roleFunction.setMenuId(str);
//                roleFunction.setHandleModule(getMenuHandleModule(list,str));
                this.insertFunction(roleFunction);
            }
        }
    }
    private String getMenuHandleModule(List<MenuHandleModule> list,String menuId){
        String heandleStr = "";
        try{
            for (MenuHandleModule vo:list){
                if(vo.getMenuId().equals(menuId)){
                    heandleStr+=vo.getModuleId()+",";
                }
            }
        }catch (Exception e ){

        }
        return heandleStr;
    }
    public void deleteFunctionByRoleId(String roleId){
        roleDaoMapper.deleteFunctionByRoleId(roleId);
    }
    public void insertFunction(RoleFunction roleFunction){
        roleDaoMapper.insertFunction(roleFunction);
    }
}
