package com.admin.service;

import com.admin.model.BaseExample;
import com.admin.model.Carousel;
import com.core.generic.GenericService;
import com.core.mybatis.DataTablesPage;

import java.util.List;

/**
 * Created by Administrator on 2016/12/7.
 */
public interface CarouselService extends GenericService<Carousel,String> {

    List<Carousel> selectByExampleAndPage(DataTablesPage<Carousel> page, BaseExample baseExample);

    void insertCarousel(Carousel carousel);

    void updateCarousel(Carousel carousel);

    void updateStatus(int id, int status);

    void deleteById(int id);

    Carousel getCarouselByCId(int id);
}
