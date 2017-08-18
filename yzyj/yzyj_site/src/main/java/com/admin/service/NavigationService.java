package com.admin.service;

import com.admin.model.Advert;
import com.admin.model.BaseExample;
import com.admin.model.Navigation;
import com.core.generic.GenericService;
import com.core.mybatis.DataTablesPage;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * Created by Administrator on 2017/3/22.
 */
public interface NavigationService extends GenericService<Navigation,String> {
    List<Navigation> selectByExampleAndPage(DataTablesPage<Navigation> page, BaseExample baseExample);
    List<Navigation> selectByExample(BaseExample baseExample);
    void updateNavigation(MultipartFile file, String path, Navigation navigation) throws IOException;
}
