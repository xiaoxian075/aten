package com.admin.service;

import com.admin.dto.AdvertDto;
import com.admin.model.Advert;
import com.admin.model.BaseExample;
import com.core.generic.GenericService;
import com.core.mybatis.DataTablesPage;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * Created by Administrator on 2016/4/11.
 */
public interface AdvertService extends GenericService<Advert,String> {
    List<Advert> selectByTypeAndPage(DataTablesPage<Advert> page, BaseExample baseExample);
    void updateAdvert(MultipartFile file, String path, Advert advert) throws IOException;
    List<Advert> selectByExample(BaseExample baseExample);
    List<Advert> selectByType(AdvertDto advertDto);
}
