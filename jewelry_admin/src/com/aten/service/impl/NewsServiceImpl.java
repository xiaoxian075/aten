package com.aten.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aten.dao.NewsDao;
import com.aten.model.orm.News;
import com.aten.service.NewsService;

@Service("newsService")
public class NewsServiceImpl extends CommonServiceImpl<News, String> implements NewsService {

	private NewsDao newsDao;

	@Autowired
	public NewsServiceImpl(NewsDao newsDao) {
		super(newsDao);
		this.newsDao = newsDao;
	}

	@Override
	public boolean checkNewsClass(String cat_id) {
		List<News> newsList = newsDao.checkNewsClass(cat_id);
		if (newsList != null && newsList.size() > 0) {
			return true;
		}
		return false;
	}

}
