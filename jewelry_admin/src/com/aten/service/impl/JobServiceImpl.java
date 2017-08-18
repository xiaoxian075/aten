package com.aten.service.impl;

import com.aten.model.orm.Job;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.aten.dao.JobDao;
import com.aten.service.JobService;

@Service("jobService")
public class JobServiceImpl extends CommonServiceImpl<Job,String> implements JobService{

	private JobDao jobDao;

	@Autowired
	public JobServiceImpl(JobDao jobDao) {
		super(jobDao);
		this.jobDao=jobDao;
	}

}




