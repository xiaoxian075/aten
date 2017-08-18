package com.admin.service;

import com.admin.model.BaseExample;
import com.admin.model.Propaganda;
import com.admin.vo.DictVo;
import com.core.generic.GenericService;
import com.core.mybatis.DataTablesPage;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * Created by Administrator on 2017/1/13.
 */
public interface PropagandaService extends GenericService<Propaganda,String> {
    List<Propaganda> selectByExampleAndPage(DataTablesPage<Propaganda> page, BaseExample baseExample);
    List<Propaganda> selectByExample(BaseExample baseExample);
    void updatePropaganda(Propaganda propaganda,String path, MultipartFile file1,MultipartFile file2) throws IOException;
    List<DictVo>selectVideo();

}
