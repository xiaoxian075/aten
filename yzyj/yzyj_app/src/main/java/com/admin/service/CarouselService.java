package com.admin.service;

import com.admin.model.Carousel;
import com.core.generic.GenericService;

import java.util.List;

/**
 * Created by Administrator on 2016/12/7.
 */
public interface CarouselService extends GenericService<Carousel,String> {

    List<Carousel> getInfoList(Integer type);

    Carousel getCarouselByCId(int id);

}
