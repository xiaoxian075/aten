package com.aten.service.impl;

import com.aten.model.orm.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.aten.dao.MemberDao;
import com.aten.service.MemberService;

@Service("memberService")
public class MemberServiceImpl extends CommonServiceImpl<Member,String> implements MemberService{

	private MemberDao memberDao;

	@Autowired
	public MemberServiceImpl(MemberDao memberDao) {
		super(memberDao);
		this.memberDao=memberDao;
	}

}




