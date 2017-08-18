package com.aten.service;

import com.aten.model.orm.News;

public interface NewsService extends CommonService<News, String> {

	boolean checkNewsClass(String cat_id);

}
