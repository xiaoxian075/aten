package com.aten.service.impl;

import com.aten.model.orm.Feedback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.aten.dao.FeedbackDao;
import com.aten.service.FeedbackService;

@Service("feedbackService")
public class FeedbackServiceImpl extends CommonServiceImpl<Feedback,String> implements FeedbackService{

	private FeedbackDao feedbackDao;

	@Autowired
	public FeedbackServiceImpl(FeedbackDao feedbackDao) {
		super(feedbackDao);
		this.feedbackDao=feedbackDao;
	}

}




