package com.admin.service.impl;

import com.admin.dao.NavigationDaoMapper;
import com.admin.model.BaseExample;
import com.admin.model.Navigation;
import com.admin.service.NavigationService;
import com.core.generic.GenericDao;
import com.core.generic.GenericServiceImpl;
import com.core.mybatis.DataTablesPage;
import com.core.util.FileUploadUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

/**
 * Created by Administrator on 2017/3/22.
 */
@Service
public class NavigationServiceImpl extends GenericServiceImpl<Navigation, String> implements NavigationService {
    @Resource
    private NavigationDaoMapper navigationDaoMapper;

    @Override
    public GenericDao<Navigation, String> getDao() {
        return navigationDaoMapper;
    }
    public List<Navigation> selectByExampleAndPage(DataTablesPage<Navigation> page, BaseExample baseExample){
        return navigationDaoMapper.selectByExampleAndPage(page,baseExample);
    }
    public List<Navigation> selectByExample(BaseExample baseExample){
        return navigationDaoMapper.selectByExample(baseExample);
    }
    public void updateNavigation(MultipartFile file, String path, Navigation navigation) throws IOException{
        if (file.getSize()>0) {
            String picPath = FileUploadUtil.upLoadFile(file, path);
            String oldPath=navigation.getLogo();
            FileUploadUtil.delFile(path+oldPath);
            navigation.setLogo(picPath);
        }
        this.update(navigation);
    }
}
