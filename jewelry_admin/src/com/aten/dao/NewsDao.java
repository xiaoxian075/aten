package com.aten.dao;
import java.util.List;

import com.aten.model.orm.News;

public interface NewsDao extends CommonDao<News, String>{

	List<News> checkNewsClass(String cat_id);
	
}


