package com.admin.service.impl;

import com.admin.dao.PropagandaDaoMapper;
import com.admin.model.BaseExample;
import com.admin.model.Propaganda;
import com.admin.service.PropagandaService;
import com.admin.vo.DictVo;
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
 * Created by Administrator on 2017/1/13.
 */
@Service
public class PropagandaServiceImpl extends GenericServiceImpl<Propaganda, String> implements PropagandaService {
    @Resource
    private PropagandaDaoMapper propagandaDaoMapper;
    @Override
    public GenericDao<Propaganda, String> getDao() {
        return propagandaDaoMapper;
    }
    public List<Propaganda> selectByExampleAndPage(DataTablesPage<Propaganda> page, BaseExample baseExample){
        return propagandaDaoMapper.selectByExampleAndPage(page,baseExample);
    }
    public List<Propaganda> selectByExample(BaseExample baseExample){
        return propagandaDaoMapper.selectByExample(baseExample);
    }
    public void updatePropaganda(Propaganda propaganda,String path, MultipartFile file1, MultipartFile file2) throws IOException {
        if (file1.getSize() > 0) {
            String picPath = FileUploadUtil.upLoadFile(file1, path);
            String oldPic=propaganda.getPicPath();
            FileUploadUtil.delFile(path+oldPic);
            propaganda.setPicPath(picPath);
        }
        if (file2.getSize() > 0) {
            String picPath = FileUploadUtil.upLoadFile(file2, path);
            String oldPath=propaganda.getPath();
            FileUploadUtil.delFile(path+oldPath);
            propaganda.setPath(picPath);
        }
        this.update(propaganda);
    }
    public List<DictVo>selectVideo(){
        return propagandaDaoMapper.selectVideo();
    }
}
