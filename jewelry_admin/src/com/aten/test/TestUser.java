package com.aten.test;

import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;  
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import com.aten.dao.SysmenuDao;
import com.aten.function.SysmenuFuc;
import com.aten.model.orm.Power;
import com.aten.model.orm.Sysmenu;
import com.aten.mongo.MongoDao;
import com.aten.service.PowerService;
import com.aten.service.SysmenuService;
  
@RunWith(SpringJUnit4ClassRunner.class)      
@ContextConfiguration(locations = {"classpath:/spring-*.xml"})   
public class TestUser extends AbstractJUnit4SpringContextTests{  
	
	private static Logger logger = Logger.getLogger(TestUser.class);  

    //@Autowired  
	//private UserTestService userTestService;
    @Autowired
    private SysmenuService sysmenuService;
    @Autowired
    private PowerService powerService;
    @Autowired
    private SysmenuDao sysmenuDao;
    @Autowired
    private MongoDao<Sysmenu,String> mongoDao;

    @Test  
    public void select() { 
    	

    	long startTime = System.currentTimeMillis(); // 获取开始时间

    	List<Power> powerList = powerService.getList(null);
    	for(Power power:powerList){
    		System.out.println(power.getUrl().trim()+"====="+SysmenuFuc.getMenuNameStr(power.getMenu_id()," > "));
    	}
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	/*Sysmenu sysmenu = new Sysmenu(); 
    	sysmenu.setParent_menu_id("532132dfdas6fasd0fdasfdsf");
    	sysmenu.setMenu_name("asfdasfdasfdasfdasfdasfdasfdsafdasfdasfdasf");
    	sysmenu.setMenu_level("663623");
    	HashMap<String, String> mongoMap = new HashMap<String, String>();
    	mongoMap.put("parent_menu_id", "532132dfdas6fasd0fdasfdsf");
    	mongoDao.updateByTrem(sysmenu, mongoMap);
    	*/
    	//sysmenuService.getList(null);
    	
    	long endTime = System.currentTimeMillis(); // 获取结束时间
		System.out.println("方法执行行时间： " + (endTime - startTime) + "ms");
    	
    }
    
}  