package com.admin.service.impl;

import com.admin.dao.HistoryAmountDaoMapper;
import com.admin.model.Amount;
import com.admin.service.HistoryAmountService;
import com.core.generic.GenericDao;
import com.core.generic.GenericServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2016/11/24.
 */
@Service
public class HistoryAmountServiceImpl extends GenericServiceImpl<Amount, String> implements HistoryAmountService{

    @Override
    public GenericDao<Amount, String> getDao() {
        return null;
    }

    @Resource
    private HistoryAmountDaoMapper historyAmountDaoMapper;

    @Override
    public void statisticsHistoryAmount() {
        HashMap<Object,Object> map = new HashMap<Object,Object>();
        map.put("day","1");// 昨天统计
        List<Amount> list = historyAmountDaoMapper.getAmountByDay(map);//总额
        map.put("payType","Y01");//云支付扫码
        List<Amount> list1 = historyAmountDaoMapper.getAmountByDay(map);
        for(int i=0;i<list.size();i++){
            for(int j=0;j<list1.size();j++){
                if(list.get(i).getDeviceCode().equals(list1.get(j).getDeviceCode())){
                    list.get(i).setSmAmount(list1.get(j).getAmount());
                    list.get(i).setSmCount(list1.get(j).getCount());
                }
            }
        }
        map.put("payType","01");//贷记卡(信用卡)
        List<Amount> list2 = historyAmountDaoMapper.getAmountByDay(map);
        for(int i=0;i<list.size();i++){
            for(int j=0;j<list2.size();j++){
                if(list.get(i).getDeviceCode().equals(list2.get(j).getDeviceCode())){
                    list.get(i).setSxykAmount(list2.get(j).getAmount());
                    list.get(i).setSxykCount(list2.get(j).getCount());
                }
            }
        }
        map.put("payType","00");//借记卡(储蓄卡)
        List<Amount> list3 = historyAmountDaoMapper.getAmountByDay(map);
        for(int i=0;i<list.size();i++){
            for(int j=0;j<list3.size();j++){
                if(list.get(i).getDeviceCode().equals(list3.get(j).getDeviceCode())){
                    list.get(i).setScxkAmount(list3.get(j).getAmount());
                    list.get(i).setScxkCount(list3.get(j).getCount());
                }
            }
        }

        historyAmountDaoMapper.inserBatchAmount(list);
    }
}
