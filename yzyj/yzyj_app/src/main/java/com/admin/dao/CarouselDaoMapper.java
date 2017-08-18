package com.admin.dao;

import com.admin.model.Carousel;
import com.core.generic.GenericDao;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by Administrator on 2016/4/15.
 */
public interface CarouselDaoMapper extends GenericDao<Carousel,String> {

    List<Carousel> getInfoList(@Param("type") Integer type);

    Carousel getCarouselByCId(@Param("id") int id);

}
