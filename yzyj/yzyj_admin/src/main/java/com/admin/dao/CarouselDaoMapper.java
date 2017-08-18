package com.admin.dao;

import com.admin.model.BaseExample;
import com.admin.model.Carousel;
import com.core.generic.GenericDao;
import com.core.mybatis.DataTablesPage;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by Administrator on 2016/4/15.
 */
public interface CarouselDaoMapper extends GenericDao<Carousel,String> {

    List<Carousel> selectByExampleAndPage(DataTablesPage<Carousel> page, BaseExample baseExample);

    void deleteById(@Param("id") int id);

    void updateStatus(@Param("id") int id,
                      @Param("status") int status);

    void updateCarousel(Carousel carousel);

    void insertCarousel(Carousel carousel);

    Carousel getCarouselByCId(@Param("id") int id);

}
