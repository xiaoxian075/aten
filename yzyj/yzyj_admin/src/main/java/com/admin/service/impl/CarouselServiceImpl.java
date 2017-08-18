package com.admin.service.impl;

import com.admin.dao.CarouselDaoMapper;
import com.admin.model.BaseExample;
import com.admin.model.Carousel;
import com.admin.service.CarouselService;
import com.core.generic.GenericDao;
import com.core.generic.GenericServiceImpl;
import com.core.mybatis.DataTablesPage;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2016/12/7.
 */
@Service
public class CarouselServiceImpl extends GenericServiceImpl<Carousel, String> implements CarouselService {

    @Resource
    private CarouselDaoMapper carouselDaoMapper;

    @Override
    public List<Carousel> selectByExampleAndPage(DataTablesPage<Carousel> page, BaseExample baseExample) {
        return carouselDaoMapper.selectByExampleAndPage(page,baseExample);
    }

    @Override
    public void insertCarousel(Carousel carousel) {
        carousel.setStatus(0);
        carousel.setAddTime(new Date());
        carouselDaoMapper.insertCarousel(carousel);
    }

    @Override
    public void updateCarousel(Carousel carousel) {
        carouselDaoMapper.updateCarousel(carousel);
    }

    @Override
    public void updateStatus(int id, int status) {
        carouselDaoMapper.updateStatus(id,status);
    }

    @Override
    public void deleteById(int id) {
        carouselDaoMapper.deleteById(id);
    }

    @Override
    public Carousel getCarouselByCId(int id) {
        return carouselDaoMapper.getCarouselByCId(id);
    }

    @Override
    public GenericDao<Carousel, String> getDao() {
        return null;
    }
}
