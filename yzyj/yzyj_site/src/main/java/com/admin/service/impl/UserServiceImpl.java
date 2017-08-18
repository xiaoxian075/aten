package com.admin.service.impl;

import com.admin.dao.UserDaoMapper;
import com.admin.model.BaseExample;
import com.admin.model.Menu;
import com.admin.model.User;
import com.admin.model.UserExample;
import com.admin.service.UserService;
import com.admin.vo.MenuVo;
import com.admin.vo.UserVo;
import com.core.generic.GenericDao;
import com.core.generic.GenericServiceImpl;
import com.core.mybatis.DataTablesPage;
import com.core.util.MD5Util;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 功能描述：
 * 作者： linym
 * 时间：2016/2/25
 */
@Service
public class UserServiceImpl extends GenericServiceImpl<User, String> implements UserService {
    @Resource
    private UserDaoMapper userDaoMapper;

    @Override
    public boolean selectUser(User user) {
        User uservo=userDaoMapper.selectUser(user);
        if(null==uservo){
            return false;
        }else{
            return true;
        }

    }
    /**
     * 根据用户名查询用户
     * @return
     */
    public User selectByUserName(String username){
        return userDaoMapper.selectByUserName(username);
    }
    /**
     * 查找用户的功能菜单
     * @param id
     * @return
     */
    public java.util.HashMap selectUserMenu(String id){
        java.util.HashMap map = new java.util.HashMap();
        List<Menu> menuList=userDaoMapper.selectUserMenu(id);
        List<MenuVo> menuVoList=new ArrayList<>();
        String handleModule = "";
        for (Menu menu : menuList) {
            MenuVo menus = new MenuVo();
            menus.setId(menu.getId());
            menus.setName(menu.getName());
            menus.setUrl(menu.getUrl());
            menus.setPid(menu.getPid());
            handleModule += MD5Util.encodeByMD5Base64(menu.getUrl()+"de0#O32.56+/*DMF")+",";
            if ("0".equals(menu.getPid())) {
                menuVoList.add(menus);
            } else {
                for (MenuVo menuVo : menuVoList) {
                    if (menuVo.getId().equals(menus.getPid())) {
                        List<MenuVo> children = menuVo.getChildren();
                        if (StringUtils.isEmpty(children)) {
                            List<MenuVo> childrenVo = new ArrayList<>();
                            childrenVo.add(menus);
                            menuVo.setChildren(childrenVo);
                        } else {
                            children.add(menus);
                        }
                    }
                }
            }
        }
        map.put("menuVoList",menuVoList);
        map.put("handleModule",handleModule);
        return map;
    }
    public List<UserVo> selectByUserAndPage(DataTablesPage<UserVo> page, UserExample usersExample){
        return userDaoMapper.selectByUserAndPage(page,usersExample);
    }

    public Integer deleteAgentUser(String agentUnique){
        return userDaoMapper.deleteAgentUser(agentUnique);
    }

    @Override
    public GenericDao<User, String> getDao() {
        return userDaoMapper;
    }
}
