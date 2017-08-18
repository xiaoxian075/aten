package com.admin.service.impl;

import com.admin.dao.AdvertDaoMapper;
import com.admin.dto.AdvertDto;
import com.admin.model.Advert;
import com.admin.model.BaseExample;
import com.admin.service.AdvertService;
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
 * Created by Administrator on 2016/4/11.
 */
@Service
public class AdvertServiceImpl extends GenericServiceImpl<Advert, String> implements AdvertService {
    @Resource
    private AdvertDaoMapper advertDaoMapper;
    @Override
    public GenericDao<Advert, String> getDao() {
        return advertDaoMapper;
    }
    public List<Advert> selectByTypeAndPage(DataTablesPage<Advert> page, BaseExample baseExample){
        return advertDaoMapper.selectByTypeAndPage(page,baseExample);
    }
    public void updateAdvert(MultipartFile file, String path, Advert advert) throws IOException{
        if (file.getSize()>0) {
            String picPath = FileUploadUtil.upLoadFile(file, path);
            String oldPath=advert.getPath();
            FileUploadUtil.delFile(path+oldPath);
            advert.setPath(picPath);
        }
        this.update(advert);
    }
    public List<Advert> selectByExample(BaseExample baseExample){
        return advertDaoMapper.selectByExample(baseExample);
    }
    public List<Advert> selectByType(AdvertDto advertDto){
        return advertDaoMapper.selectByType(advertDto);
    }
}
